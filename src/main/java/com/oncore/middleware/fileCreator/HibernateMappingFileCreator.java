package com.oncore.middleware.fileCreator;

import com.oncore.middleware.CommonConfigure;
import com.oncore.middleware.model.TableElement;
import freemarker.template.Template;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by steve on 3/6/16.
 */
@Component("hibernateMappingFileCreator")
public class HibernateMappingFileCreator extends FileCreator<TableElement> {
    Log log = LogFactory.getLog(HibernateMappingFileCreator.class);
    @Autowired
    public HibernateMappingFileCreator(CommonConfigure commonConfigure) {
        super(commonConfigure);
    }

    @Override
    public File getDestination(TableElement t) throws IOException {
        File file = new File(commonConfigure.getBaseDir()+"/"+commonConfigure.getGenerated_file_destination_hibernate_mapping_file() + "/" + t.getHbmPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        String mappingFileName = t.getName() + ".xml";
        file = new File(commonConfigure.getBaseDir()+"/"+commonConfigure.getGenerated_file_destination_hibernate_mapping_file() + "/" + t.getHbmPath() + "/" + mappingFileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        log.info("created hibernate mapping file for "+t.getName()+" at " +file.getAbsolutePath());
        return file;
    }

    @Override
    protected Template getTemplate() {
        try {
            Template template = configuration.getTemplate(commonConfigure.getTemplate_hibernate_mapping_file());
            return template;
        } catch (IOException e) {
            log.info("reading hibernate mapping template file error");
            return null;
        }

    }

    @Override
    protected Map getElementRoot(TableElement t) {
        Map root = new HashMap();
        root.put("name", t.getName());
        root.put("tableName", t.getTableName());
        root.put("fields", t.getFields());
        return root;
    }
}

