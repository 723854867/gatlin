package org.gatlin.dao.redis;

import static org.gatlin.util.serial.SerializeUtil.REDIS.encode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.gatlin.dao.DaoConsts;
import org.gatlin.dao.DaoConsts.RedisConsts.EXPX;
import org.gatlin.dao.DaoConsts.RedisConsts.NXXX;
import org.gatlin.util.callback.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

@Component
@Conditional(RedisCondition.class)
public class Redis {
	
	private static final Logger logger = LoggerFactory.getLogger(Redis.class);
	
	@Resource
	private LuaFiber luaFiber;
	@Resource
	private Pool<Jedis> jedisPool;
	
	public String get(String key) { 
		return invoke(new Callback<Jedis, String>() {
			@Override
			public String invoke(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}
	
	public boolean set(String key, String value) {
		String result = invoke(new Callback<Jedis, String>() {
			@Override
			public String invoke(Jedis jedis) {
				return jedis.set(key, value);
			}
		});
		return null != result && result.equalsIgnoreCase(DaoConsts.RedisConsts.OK);
	}
	
	public boolean set(String key, String value, NXXX nxxx, EXPX expx, int timeout) {
		String result = invoke(new Callback<Jedis, String>() {
			@Override
			public String invoke(Jedis jedis) {
				return jedis.set(key, value, nxxx.name(), expx.name(), timeout);
			}
		});
		return null != result && result.equalsIgnoreCase(DaoConsts.RedisConsts.OK);
	}
	
	// ******************************** set ********************************
	
	public void sadd(String key, Object... members) {
		invoke(new Callback<Jedis, Long>() {
			@Override
			public Long invoke(Jedis jedis) {
				return jedis.sadd(encode(key), encode(members));
			}
		});
	}
	
	public boolean sismember(String key, Object member) {
		return invoke(new Callback<Jedis, Boolean>() {
			@Override
			public Boolean invoke(Jedis jedis) {
				return jedis.sismember(encode(key), encode(member));
			}
		});
	}
	
	// ******************************** hash ********************************
	
	public byte[] hget(Object key, Object field) {
		return invoke(new Callback<Jedis, byte[]>() {
			@Override
			public byte[] invoke(Jedis jedis) {
				return jedis.hget(encode(key), encode(field));
			}
		});
	}
	
	public List<byte[]> hmget(Object key, Object... fields) {
		return invoke(new Callback<Jedis, List<byte[]>>() {
			@Override
			public List<byte[]> invoke(Jedis jedis) {
				return jedis.hmget(encode(key), encode(fields));
			}
		});
	}
	
	public long hset(Object key, Object field, Object value) {
		return invoke(new Callback<Jedis, Long>() {
			@Override
			public Long invoke(Jedis jedis) {
				return jedis.hset(encode(key), encode(field), encode(value));
			}
		});
	}
	
	public void hmset(Object key, Map<Object, Object> hash) {
		invoke(new Callback<Jedis, String>() {
			@Override
			public String invoke(Jedis jedis) {
				Map<byte[], byte[]> data = new HashMap<byte[], byte[]>();
				for (Entry<Object, Object> entry : hash.entrySet()) 
					data.put(encode(entry.getKey()), encode(entry.getValue()));
				return jedis.hmset(encode(key), data);
			}
		});
	}
	
	public long hdel(Object key, Object...fields) { 
		return invoke(new Callback<Jedis, Long>() {
			@Override
			public Long invoke(Jedis jedis) {
				return jedis.hdel(encode(key), encode(fields));
			}
		});
	}
	
	// ******************************** lua ********************************
	
	/**
	 * 获取验证码
	 * 
	 * @param captchaKey 存放验证码的 key
	 * @param countKey 存放验证码获取次数的 key
	 * @param captcha 验证码
	 * @param lifeTime 验证码有效时长，单位毫秒
	 * @param countMaxinum 验证码最大获取次数
	 * @param countLifetTime 验证码次数生命周期(超过该时间没有获取验证码，则验证码次数 key 会被删除，也就是说验证码次数会被清零)，单位毫秒
	 * @param interval 两次获取验证码之间的时间间隔：在该时间之内再次获取会提示验证码获取太频繁
	 */
	public long captchaObtain(String captchaKey, String countKey, String captcha, long lifeTime, long countMaxinum, long countLifetTime, int interval) {
		return this.luaFiber.invoke(LuaCmd.CAPTCHA_OBTAIN, captchaKey, countKey, captcha, lifeTime, countMaxinum, countLifetTime, interval);
	}
	
	/**
	 * 如果 key 值存在并且值等于 value 则删除 value 然后返回 true，否则什么也不做返回 false
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean delIfEquals(String key, String value) {
		long flag = this.luaFiber.invoke(LuaCmd.DEL_IF_EQUALS, key, value);
		return flag == 1;
	}
	
	public <T> T execute(ILuaCmd cmd, Object... params) {
		return this.luaFiber.invoke(cmd, params);
	}
	
	public <T> T execute(int keyNum, LuaCmd cmd, Object... params) {
		return this.luaFiber.invoke(keyNum, cmd, params);
	}
	
	/**
	 * 执行普通的 redis 命令
	 * 
	 * @param invoke
	 * @return
	 */
	<T> T invoke(Callback<Jedis, T> invoke) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return invoke.invoke(jedis);
		} catch (Exception e) {
			logger.error("Jedis connection get failure!", e);
			throw e;
		} finally {
			if (null != jedis)
				jedis.close();
		}
	}
}
