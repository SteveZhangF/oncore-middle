package com.oncore.middleware.jms.message.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oncore.middleware.storage.downloader.DownLoader;
import com.oncore.middleware.storage.uploader.UpLoader;

import javax.jms.*;
import java.io.IOException;

/**
 * Created by steve on 3/18/16.
 */

/**
 * upload the report template to cloud storage (S3)
 * */
public class ReportTemplateMessageListener implements MessageListener {

    private UpLoader upLoader;
    private DownLoader downLoader;

    public DownLoader getDownLoader() {
        return downLoader;
    }

    public void setDownLoader(DownLoader downLoader) {
        this.downLoader = downLoader;
    }

    public UpLoader getUpLoader() {
        return upLoader;
    }

    public void setUpLoader(UpLoader upLoader) {
        this.upLoader = upLoader;
    }

    @Override
    public void onMessage(Message message) {
        TextMessage mapMessage = (TextMessage) message;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(mapMessage.getText());
            String reportTableName = root.get("reportTableName").asText();
            String content = root.get("content").asText();
            upLoader.upload(content,reportTableName);

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
