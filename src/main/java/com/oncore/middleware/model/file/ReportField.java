package com.oncore.middleware.model.file;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oncore.middleware.model.Element;
import com.oncore.middleware.model.Field;


/**
 * Created by steve on 1/20/16.
 */
public class ReportField extends Element {
    @JsonIgnore
    private Report report;

    private String fieldType;

    private int length;

    private boolean ifNull;

    private boolean ifRelatedField;

    private Field relatedField;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Field getRelatedField() {
        return relatedField;
    }

    public void setRelatedField(Field relatedField) {
        this.relatedField = relatedField;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isIfNull() {
        return ifNull;
    }

    public void setIfNull(boolean ifNull) {
        this.ifNull = ifNull;
    }

    public boolean isIfRelatedField() {
        return ifRelatedField;
    }

    public void setIfRelatedField(boolean ifRelatedField) {
        this.ifRelatedField = ifRelatedField;
    }
}
