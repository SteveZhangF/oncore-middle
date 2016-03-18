package com.oncore.middleware.jms.message.sender;

/**
 * Created by steve on 3/17/16.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;

public abstract class MessageSender<T> {


    JmsTemplate jmsTemplate;

    @Autowired
    public MessageSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    protected Destination destination;

    protected abstract MessageCreator getMessageCreator(T message);

    public void sendMessage(final T message) {
        jmsTemplate.send(destination, getMessageCreator(message));
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

}

