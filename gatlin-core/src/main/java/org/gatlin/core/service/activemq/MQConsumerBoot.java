package org.gatlin.core.service.activemq;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.gatlin.core.CoreConsts;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.condition.MQConsumerCondition;
import org.gatlin.util.lang.StringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Conditional(MQConsumerCondition.class)
public class MQConsumerBoot {

	@Bean("jmsFactory")
	public PooledConnectionFactory jmsFactory() {
		ActiveMQConnectionFactory amc = new ActiveMQConnectionFactory();
		amc.setBrokerURL(GatlinConfigration.get(CoreConsts.ACTIVEMQ_BROKER_URL));
		amc.setTrustAllPackages(GatlinConfigration.get(CoreConsts.ACTIVEMQ_TRUST_ALL_PACKAGES));
		amc.setUseAsyncSend(GatlinConfigration.get(CoreConsts.ACTIVEMQ_USE_ASYNC_SEND));
		String username = GatlinConfigration.get(CoreConsts.ACTIVEMQ_USERNAME);
		if (StringUtil.hasText(username))
			amc.setUserName(username);
		String password = GatlinConfigration.get(CoreConsts.ACTIVEMQ_PASSWORD);
		if (StringUtil.hasText(password))
			amc.setPassword(username);
		PooledConnectionFactory factory = new PooledConnectionFactory(amc);
		factory.setMaxConnections(GatlinConfigration.get(CoreConsts.ACTIVEMQ_MAX_CONNECTIONS));
		return factory;
	}
	
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory =  new CachingConnectionFactory();
		connectionFactory.setTargetConnectionFactory(jmsFactory());
		connectionFactory.setSessionCacheSize(GatlinConfigration.get(CoreConsts.ACTIVEMQ_SESSION_CACHE_SIZE));
		return connectionFactory;
	}
	
	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setDaemon(GatlinConfigration.get(CoreConsts.ACTIVEMQ_DAEMON));
		taskExecutor.setMaxPoolSize(GatlinConfigration.get(CoreConsts.ACTIVEMQ_MAX_POOL_SIZE));
		taskExecutor.setCorePoolSize(GatlinConfigration.get(CoreConsts.ACTIVEMQ_CORE_POOL_SIZE));
		taskExecutor.setKeepAliveSeconds(GatlinConfigration.get(CoreConsts.ACTIVEMQ_KEEP_ALIVE_SECONDS));
		return taskExecutor;
	}
	
	@Bean
	@Scope("prototype")
	public MessageListenerContainer messageContainer() {
		DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
		messageListenerContainer.setConnectionFactory(connectionFactory());
		messageListenerContainer.setDestinationName(StringUtil.EMPTY);
		messageListenerContainer.setReceiveTimeout((long) GatlinConfigration.get(CoreConsts.ACTIVEMQ_RECEIVE_TIMEOUT));
		messageListenerContainer.setTaskExecutor(taskExecutor());
		messageListenerContainer.setConcurrentConsumers(GatlinConfigration.get(CoreConsts.ACTIVEMQ_CONCURRENT_CONSUMERS));
		return messageListenerContainer;
	}
}
