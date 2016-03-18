package com.oncore.middleware.jms.message.consumer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oncore.middleware.fileCreator.GroovyFileCreator;
import com.oncore.middleware.fileCreator.HibernateMappingFileCreator;
import com.oncore.middleware.fileCreator.ReportGroovyFileCreator;
import com.oncore.middleware.jms.message.sender.TableCreatorMessageSender;
import com.oncore.middleware.model.Entity;
import com.oncore.middleware.model.file.Report;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.IOException;

/**
 * Created by steve on 3/17/16.
 */
public class FileCreatorMessageListener implements MessageListener {
    @Autowired
    HibernateMappingFileCreator hibernateMappingFileCreator;
    @Autowired
    GroovyFileCreator groovyFileCreator;
    @Autowired
    ReportGroovyFileCreator reportGroovyFileCreator;
    @Autowired
    TableCreatorMessageSender tableCreatorMessageSender;

    @Override
    public void onMessage(Message message) {
        TextMessage textMsg = (TextMessage) message;
        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode node = mapper.readTree(textMsg.getText());
            boolean isReport = node.get("report").asBoolean();
            if (isReport) {
                Report report = mapper.readValue(textMsg.getText(), Report.class);
                File file = hibernateMappingFileCreator.createFile(report);
                reportGroovyFileCreator.createFile(report);
                tableCreatorMessageSender.sendMessage(file);
            } else {
                Entity entity = mapper.readValue(textMsg.getText(), Entity.class);
                File file = hibernateMappingFileCreator.createFile(entity);
                groovyFileCreator.createFile(entity);
                tableCreatorMessageSender.sendMessage(file);
            }

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }
}
