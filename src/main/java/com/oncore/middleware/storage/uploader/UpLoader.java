package com.oncore.middleware.storage.uploader;

import com.oncore.middleware.CommonConfigure;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by steve on 3/6/16.
 */
public abstract class UpLoader {

    protected CommonConfigure commonConfigure;

    @Autowired
    public UpLoader(CommonConfigure commonConfigure) {
        this.commonConfigure = commonConfigure;
    }


    public abstract void init();



    public abstract void upload(String content,String targetName) ;
}
