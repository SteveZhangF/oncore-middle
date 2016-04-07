package com.oncore.middleware.jms.message.sender;

import com.oncore.middleware.model.TableElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.File;

/**
 * Created by steve on 3/18/16.
 */
@Component("tableCreatorMessageSender")
public class TableCreatorMessageSender extends MessageSender<File> {

    @Autowired
    public TableCreatorMessageSender(JmsTemplate jmsTemplate, @Qualifier(value = "tableCreatorDestination") Destination d) {
        super(jmsTemplate);
        this.destination = d;
    }

    @Override
    protected MessageCreator getMessageCreator(final File message) {
        return new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("path",message.getAbsolutePath());
                mapMessage.setString("name",message.getName());
                return mapMessage;
            }
        };
    }
}
