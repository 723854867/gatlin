package org.gatlin.core.service.activemq;

import javax.jms.ConnectionFactory;

import org.gatlin.core.CoreConsts;
import org.gatlin.core.GatlinConfigration;
import org.gatlin.core.condition.MQConsumerCondition;
import org.gatlin.util.lang.StringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Conditional(MQConsumerCondition.class)
public class ActiveMQConsumerBoot {

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
	public MessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {
		DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
		messageListenerContainer.setConnectionFactory(connectionFactory);
		messageListenerContainer.setDestinationName(StringUtil.EMPTY);
		messageListenerContainer.setReceiveTimeout((long) GatlinConfigration.get(CoreConsts.ACTIVEMQ_RECEIVE_TIMEOUT));
		messageListenerContainer.setTaskExecutor(taskExecutor());
		messageListenerContainer.setConcurrentConsumers(GatlinConfigration.get(CoreConsts.ACTIVEMQ_CONCURRENT_CONSUMERS));
		return messageListenerContainer;
	}
}
