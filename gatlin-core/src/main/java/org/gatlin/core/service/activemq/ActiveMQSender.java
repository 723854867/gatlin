package org.gatlin.core.service.activemq;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.gatlin.core.Gatlin;
import org.gatlin.core.bean.enums.Env;
import org.gatlin.core.bean.model.message.SchedulerMessage;
import org.gatlin.core.condition.MQProviderCondition;
import org.gatlin.core.service.MessageSender;
import org.gatlin.util.IpUtil;
import org.springframework.context.annotation.Conditional;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * Spring activeMQ 消息发送类
 * 
 * @author lynn
 */
@Component
@Conditional(MQProviderCondition.class)
public class ActiveMQSender implements MessageSender {

	@Resource
	private Gatlin gatlin;
	@Resource
	private JmsTemplate jmsTemplate;
	@Resource
	private SchedulerMessageProcessor schedulerMessageProcessor;
	
	protected MessageCreator generateObjectCreator(Serializable message) {
		return new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage msg = session.createObjectMessage();
				msg.setObject(message);
				return msg;
			}
		};
	}
	
	protected MessageCreator generateTextCreator(String message) {
		return new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		};
	}
	
	protected void sendMessage(String queueName, MessageCreator creator) {
		jmsTemplate.send(queueName, creator);
	}
	
	protected void sendMessage(String queueName, SchedulerMessage message) {
		jmsTemplate.convertAndSend(queueName, message, schedulerMessageProcessor);
	}

	@Override
	public void send(String destination, org.gatlin.core.bean.model.message.Message message) {
		jmsTemplate.send(_destination(destination), generateObjectCreator(message));
	}

	@Override
	public void send(String destination, SchedulerMessage message) {
		jmsTemplate.convertAndSend(_destination(destination), message, schedulerMessageProcessor);
	}
	
	private String _destination(String destination) {
		return gatlin.env() == Env.LOCAL ? destination + "_" + IpUtil.hostname() : destination;
	}
}
