package com.oncore.middleware;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by steve on 3/4/16.
 */
@Configuration
public class CommonConfigure {


    @Value("${base_dir}")
    private String baseDir;

    @Value("${template.base_path}")
    private String templateBasePath;
    @Value("${create_table_thread_number}")
    private int create_table_thread_number;
    @Value("${template.hibernate_mapping_file}")
    private String template_hibernate_mapping_file;

    @Value("${generated_file_destination.hibernate_mapping_file}")
    private String generated_file_destination_hibernate_mapping_file;
    @Value("${generated_file_destination.groovy_dao_file}")
    private String generated_file_destination_groovy_dao_file;
    @Value("${template.template_groovy_dao_file}")
    private String template_groovy_dao_file;
    @Value("${template.template_report_groovy_dao_file}")
    private String template_report_groovy_dao_file;

    @Value("${qiniu_access_key}")
    private String qiniu_access_key;
    @Value("${qiniu_secret_key}")
    private String qiniu_secret_key;

    @Value("${qiniu_bucket_name}")
    private String qiniu_bucket_name;

    @Value("${qiniu_download_domain}")
    private String qiniu_down_load_domain;

    @Value("${report_template_file_path}")
    private String report_template_file_path;
    @Value("${aws_report_template_bucket_name}")
    private String aws_report_template_bucket_name;
    @Value("${aws_access_key}")
    private String aws_access_key;
    @Value("${aws_secret_key}")
    private String aws_secret_key;

    public String getAws_report_template_bucket_name() {
        return aws_report_template_bucket_name;
    }

    public void setAws_report_template_bucket_name(String aws_report_template_bucket_name) {
        this.aws_report_template_bucket_name = aws_report_template_bucket_name;
    }

    public String getAws_access_key() {
        return aws_access_key;
    }

    public void setAws_access_key(String aws_access_key) {
        this.aws_access_key = aws_access_key;
    }

    public String getAws_secret_key() {
        return aws_secret_key;
    }

    public void setAws_secret_key(String aws_secret_key) {
        this.aws_secret_key = aws_secret_key;
    }

    public String getReport_template_file_path() {
        return report_template_file_path;
    }

    public void setReport_template_file_path(String report_template_file_path) {
        this.report_template_file_path = report_template_file_path;
    }

    public String getQiniu_bucket_name() {
        return qiniu_bucket_name;
    }

    public String getQiniu_down_load_domain() {
        return qiniu_down_load_domain;
    }

    public void setQiniu_down_load_domain(String qiniu_down_load_domain) {
        this.qiniu_down_load_domain = qiniu_down_load_domain;
    }

    public void setQiniu_bucket_name(String qiniu_bucket_name) {
        this.qiniu_bucket_name = qiniu_bucket_name;
    }

    public String getQiniu_access_key() {
        return qiniu_access_key;
    }

    public void setQiniu_access_key(String qiniu_access_key) {
        this.qiniu_access_key = qiniu_access_key;
    }

    public String getQiniu_secret_key() {
        return qiniu_secret_key;
    }

    public void setQiniu_secret_key(String qiniu_secret_key) {
        this.qiniu_secret_key = qiniu_secret_key;
    }

    public int getCreate_table_thread_number() {
        return create_table_thread_number;
    }

    public void setCreate_table_thread_number(int create_table_thread_number) {
        this.create_table_thread_number = create_table_thread_number;
    }

    public String getTemplateBasePath() {
        return templateBasePath;
    }

    public void setTemplateBasePath(String templateBasePath) {
        this.templateBasePath = templateBasePath;
    }


    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getTemplate_hibernate_mapping_file() {
        return template_hibernate_mapping_file;
    }

    public void setTemplate_hibernate_mapping_file(String template_hibernate_mapping_file) {
        this.template_hibernate_mapping_file = template_hibernate_mapping_file;
    }

    public String getGenerated_file_destination_hibernate_mapping_file() {
        return generated_file_destination_hibernate_mapping_file;
    }

    public void setGenerated_file_destination_hibernate_mapping_file(String generated_file_destination_hibernate_mapping_file) {
        this.generated_file_destination_hibernate_mapping_file = generated_file_destination_hibernate_mapping_file;
    }

    public String getTemplate_groovy_dao_file() {
        return template_groovy_dao_file;
    }

    public void setTemplate_groovy_dao_file(String template_groovy_dao_file) {
        this.template_groovy_dao_file = template_groovy_dao_file;
    }

    public String getGenerated_file_destination_groovy_dao_file() {
        return generated_file_destination_groovy_dao_file;
    }

    public void setGenerated_file_destination_groovy_dao_file(String generated_file_destination_groovy_dao_file) {
        this.generated_file_destination_groovy_dao_file = generated_file_destination_groovy_dao_file;
    }


    public String getTemplate_report_groovy_dao_file() {
        return template_report_groovy_dao_file;
    }

    public void setTemplate_report_groovy_dao_file(String template_report_groovy_dao_file) {
        this.template_report_groovy_dao_file = template_report_groovy_dao_file;
    }
}
