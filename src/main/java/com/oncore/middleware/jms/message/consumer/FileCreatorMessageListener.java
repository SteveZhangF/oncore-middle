package com.oncore.middleware.jms.message.consumer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oncore.middleware.fileCreator.*;
import com.oncore.middleware.jms.message.FileType;
import com.oncore.middleware.jms.message.sender.HtmlFileMessageSender;
import com.oncore.middleware.jms.message.sender.TableCreatorMessageSender;
import com.oncore.middleware.model.Entity;
import com.oncore.middleware.model.file.Report;
import freemarker.template.TemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by steve on 3/17/16.
 */


/**
 * create all the related file and send them to next queue
 * */
public class FileCreatorMessageListener implements MessageListener {
    @Autowired
    HibernateMappingFileCreator hibernateMappingFileCreator;
    @Autowired
    GroovyFileCreator groovyFileCreator;
    @Autowired
    ReportGroovyFileCreator reportGroovyFileCreator;
    @Autowired
    TableCreatorMessageSender tableCreatorMessageSender;
    @Autowired
    EntityHtmlFileCreator entityHtmlFileCreator;
    @Autowired
    ReportHtmlFileCreator reportHtmlFileCreator;
    @Autowired
    HtmlFileMessageSender htmlFileMessageSender;

    @Autowired
    TriggleFileCreator triggleFileCreator;

    Log log = LogFactory.getLog(FileCreatorMessageListener.class);

    //TODO need to refactor
    @Override
    public void onMessage(Message message) {
        TextMessage textMsg = (TextMessage) message;


        try {
//            System.out.print(textMsg.getText());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(textMsg.getText());
            boolean isReport = node.get("report").asBoolean();
            if (isReport) {

                Report report = mapper.readValue(textMsg.getText(), Report.class);
                log.info("creating report files..." + report.getName());

                log.info("creating related triggler for report " + report.getName());
                triggleFileCreator.addTrigger(report);
                //create mapping file
                File file = hibernateMappingFileCreator.createFile(report);
                //send to queue to create table
                tableCreatorMessageSender.sendMessage(file);
                //create report groovy file
                File reportDaoFile = reportGroovyFileCreator.createFile(report);
                //send groovy file to user end
                Map reportDaoMap = new HashMap();
                reportDaoMap.put("file", reportDaoFile);
                reportDaoMap.put("path", reportDaoFile.getParentFile().getName());
                reportDaoMap.put("type", FileType.ReportDao);
                reportDaoMap.put("name", reportDaoFile.getName());
                htmlFileMessageSender.sendMessage(reportDaoMap);
                //create report html file
                File html = reportHtmlFileCreator.createFile(report);
                //send html file to user end
                Map map = new HashMap();
                map.put("file", html);
                map.put("path", html.getParentFile().getName());
                map.put("type", FileType.ReportHtml);
                map.put("name", html.getName());

                htmlFileMessageSender.sendMessage(map);

            } else {
                Entity entity = mapper.readValue(textMsg.getText(), Entity.class);
                log.info("creating entity files..." + entity.getName());
                //create mapping file
                File file = hibernateMappingFileCreator.createFile(entity);
                //send to queue to create table
                tableCreatorMessageSender.sendMessage(file);


                //create groovy file
                File entityDaoFile = groovyFileCreator.createFile(entity);
                // send groovy file to user end
                Map entityDaoMap = new HashMap();
                entityDaoMap.put("file", entityDaoFile);
                entityDaoMap.put("path", entityDaoFile.getParentFile().getName());
                entityDaoMap.put("type", FileType.EntityDao);
                entityDaoMap.put("name", entityDaoFile.getName());
                log.info("sending entity files..." + entity.getName());
                htmlFileMessageSender.sendMessage(entityDaoMap);


                // create html file
                File entityHtml = entityHtmlFileCreator.createFile(entity);

                // send html file to user end
                Map entityHtmlMap = new HashMap();
                entityHtmlMap.put("file", entityHtml);
                entityHtmlMap.put("path", entityHtml.getParentFile().getName());
                entityHtmlMap.put("type", FileType.EntityHtml);
                entityHtmlMap.put("name", entityHtml.getName());
                htmlFileMessageSender.sendMessage(entityHtmlMap);


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
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
