package com.oncore.middleware.storage.uploader;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.oncore.middleware.CommonConfigure;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by steve on 3/17/16.
 */
@Component("aWSUploader")
public class AWSUpLoader extends UpLoader {

    Log log = LogFactory.getLog(AWSUpLoader.class);
    String bucketName;
    AmazonS3 s3client;
    @Autowired
    public AWSUpLoader(CommonConfigure commonConfigure) {
        super(commonConfigure);
        init();
    }

    String awsAccessKey = null;
    String awsSecretKey = null;

    @Override
    public void init() {

        if (commonConfigure != null) {
            awsAccessKey = commonConfigure.getAws_access_key();
            awsSecretKey = commonConfigure.getAws_secret_key();
            bucketName = commonConfigure.getAws_report_template_bucket_name();
        } else {
            awsAccessKey = "AKIAJ7ZCYOJ3575ULHMA";
            awsSecretKey = "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";
            bucketName = "oncore-template";
        }


        AWSCredentials awsCredentials = new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return awsAccessKey;
            }

            @Override
            public String getAWSSecretKey() {
                return awsSecretKey;
            }
        };
        s3client = new AmazonS3Client(awsCredentials);
    }



    @Override
    public void upload(String content, String targetName) {

        try {
            File temp = File.createTempFile
                    ("report_", ".html");
            temp.deleteOnExit();
            BufferedWriter out = new BufferedWriter
                    (new FileWriter(temp));
            out.write(content);
            out.close();
            s3client.putObject(new PutObjectRequest(
                    bucketName, targetName, temp));
            log.info("uploaded file "+ targetName);

        } catch (AmazonServiceException ase) {
            log.error("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            log.error("Error Message:    " + ase.getMessage());
            log.error("HTTP Status Code: " + ase.getStatusCode());
            log.error("AWS Error Code:   " + ase.getErrorCode());
            log.error("Error Type:       " + ase.getErrorType());
            log.error("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            log.error("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            log.error("Error Message: " + ace.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args){
        AWSUpLoader awsUpLoader = new AWSUpLoader(null);
        awsUpLoader.upload("access_test","report_updatedmodule_1458434245796");
    }

}
