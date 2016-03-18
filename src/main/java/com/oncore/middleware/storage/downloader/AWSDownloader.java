package com.oncore.middleware.storage.downloader;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.oncore.middleware.CommonConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * Created by steve on 3/17/16.
 */


/**
 * Created by steve on 3/17/16.
 */
@Component("awsDownloader")
public class AWSDownloader extends DownLoader {
    @Autowired
    public AWSDownloader(CommonConfigure commonConfigure) {

        super(commonConfigure);
        final String awsAccessKey = commonConfigure.getAws_access_key();
        final String awsSecretKey = commonConfigure.getAws_secret_key();
        bucket = commonConfigure.getAws_report_template_bucket_name();
        awsCredentials = new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return awsAccessKey;
            }

            @Override
            public String getAWSSecretKey() {
                return awsSecretKey;
            }
        };

        s3Service = new AmazonS3Client(awsCredentials);
    }

    AmazonS3Client s3Service;
    String bucket;
    AWSCredentials awsCredentials;

    @Override
    public String getDownloadUrl(String key) {
        Date now = new Date(new Date().getTime() + 1000 * 60 * 60);
        String url = s3Service.generatePresignedUrl(bucket, key, now).toString();
        return url;
    }

    @Override
    public String getReportContent(String targetName) throws IOException {
        return null;
    }
}
