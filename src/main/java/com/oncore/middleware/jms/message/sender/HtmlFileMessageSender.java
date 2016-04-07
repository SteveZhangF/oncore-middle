package com.oncore.middleware.jms.message.sender;

import com.oncore.middleware.jms.message.FileType;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.BlobMessage;
import org.apache.activemq.pool.PooledSession;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.File;
import java.util.Map;

/**
 * Created by steve on 3/21/16.
 */
@Component("htmlFileMessageSender")
public class HtmlFileMessageSender extends MessageSender<Map> {
    @Autowired
    public HtmlFileMessageSender(JmsTemplate jmsTemplate,@Qualifier(value = "htmlFileDestination") Destination d) {
        super(jmsTemplate);
        this.destination = d;
    }

    @Override
    protected MessageCreator getMessageCreator(final Map message) {


        return new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                org.apache.activemq.pool.PooledSession amqSession = (PooledSession) session;
                File file = (File) message.get("file");
                FileType type = (FileType) message.get("type");
                String name = (String) message.get("name");
                String path = (String) message.get("path");

                BlobMessage blobMessage = amqSession.getInternalSession().createBlobMessage(file);
                blobMessage.setStringProperty("TYPE",type.name());
                blobMessage.setStringProperty("NAME",name);
                blobMessage.setStringProperty("PATH",path);
                return blobMessage;
            }
        };
    }

}
