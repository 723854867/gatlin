package org.gatlin.dao.mybatis;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.gatlin.core.CoreCode;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.dao.DaoConsts.Options;
import org.gatlin.dao.bean.AutoEnumTypeHandler;
import org.gatlin.util.lang.CollectionUtil;
import org.gatlin.util.lang.StringUtil;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@Conditional(DBCondition.class)
public class DBConfig {
	
	@Bean(name = "dataSource", destroyMethod = "close")
	public DataSource datasource() throws Exception {
		String datasource = GatlinConfigration.get(Options.DB_DATASOURCE_CLASS);
		Class.forName(datasource);
		if (datasource.equals("com.zaxxer.hikari.HikariDataSource")) 
			return _hikariCP();
		else 
			throw new CodeException(CoreCode.SYSTEM_ERR, "Unsupport dataSource type: " + datasource);
	}
	
	private DataSource _hikariCP() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(GatlinConfigration.get(Options.DB_DATASOURCE_DRIVER_CLASS));
		config.setJdbcUrl(GatlinConfigration.get(Options.DB_DATASOURCE_JDBC));
		config.setUsername(GatlinConfigration.get(Options.DB_DATASOURCE_USERNAME));
		config.setPassword(GatlinConfigration.get(Options.DB_DATASOURCE_PASSWORD));
		// 连接池中允许的最大连接数。常见的错误是设置一个太大的值，连接数多反而性能下降。参考计算公式是：connections = ((cpu * 2) + 硬盘数)
		config.setMaximumPoolSize(GatlinConfigration.get(Options.DB_DATASOURCE_MAX_POOL_SIZE));
		// 连接池空闲连接的最小数量，当连接池空闲连接少于minimumIdle，而且总共连接数不大于maximumPoolSize时，HikariCP会尽力补充新的连接。为了性能考虑，不建议设置此值，而是让HikariCP把连接池当做固定大小的处理，
		// 默认minimumIdle与maximumPoolSize一样。当minIdle<0或者minIdle>maxPoolSize,则被重置为maxPoolSize，该值默认为10。
		config.setMinimumIdle(GatlinConfigration.get(Options.DB_DATASOURCE_MIN_IDLE));
		// 一个连接的生命时长（毫秒），超时而且没被使用则被释放。强烈建议设置比数据库超时时长少30秒，（MySQL的wait_timeout参数，show variables like ‘%timeout%’，一般为8小时）
		// 和IdleTimeout的区别是不管连接池链接数量多少，只要一个连接超过该时间没被使用就会被回收
		config.setMaxLifetime((long)GatlinConfigration.get(Options.DB_DATASOURCE_MAX_LIFE_TIME));
		// 如果idleTimeout+1秒>maxLifetime 且 maxLifetime>0，则会被重置为0。如果idleTimeout=0则表示空闲的连接在连接池中永远不被移除。
		// 只有当minimumIdle小于maximumPoolSize时，这个参数才生效，当空闲连接数超过minimumIdle且空闲时间超过idleTimeout，才会被移除。
		config.setIdleTimeout((long)GatlinConfigration.get(Options.DB_DATASOURCE_IDLE_TIMEOUT));
		// 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException。 缺省:30秒
		config.setConnectionTimeout((long)GatlinConfigration.get(Options.DB_DATASOURCE_CONNECTION_TIMEOUT));
		config.addDataSourceProperty("cachePrepStmts", GatlinConfigration.get(Options.DB_DATASOURCE_CACHE_PREP_STMTS));
		config.addDataSourceProperty("prepStmtCacheSize", GatlinConfigration.get(Options.DB_DATASOURCE_PREP_STMT_CACHE_SIZE));
		config.addDataSourceProperty("prepStmtCacheSqlLimit", GatlinConfigration.get(Options.DB_DATASOURCE_PREP_STMT_CACHE_SQL_LIMIT));
		return new HikariDataSource(config);
	}
	
	@Bean
	public PlatformTransactionManager txManager() throws Exception {
		return new DataSourceTransactionManager(datasource());
	}
	
	@Bean("sqlSessionFactory")
	public SqlSessionFactoryBean sessionFactory() throws Exception {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(datasource());
		String mapperLocation = GatlinConfigration.get(Options.DB_MYBATIS_MAPPER_LOCATION);
		if (StringUtil.hasText(mapperLocation))
			factory.setMapperLocations(GatlinConfigration.getResources(mapperLocation));
		String typeAliasesPackage = GatlinConfigration.get(Options.DB_MYBATIS_TYPE_ALIASES_PACKAGE);
		if (StringUtil.hasText(typeAliasesPackage))
			factory.setTypeAliasesPackage(typeAliasesPackage);
		Set<Interceptor> interceptors = new HashSet<Interceptor>();
		boolean page = GatlinConfigration.get(Options.DB_MYBATIS_PAGE); 
		if (page) {
			PageInterceptor interceptor = new PageInterceptor();
			interceptor.setProperties(new Properties());
			interceptors.add(interceptor);
		}
		if (!CollectionUtil.isEmpty(interceptors))
			factory.setPlugins(interceptors.toArray(new Interceptor[] {}));
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setCacheEnabled(GatlinConfigration.get(Options.DB_SESSION_CACHE_ENABLED));
		configuration.setMapUnderscoreToCamelCase(GatlinConfigration.get(Options.DB_SESSION_CAMEL_2_UNDERLINE));
		configuration.getTypeHandlerRegistry().setDefaultEnumTypeHandler(AutoEnumTypeHandler.class);
		factory.setConfiguration(configuration);
		return factory;
	}
	
	@Bean("sqlSession")
	public SqlSession sqlSession(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
