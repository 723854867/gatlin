package org.gatlin.soa.user;

import javax.annotation.Resource;

import org.gatlin.core.util.Assert;
import org.gatlin.dao.redis.RedisLock;
import org.gatlin.soa.user.bean.UserCode;
import org.gatlin.util.callback.NullCallback;
import org.gatlin.util.callback.NullParamCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ThreadsafeInvoker {
	
	private static final Logger logger = LoggerFactory.getLogger(ThreadsafeInvoker.class);
	
	@Resource
	private RedisLock redisLock;
	
	public void invoke(long uid, NullCallback callback) {
		String lockId = tryLock(uid);
		try {
			callback.invoke();
		} finally {
			boolean released = releaseLock(uid, lockId);
			if (!released)
				logger.warn("用户  {} 锁资源释放失败！", uid);
		}
	}
	
	public void invoke(long uid, long timeout, NullCallback callback) {
		String lock = KeyGenerator.userLockKey(uid);
		String lockId = redisLock.lock(lock, timeout);
		Assert.hasText(UserCode.USER_LOCK_FAIL, lockId);
		try {
			callback.invoke();
		} finally {
			boolean released = redisLock.releaseLock(lock, lockId);
			if (!released)
				logger.warn("用户  {} 锁资源释放失败！", uid);
		}
	}
	
	public <T> T invoke(long uid, NullParamCallback<T> callback) {
		String lockId = tryLock(uid);
		try {
			return callback.invoke();
		} finally {
			boolean released = releaseLock(uid, lockId);
			if (!released)
				logger.warn("用户  {} 锁资源释放失败！", uid);
		}
	}
	
	public String tryLock(long uid) {
		String lockId =  redisLock.tryLock(KeyGenerator.userLockKey(uid));
		return Assert.hasText(UserCode.USER_LOCK_FAIL, lockId);
	}
	
	public String lock(long uid, long timeout) {
		String lockId =  redisLock.lock(KeyGenerator.userLockKey(uid), timeout);
		return Assert.hasText(UserCode.USER_LOCK_FAIL, lockId);
	}
	
	public boolean releaseLock(long uid, String lockId) {
		return redisLock.releaseLock(KeyGenerator.userLockKey(uid), lockId);
	}
}
