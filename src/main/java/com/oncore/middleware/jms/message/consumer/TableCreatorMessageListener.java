package com.oncore.middleware.jms.message.consumer;

import org.hibernate.SessionFactory;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.*;
import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by steve on 3/18/16.
 */
public class TableCreatorMessageListener  implements MessageListener {
    @Override
    public void onMessage(Message message) {
        MapMessage textMessage = (MapMessage) message;
        try {
            String name = textMessage.getString("name");
            String path = textMessage.getString("path");
            File file = new File(path);
            createTable(file);
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Autowired
    DataSource dataSource;
    @Autowired
    SessionFactory sessionFactory;

    public void createTable(File file) throws SQLException {
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
                e.printStackTrace();
            }
            return false;
        }
}
