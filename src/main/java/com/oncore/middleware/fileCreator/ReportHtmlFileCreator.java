package com.oncore.middleware.fileCreator;

import com.oncore.middleware.CommonConfigure;
import com.oncore.middleware.model.file.Report;
import com.oncore.middleware.model.file.ReportField;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by steve on 3/20/16.
 */
@Component("reportHtmlFileCreator")
public class ReportHtmlFileCreator extends FileCreator<Report> {
    @Autowired
    public ReportHtmlFileCreator(CommonConfigure commonConfigure) {
        super(commonConfigure);
    }

    @Override
    public File getDestination(Report t) throws IOException {
        File file = new File(commonConfigure.getBaseDir()+"/"+commonConfigure.getGenerated_file_destination_report_html_file() + "/" + t.getTableName()+"/");
        if (!file.exists()) {
            file.mkdirs();
        }
        String mappingFileName = "list.jsp";
        file = new File(commonConfigure.getBaseDir()+"/"+commonConfigure.getGenerated_file_destination_report_html_file() + "/" + t.getTableName() + "/" + mappingFileName);
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
            Template template = configuration.getTemplate(commonConfigure.getTemplate_report_html_file());
            return template;
        } catch (IOException e) {
            log.info("reading report html template file error");
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
                map.put("name",field.getName());
                relatedFields.add(map);
            }
        }
        root.put("fields",report.getFields());
        root.put("tableName",tableName);
        root.put("name",name);
        root.put("relatedFields",relatedFields);
        root.put("nonRelatedFields",nonRelatedFields);
        return root;
    }
}
