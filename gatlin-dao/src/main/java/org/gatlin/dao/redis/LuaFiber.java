package org.gatlin.dao.redis;

import static org.gatlin.util.serial.SerializeUtil.REDIS.encode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.gatlin.util.callback.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisNoScriptException;

/**
 * Lua 命令执行器
 * 
 * @author lynn
 */
@Component
@Conditional(RedisCondition.class)
public class LuaFiber {
	
	private static final Logger logger = LoggerFactory.getLogger(LuaFiber.class);

	@Resource
	private Redis redis;
	private Map<String, Script> scripts = new ConcurrentHashMap<String, Script>();		
	
	public void addScript(String cmd, byte[] content) {
		Script script = new Script(content);
		Script old = scripts.put(cmd, script);
		if (null != old)
			logger.info("Script {} conflict, old script was replaced!", cmd);
	}
	
	@SuppressWarnings("unchecked")
	<T> T invoke(ILuaCmd cmd, Object... params) {
		Script script = scripts.get(cmd.key());
		if (null == script)
			throw new JedisNoScriptException("Script " + cmd + " not exist!");
		byte[][] arr = encode(params);
		return redis.invoke(new Callback<Jedis, T>() {
			@Override
			public T invoke(Jedis jedis) {
				if (script.stored) {
					try {
						return (T) jedis.evalsha(encode(script.sha1Key), cmd.keyCount(), arr);
					} catch (JedisNoScriptException e) {
						logger.warn("redis lua 脚本 - {} 缓存未命中,直接执行脚本！", cmd);
					}
				}
				T object = (T) jedis.eval(script.content, cmd.keyCount(), arr);
				script.stored = true;
				return object;
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	<T> T invoke(int keyNum, ILuaCmd cmd, Object... params) {
		Script script = scripts.get(cmd.key());
		if (null == script)
			throw new JedisNoScriptException("Script " + cmd + " not exist!");
		byte[][] arr = encode(params);
		return redis.invoke(new Callback<Jedis, T>() {
			@Override
			public T invoke(Jedis jedis) {
				if (script.stored) {
					try {
						return (T) jedis.evalsha(encode(script.sha1Key), keyNum, arr);
					} catch (JedisNoScriptException e) {
						logger.warn("redis lua 脚本 - {} 缓存未命中,直接执行脚本！", cmd);
					}
				}
				T object = (T) jedis.eval(script.content, keyNum, arr);
				script.stored = true;
				return object;
			}
		});
	}
	
	private class Script {
		private String sha1Key;
		private byte[] content;
		private boolean stored;
		private Script(byte[] content) {
			this.sha1Key = DigestUtils.sha1Hex(content);
			this.content = encode(new String(content));
		}
	}
}
