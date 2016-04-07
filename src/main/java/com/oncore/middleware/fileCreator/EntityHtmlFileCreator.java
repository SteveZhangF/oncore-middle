package com.oncore.middleware.fileCreator;

import com.oncore.middleware.CommonConfigure;
import com.oncore.middleware.model.Entity;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by steve on 3/20/16.
 */
@Component("entityHtmlFileCreator")
public class EntityHtmlFileCreator extends FileCreator<Entity> {
    @Autowired
    public EntityHtmlFileCreator(CommonConfigure commonConfigure) {
        super(commonConfigure);
    }

    @Override
    public File getDestination(Entity t) throws IOException {
        File file = new File(commonConfigure.getBaseDir() + "/" + commonConfigure.getGenerated_file_destination_entity_html_file() + "/" + t.getTableName() + "/");
        if (!file.exists()) {
            file.mkdirs();
        }
//        String mappingFileName = t.getName().replace(" ","_") + ".jsp";
        String mappingFileName = "list.jsp";
        file = new File(commonConfigure.getBaseDir() + "/" + commonConfigure.getGenerated_file_destination_entity_html_file() + "/" + t.getTableName() + "/" + mappingFileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        log.info("created entity jsp file for " + t.getName() + " at " + file.getAbsolutePath());
        return file;
    }

    @Override
    protected Template getTemplate() {
        try {
            Template template = configuration.getTemplate(commonConfigure.getTemplate_entity_html_file());
            return template;
        } catch (IOException e) {
            log.info("reading entity list jsp template file error");
            return null;
        }
    }

    @Override
    protected Map getElementRoot(Entity t) {
        Map root = new HashMap();
        root.put("name", t.getName());
        root.put("tableName", t.getTableName());
        root.put("fields", t.getFields());
        return root;
    }
}
