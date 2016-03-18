package com.oncore.middleware.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by steve on 1/14/16.
 */
public class Entity extends Element implements TableElement {


    private String tableName;

    private String hbmPath;

    private List<Field> fields = new ArrayList<>();

    public String getHbmPath() {
        return hbmPath;
    }

    public void setHbmPath(String hbmPath) {
        this.hbmPath = hbmPath;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }



    public List<Field> getFields() {
        return fields;
    }
    private boolean report;
    public boolean isReport() {
        return false;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void addField(Field field) {
        this.fields.add(field);
        field.setEntity(this);
    }

    public void removeField(Field field) {
        for (Field field1 : fields) {
            if (field1.getId().equals(field.getId())) {
                fields.remove(field1);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (!tableName.equals(entity.tableName)) return false;
        if (!hbmPath.equals(entity.hbmPath)) return false;
        return fields.equals(entity.fields);

    }

    @Override
    public int hashCode() {
        int result = tableName.hashCode();
        result = 31 * result + hbmPath.hashCode();
        result = 31 * result + fields.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "tableName='" + tableName + '\'' +
                ", hbmPath='" + hbmPath + '\'' +
                ", fields=" + fields +
                "} " + super.toString();
    }
}
