package com.oncore.middleware.model;

import java.util.List;

/**
 * Created by steve on 3/17/16.
 */
public interface TableElement {

    public String getTableName();

    public String getHbmPath();

    public String getName();

    public List getFields();
    public boolean isReport();
}
