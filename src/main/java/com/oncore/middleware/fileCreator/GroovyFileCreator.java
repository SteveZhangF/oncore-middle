package com.oncore.middleware.fileCreator;

import com.oncore.middleware.CommonConfigure;
import com.oncore.middleware.model.TableElement;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by steve on 3/6/16.
 */
@Component("groovyFileCreator")
public class GroovyFileCreator extends FileCreator<TableElement> {


    @Autowired
    public GroovyFileCreator(CommonConfigure commonConfigure) {
        super(commonConfigure);
    }

    @Override
    public File getDestination(TableElement t) throws IOException {

        File file = new File(commonConfigure.getBaseDir()+"/"+commonConfigure.getGenerated_file_destination_groovy_dao_file() + "/" + t.getHbmPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        String mappingFileName = t.getName().replace(" ","_") + ".groovy";
        file = new File(commonConfigure.getBaseDir()+"/"+commonConfigure.getGenerated_file_destination_groovy_dao_file() + "/" + t.getHbmPath() + "/" + mappingFileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        log.info("created groovy dao file for "+t.getName()+" at " +file.getAbsolutePath());
        return file;
    }

    @Override
    protected Template getTemplate() {
        try {
            Template template = configuration.getTemplate(commonConfigure.getTemplate_groovy_dao_file());
            return template;
        } catch (IOException e) {
            log.info("reading groovy dao template file error");
            return null;
        }
    }

    @Override
    protected Map getElementRoot(TableElement o) {
        Map root = new HashMap<>();
        root.put("name", o.getName().replace(" ","_"));
        root.put("tableName", o.getTableName());
        root.put("fields", o.getFields());
        return root;
    }
}
