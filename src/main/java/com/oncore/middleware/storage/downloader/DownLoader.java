package com.oncore.middleware.storage.downloader;

import com.oncore.middleware.CommonConfigure;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by steve on 3/6/16.
 */
public abstract class DownLoader {
    CommonConfigure commonConfigure;
    Log log = LogFactory.getLog(DownLoader.class);
    @Autowired
    public DownLoader(CommonConfigure commonConfigure) {
        this.commonConfigure = commonConfigure;
    }
    public abstract String getDownloadUrl(String key);

    public abstract String getReportContent(String targetName) throws IOException;


}
