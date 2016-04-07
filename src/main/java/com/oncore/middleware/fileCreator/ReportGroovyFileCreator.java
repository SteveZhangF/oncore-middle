package com.oncore.middleware.fileCreator;

import com.oncore.middleware.CommonConfigure;
import com.oncore.middleware.model.file.Report;
import com.oncore.middleware.model.file.ReportField;
import freemarker.template.Template;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by steve on 3/10/16.
 */
@Component("reportGroovyFileCreator")
public class ReportGroovyFileCreator extends FileCreator<Report> {

    Log log = LogFactory.getLog(ReportGroovyFileCreator.class);

    @Autowired
    public ReportGroovyFileCreator(CommonConfigure commonConfigure) {
        super(commonConfigure);
    }

    @Override
    public File getDestination(Report t) throws IOException {
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
        log.info("created report groovy dao file for "+t.getName()+" at " +file.getAbsolutePath());
        return file;
    }

    @Override
    protected Template getTemplate() {
        try {
            Template template = configuration.getTemplate(commonConfigure.getTemplate_report_groovy_dao_file());
            return template;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("reading report groovy dao template file error");
            return null;
        }
    }

    @Override
    protected Map getElementRoot(Report report) {
        Map root = new HashMap<>();
        String tableName = report.getTableName();
        String name = report.getName();
        List relatedFields = new ArrayList<>();
        List nonRelatedFields = new ArrayList<>();
        for(ReportField field:report.getFields()){
            if(!field.isIfRelatedField()){
                nonRelatedFields.add(field);
            }else{
                Map map=new HashMap<>();
                map.put("tableName",field.getRelatedField().getTableName());
                map.put("field_name",field.getRelatedField().getName());
                map.put("name",field.getName().replace(" ","_"));
                relatedFields.add(map);
            }
        }
        root.put("fields",report.getFields());
        root.put("tableName",tableName);
        root.put("name",name.replace(" ","_"));
        root.put("relatedFields",relatedFields);
        root.put("nonRelatedFields",nonRelatedFields);
        return root;
    }
}
