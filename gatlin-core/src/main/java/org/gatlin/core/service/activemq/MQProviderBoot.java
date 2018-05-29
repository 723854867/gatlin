package org.gatlin.core.service.activemq;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.gatlin.core.CoreConsts;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.condition.MQProviderCondition;
import org.gatlin.util.lang.StringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@Conditional(MQProviderCondition.class)
public class MQProviderBoot {

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
			amc.setPassword(password);
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
	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory());
		jmsTemplate.setDeliveryPersistent(GatlinConfigration.get(CoreConsts.ACTIVEMQ_DELIVERY_PERSISTENT));
		jmsTemplate.setExplicitQosEnabled(GatlinConfigration.get(CoreConsts.ACTIVEMQ_EXPLICIT_QOS_ENABLED));
		return jmsTemplate;
	}
}
