package org.gatlin.core.service.activemq;

import javax.jms.ConnectionFactory;

import org.gatlin.core.CoreConsts;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.condition.MQProviderCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@Conditional(MQProviderCondition.class)
public class ActiveMQProviderBoot {

	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory);
		jmsTemplate.setDeliveryPersistent(GatlinConfigration.get(CoreConsts.ACTIVEMQ_DELIVERY_PERSISTENT));
		jmsTemplate.setExplicitQosEnabled(GatlinConfigration.get(CoreConsts.ACTIVEMQ_EXPLICIT_QOS_ENABLED));
		return jmsTemplate;
	}
}
