package org.gatlin.dao.redis;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.gatlin.core.CoreCode;
import org.gatlin.core.util.Assert;
import org.gatlin.dao.DaoConsts.RedisConsts.EXPX;
import org.gatlin.dao.DaoConsts.RedisConsts.NXXX;
import org.gatlin.util.KeyUtil;
import org.gatlin.util.callback.Callback;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * redis 分布式锁
 */
@Component
@Conditional(RedisCondition.class)
public class RedisLock {

	@Resource
	private Redis redis;
	private int lockTimeout = 30000;		// 默认 30 秒
	
	/**
	 * 分布式锁：尝试获取指定资源的锁，获取成功返回唯一锁id，失败则返回null。只尝试获取一次
	 * 
	 * @param lock
	 * @return
	 */
	public String tryLock(String lock) {
		String lockId = KeyUtil.uuid();
		return redis.set(lock, lockId, NXXX.NX, EXPX.PX, lockTimeout) ? lockId : null;
	}
	
	public String tryLock(String lock, int lockTimeout) {
		String lockId = KeyUtil.uuid();
		return redis.set(lock, lockId, NXXX.NX, EXPX.PX, lockTimeout) ? lockId : null;
	}
	
	/**
	 * 分布式锁：获取指定资源的锁，直到超时，成功则返回锁id，失败或者超时返回null,使用默认的超时时间
	 * 
	 * @param lock
	 * @return
	 */
	public String lock(String lock) {
		return lock(lock, this.lockTimeout);
	}
	
	/**
	 * 分布式锁：获取指定资源的锁，直到超时，成功则返回锁id，失败或者超时返回null。指定超时时间
	 * 
	 * @param lock
	 * @return
	 */
	public String lock(String lock, long timeout) {
		long begin = System.nanoTime();
		while (true) {
			String lockId = tryLock(lock);
			if (null != lockId)
				return lockId;
			
			long time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - begin);
			if (time >= timeout)
				return null;
			Thread.yield();
		}
	}
	
	public String lock(String... locks) {
		return lock(this.lockTimeout, locks);
	}
	
	public String lock(long timeout, String... locks) {
		long begin = System.nanoTime();
		while (true) {
			String lockId = tryLock(locks);
			if (null != lockId)
				return lockId;
			
			long time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - begin);
			if (time >= timeout)
				return null;
			Thread.yield();
		}
	}
	
	public String tryLock(String... locks) {
		String lockId = KeyUtil.uuid();
		Object[] params = new Object[locks.length * 2 + 1];
		params[0] = this.lockTimeout;
		for (int i = 1; i < params.length; i += 2) {
			params[i] = locks[i / 2];
			params[i + 1] = lockId;
		}
		long flag = redis.execute(LuaCmd.MULTILOCK, params);
		return 1 == flag ? lockId : null ;
	}
	
	/**
	 * 释放锁资源：建议获取锁成功之后将释放锁资源写在 final 块中
	 * 
	 * @param lock
	 * @param lockId
	 * @return
	 */
	public boolean releaseLock(String lock, String lockId) {
		return redis.delIfEquals(lock, lockId);
	}
	
	public boolean releaseLocks(String lockId, String... locks) {
		Object[] params = new Object[locks.length + 1];
		System.arraycopy(locks, 0, params, 0, locks.length);
		params[params.length - 1] = lockId;
		long count = redis.execute(locks.length, LuaCmd.RELEASE_LOCK, params);
		Assert.isTrue(count == 0 || count == locks.length, "release " + count + " locks within total " + locks.length);
		return count == locks.length;
	}
	
	public <P, V> V execute(Callback<P, V> callback, P param, String lock) {
		String lockId = tryLock(lock);
		Assert.notNull(CoreCode.RESOURCE_LOCKED, lockId);
		try {
			return callback.invoke(param);
		} finally {
			releaseLock(lock, lockId);
		}
	}
	
	public <P, V> V execute(Callback<P, V> callback, P param, String[] locks) {
		String lockId = tryLock(locks);
		Assert.notNull(CoreCode.RESOURCE_LOCKED, lockId);
		try {
			return callback.invoke(param);
		} finally {
			releaseLocks(lockId, locks);
		}
	} 
}
