package com.oncore.middleware.model;

import java.io.Serializable;

/**
 * Created by steve on 3/18/16.
 */
public class ReportElement implements Serializable {
    public ReportElement(String reportTableName, String content) {
        this.reportTableName = reportTableName;
        this.content = content;
    }

    private String content;
    private String reportTableName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReportTableName() {
        return reportTableName;
    }

    public void setReportTableName(String report_id) {
        this.reportTableName = report_id;
    }
}
