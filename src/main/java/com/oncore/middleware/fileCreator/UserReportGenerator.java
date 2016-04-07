package com.oncore.middleware.fileCreator;

import com.oncore.middleware.CommonConfigure;
import com.oncore.middleware.model.TableElement;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by steve on 3/9/16.
 */

/**
 * moved to user project
 * */
@Component
public class UserReportGenerator extends FileCreator<TableElement> {

    @Autowired
    public UserReportGenerator(CommonConfigure commonConfigure) {
        super(commonConfigure);
    }

    @Override
    public File getDestination(TableElement tableElement) throws IOException {
        return null;
    }

    @Override
    protected Template getTemplate() {
        return null;
    }

    @Override
    protected Map getElementRoot(TableElement tableElement) {
        return null;
    }
}
