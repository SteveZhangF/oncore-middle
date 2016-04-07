package com.oncore.middleware.jms.message.consumer;

import com.oncore.middleware.fileCreator.TriggleFileCreator;
import freemarker.template.TemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.sql.DataSource;
import java.io.*;
import java.nio.Buffer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by steve on 3/18/16.
 */
public class TableCreatorMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        MapMessage textMessage = (MapMessage) message;
        try {
            String name = textMessage.getString("name");
            String path = textMessage.getString("path");
            File file = new File(path);
            if(file.getName().toLowerCase().endsWith(".sql")){
                createTrigger(file);
            }else{
                createTable(file);
            }

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Autowired
    DataSource dataSource;
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    TriggleFileCreator triggleFileCreator;

    Log log = LogFactory.getLog(TableCreatorMessageListener.class);
    public void createTrigger(File file) throws SQLException, IOException {
        Connection connection = dataSource.getConnection();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String buf = "";
        StringBuffer stringBuffer = new StringBuffer();
        buf = bufferedReader.readLine();
        // drop trigger
        connection.createStatement().executeUpdate(buf);
        while((buf = bufferedReader.readLine())!=null){
            stringBuffer.append(buf);
            stringBuffer.append("\n");
        }
        log.info("creating trigger "+stringBuffer.toString());
        //create trigger
        connection.createStatement().executeUpdate(stringBuffer.toString());
        connection.close();
    }

    public void createTable(File file) throws SQLException, IOException, TemplateException {
        org.hibernate.cfg.Configuration conf = new org.hibernate.cfg.Configuration();
//        conf.configure("/META-INF/springHibernate.xml");
        Properties extraProperties = new Properties();
        extraProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        extraProperties.put("hibernate.hbm2ddl.auto", "update");
        extraProperties.put("hibernate.show_sql", "false");
        extraProperties.put("hibernate.format_sql", "false");
//        conf.addProperties(extraProperties);

        conf.addFile(file);

        org.hibernate.service.ServiceRegistry serviceRegistry = sessionFactory.getSessionFactoryOptions().getServiceRegistry();

        conf.addProperties(extraProperties);

        String tableName = file.getParentFile().getName();

        // if the table is not exists then create one
        if (!checkTableExist(tableName)) {
            SchemaExport export = new SchemaExport(conf, dataSource.getConnection());
            export.setOutputFile(file.getParentFile().getAbsolutePath() + "/" + tableName);
            export.create(false, true);
        } else {
            SchemaUpdate dbExport = new SchemaUpdate(serviceRegistry, conf);
            dbExport.setOutputFile(file.getParentFile().getAbsolutePath() + "/" + tableName);
            dbExport.execute(false, true);
        }
        //triggleFileCreator.createFile(tableName);
    }

    private boolean checkTableExist(String tableName) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(null, null, tableName, new String[]{"TABLE"});
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            log.error("SQLException "+e.getMessage());
        }
        return false;
    }
}
