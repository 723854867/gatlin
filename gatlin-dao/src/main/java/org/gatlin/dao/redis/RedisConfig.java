package org.gatlin.dao.redis;

import java.util.Set;

import org.gatlin.core.GatlinConfigration;
import org.gatlin.dao.DaoConsts.Options;
import org.gatlin.util.lang.CollectionUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Pool;

@Configuration
@Conditional(RedisCondition.class)
public class RedisConfig {
	
	@Bean(name = "jedisPool")
	public Pool<Jedis> jedisPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		// 连接耗尽时是否阻塞：false-直接抛异常，true-阻塞直到超时，默认 true
		config.setBlockWhenExhausted(GatlinConfigration.get(Options.REDIS_BLOCK_WHEN_EXHAUSTED));
		// 设置注册策略类名：默认 DefaultEvictionPolicy(当连接超过最大空闲时间，或连接数超过最大空闲连接数时逐出)
		config.setEvictionPolicyClassName(GatlinConfigration.get(Options.REDIS_EVICTION_POLICY_CLASS));
//		config.setFairness(fairness);
		// 是否启用pool的jmx管理功能, 默认true
		config.setJmxEnabled(GatlinConfigration.get(Options.REDIS_JMX_ENABLED));
//		config.setJmxNameBase(jmxNameBase);
//		config.setJmxNamePrefix("");
		// 是否启用后进先出  - last in first out, 默认true
		config.setLifo(GatlinConfigration.get(Options.REDIS_LIFO));
		// 最大空闲连接数, 默认8个
		config.setMaxIdle(GatlinConfigration.get(Options.REDIS_MAX_IDLE));
		// 最小空闲连接数, 默认0
		config.setMinIdle(GatlinConfigration.get(Options.REDIS_MIN_IDLE));
		// 最大连接数, 默认8个
		config.setMaxTotal(GatlinConfigration.get(Options.REDIS_MAX_TOTAL));
		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间, 默认-1
		config.setMaxWaitMillis((long)GatlinConfigration.get(Options.REDIS_MAX_WAIT_MILLIS));
		// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		config.setMinEvictableIdleTimeMillis((long)GatlinConfigration.get(Options.REDIS_MIN_EVICTABLE_IDLE_TIME_MILLIS));
		// 每次逐出检查时逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		config.setNumTestsPerEvictionRun(GatlinConfigration.get(Options.REDIS_NUM_TESTS_PER_EVICTION_RUN));
		// 对象空闲多久后逐出, 当空闲时间>该值且空闲连接>最大空闲数时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)   
		config.setSoftMinEvictableIdleTimeMillis((long)GatlinConfigration.get(Options.REDIS_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS));
		// 在获取连接的时候检查有效性, 默认false
		config.setTestOnBorrow(GatlinConfigration.get(Options.REDIS_TEST_ON_BORROW));
		config.setTestOnCreate(GatlinConfigration.get(Options.REDIS_TEST_ON_CREATE));
		config.setTestOnReturn(GatlinConfigration.get(Options.REDIS_TEST_ON_RETURN));
		// 在空闲时检查有效性, 默认false
		config.setTestWhileIdle(GatlinConfigration.get(Options.REDIS_TEST_WHILE_IDLE));
		// 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
		config.setTimeBetweenEvictionRunsMillis((long)GatlinConfigration.get(Options.REDIS_TIME_BETWEEN_EVICTION_RUNS_MILLIS));
		
		String poolName = GatlinConfigration.get(Options.REDIS_POOL);
		if (poolName.equals(ShardedJedisPool.class.getName())) {
			return null;
		} else if (poolName.equals(JedisSentinelPool.class.getName())) {
			String sentinels = GatlinConfigration.get(Options.REDIS_HOST);
			Set<String> set = CollectionUtil.splitIntoStringSet(sentinels, ";");
			return new JedisSentinelPool(GatlinConfigration.get(Options.REDIS_MASTER), set, config, 
					GatlinConfigration.get(Options.REDIS_CONN_TIMEOUT), 
					GatlinConfigration.get(Options.REDIS_PASSWORD));
		} else {
			return new JedisPool(config, GatlinConfigration.get(Options.REDIS_HOST), 
					GatlinConfigration.get(Options.REDIS_PORT), 
					GatlinConfigration.get(Options.REDIS_CONN_TIMEOUT), 
					GatlinConfigration.get(Options.REDIS_PASSWORD),
					GatlinConfigration.get(Options.REDIS_DATABASE));
		}
	}
}
