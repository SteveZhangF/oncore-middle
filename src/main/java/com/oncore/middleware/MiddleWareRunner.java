package com.oncore.middleware;

import com.amazonaws.util.StringInputStream;
import com.oncore.middleware.jms.message.consumer.TableCreatorMessageListener;
import com.sun.org.apache.xerces.internal.jaxp.SAXParserImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sun.net.www.http.HttpClient;
import sun.net.www.protocol.https.HttpsURLConnectionImpl;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;


@ComponentScan
public class MiddleWareRunner {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("classpath:spring-context.xml");

//        TableCreatorMessageListener tableCreatorMessageListener = context.getBean(TableCreatorMessageListener.class);
//        try {
//            tableCreatorMessageListener.createTrigger(new File("/Users/steve/Desktop/destination/mapping/entity_Employee_1458931383563/entity_Employee_1458931383563_trigger.sql"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        HtmlFileMessageSender htmlFileMessageSender = context.getBean(HtmlFileMessageSender.class);
//
//        File file = new File("/Users/steve/Desktop/destination/html/entities/entity_Employee_1458497984593/Employee.html");
//
//        Map map = new HashMap();
//        map.put("file",file);
//        String path =  file.getParentFile().getName();
//        map.put("path",path);
//        map.put("type","ENTITY");
//        map.put("name",file.getName());
//        htmlFileMessageSender.sendMessage(map);
    }
}
