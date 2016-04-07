package com.oncore.middleware.fileCreator;

import com.oncore.middleware.CommonConfigure;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by steve on 3/6/16.
 */

/**
 * file creator
 * to create the related file based on T
 *
 * all file creator should extends this class and implement
 *  getDestination(T t): the generated path
 *  getTemplate() : the template
 *  getElementRoot(T t): the element root map.
 *
 * invoke:
 *  fileCreator.createFile(t);
 *
 *
 * */
public abstract class FileCreator<T> {

    Log log = LogFactory.getLog(FileCreator.class);
    protected Configuration configuration;

    protected CommonConfigure commonConfigure;

    public FileCreator(CommonConfigure commonConfigure){
        this.commonConfigure = commonConfigure;

    }

    public abstract File getDestination(T t) throws IOException;

    protected abstract Template getTemplate();

    protected abstract Map getElementRoot(T t);

    public File createFile(T t) throws IOException, TemplateException {
        if (configuration == null) {
            configuration = new Configuration();
            File file = new File(commonConfigure.getBaseDir() + "/" + commonConfigure.getTemplateBasePath());
            if (!file.exists() || !file.isDirectory()) {
                file.delete();
                file.mkdir();
            }
            try {
                configuration.setDirectoryForTemplateLoading(file);
            } catch (IOException e) {
                log.error("setting freemarker configuration error");
                e.printStackTrace();
            }
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        }

        File file = getDestination(t);
        Template template = getTemplate();
        Map root = getElementRoot(t);
        FileWriter fileWriter = new FileWriter(file);
        template.process(root, fileWriter);
        fileWriter.flush();
        fileWriter.close();
        log.info("created file at "+ file.getAbsolutePath());
        return file;
    }
}
