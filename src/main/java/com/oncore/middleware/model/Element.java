package com.oncore.middleware.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by steve on 1/14/16.
 */
public class Element implements Serializable{
    private String id;
    private Timestamp createTime;
    private Timestamp updateTime;
    private boolean deleted;
    private String name;
    private String description;
    private String module_id;

    public String getModule_id() {
        return module_id;
    }

    public void setModule_id(String module_id) {
        this.module_id = module_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

//    public String toString(){
//        String result = "name" + name
//    }

    @Override
    public String toString() {
        return "Element{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        if (deleted != element.deleted) return false;
        if (id != null ? !id.equals(element.id) : element.id != null) return false;
        if (createTime != null ? !createTime.equals(element.createTime) : element.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(element.updateTime) : element.updateTime != null) return false;
        if (name != null ? !name.equals(element.name) : element.name != null) return false;
        return !(description != null ? !description.equals(element.description) : element.description != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (deleted ? 1 : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
