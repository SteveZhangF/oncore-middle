package com.oncore.middleware.model;



/**
 * Created by steve on 1/14/16.
 */
public class Field extends Element {

    private String fieldType;

    private int length;

    private boolean ifNull;

    private Entity entity;

    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isIfNull() {
        return ifNull;
    }

    public void setIfNull(boolean ifNull) {
        this.ifNull = ifNull;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Field{" +
                "ifNull=" + ifNull +
                ", length=" + length +
                ", fieldType='" + fieldType + '\'' +
                "} " + super.toString();
    }
}
