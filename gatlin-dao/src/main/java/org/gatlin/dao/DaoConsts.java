package org.gatlin.dao;

import org.gatlin.core.bean.model.option.BoolOption;
import org.gatlin.core.bean.model.option.IntegerOption;
import org.gatlin.core.bean.model.option.StrOption;

public interface DaoConsts {

	interface Options {
		// 数据源配置
		final BoolOption DB_ENABLE										= new BoolOption("db.enable", false);
		final StrOption DB_DATASOURCE_JDBC								= new StrOption("db.datasource.jdbc");
		final StrOption DB_DATASOURCE_CLASS								= new StrOption("db.datasource.class");
		final StrOption DB_DATASOURCE_USERNAME							= new StrOption("db.datasource.username");
		final StrOption DB_DATASOURCE_PASSWORD							= new StrOption("db.datasource.password");
		final StrOption DB_DATASOURCE_DRIVER_CLASS						= new StrOption("db.datasource.driverClass");
		final IntegerOption DB_DATASOURCE_MIN_IDLE						= new IntegerOption("db.datasource.minIdle", 10);
		final IntegerOption DB_DATASOURCE_MAX_POOL_SIZE					= new IntegerOption("db.datasource.maxPoolSize", 10);
		final BoolOption DB_DATASOURCE_CACHE_PREP_STMTS					= new BoolOption("db.datasource.cachePrepStmts", true);
		final IntegerOption DB_DATASOURCE_IDLE_TIMEOUT					= new IntegerOption("db.datasource.idleTimeout", 600000);
		final IntegerOption DB_DATASOURCE_MAX_LIFE_TIME					= new IntegerOption("db.datasource.maxLifeTime", 1800000);
		final IntegerOption DB_DATASOURCE_PREP_STMT_CACHE_SIZE			= new IntegerOption("db.datasource.prepStmtCacheSize", 250);
		final IntegerOption DB_DATASOURCE_CONNECTION_TIMEOUT			= new IntegerOption("db.datasource.connectionTimeout", 30000);
		final IntegerOption DB_DATASOURCE_PREP_STMT_CACHE_SQL_LIMIT		= new IntegerOption("db.datasource.prepStmtCacheSqlLimit", 2048);
		
		// mybatis 配置
		final StrOption DB_MYBATIS_BASE_PACKAGE							= new StrOption("db.mybatis.basePackage");
		final BoolOption DB_MYBATIS_PAGE								= new BoolOption("db.mybatis.page", false);
		final StrOption DB_MYBATIS_MAPPER_LOCATION						= new StrOption("db.mybatis.mapperLocation", "classpath*:conf/mapper/*.xml");
		final StrOption DB_MYBATIS_TYPE_ALIASES_PACKAGE					= new StrOption("db.mybatis.typeAliasesPackage", "");
		final BoolOption DB_SESSION_CACHE_ENABLED						= new BoolOption("db.session.cacheEnabled", false);
		final BoolOption DB_SESSION_CAMEL_2_UNDERLINE					= new BoolOption("db.session.camel2underline", true);
		
		// redis
		final StrOption REDIS_HOST											= new StrOption("redis.host");
		final StrOption REDIS_PASSWORD										= new StrOption("redis.password");
		final StrOption REDIS_MASTER										= new StrOption("redis.masterName");
		final BoolOption REDIS_LIFO											= new BoolOption("redis.lifo", true);
		final BoolOption REDIS_ENABLE										= new BoolOption("redis.enable", false);
		final IntegerOption REDIS_PORT										= new IntegerOption("redis.port", 6379);
		final IntegerOption REDIS_MAX_IDLE									= new IntegerOption("redis.maxIdle", 8);
		final IntegerOption REDIS_MIN_IDLE									= new IntegerOption("redis.minIdle", 0);
		final IntegerOption REDIS_MAX_TOTAL									= new IntegerOption("redis.maxTotal", 8);
		final IntegerOption REDIS_DATABASE									= new IntegerOption("redis.database", 0);
		final BoolOption REDIS_JMX_ENABLED									= new BoolOption("redis.jmxEnabled", true);
		final BoolOption REDIS_TEST_ON_BORROW								= new BoolOption("redis.testOnBorrow", false);
		final BoolOption REDIS_TEST_ON_CREATE								= new BoolOption("redis.testOnCreate", false);
		final BoolOption REDIS_TEST_ON_RETURN								= new BoolOption("redis.testOnReturn", false);
		final BoolOption REDIS_TEST_WHILE_IDLE								= new BoolOption("redis.testWhileIdle", false);
		final IntegerOption REDIS_MAX_WAIT_MILLIS							= new IntegerOption("redis.maxWaitMillis", -1);
		final IntegerOption REDIS_CONN_TIMEOUT								= new IntegerOption("redis.connTimeout", 3000);
		final BoolOption REDIS_BLOCK_WHEN_EXHAUSTED							= new BoolOption("redis.blockWhenExhausted", true);
		final IntegerOption REDIS_NUM_TESTS_PER_EVICTION_RUN				= new IntegerOption("redis.numTestsPerEvictionRun", 3);
		final IntegerOption REDIS_TIME_BETWEEN_EVICTION_RUNS_MILLIS			= new IntegerOption("redis.timeBetweenEvictionRunsMillis", -1);
		final StrOption REDIS_POOL											= new StrOption("redis.pool", "redis.clients.jedis.JedisPool");
		final IntegerOption REDIS_MIN_EVICTABLE_IDLE_TIME_MILLIS			= new IntegerOption("redis.minEvictableIdleTimeMillis", 1800000);
		final IntegerOption REDIS_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS		= new IntegerOption("redis.softMinEvictableIdleTimeMillis", 1800000);
		final StrOption REDIS_EVICTION_POLICY_CLASS							= new StrOption("redis.evictionPolicyClass", "org.apache.commons.pool2.impl.DefaultEvictionPolicy");
		
		// mongo
		final StrOption MONGO_DB					= new StrOption("mongo.db");
		final StrOption MONGO_USERNAME				= new StrOption("mongo.username", "");
		final StrOption MONGO_PASSWORD				= new StrOption("mongo.password", "");
		final StrOption MONGO_HOST					= new StrOption("mongo.host");
		final BoolOption MONGO_ENABLE				= new BoolOption("mongo.enable", false);
	}
	
	interface RedisConsts {
		
		final String OK							= "OK";

		enum NXXX {
			NX,
			XX;
		}
		
		enum EXPX {
			EX,
			PX;
		}
	}
}
