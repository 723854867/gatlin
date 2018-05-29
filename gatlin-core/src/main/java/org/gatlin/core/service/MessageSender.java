package org.gatlin.core.service;

import org.gatlin.core.bean.model.message.Message;
import org.gatlin.core.bean.model.message.SchedulerMessage;

public interface MessageSender {

	void send(String destination, Message message);
	
	void send(String destination, SchedulerMessage message);
}
