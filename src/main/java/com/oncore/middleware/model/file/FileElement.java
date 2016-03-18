/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.oncore.middleware.model.file;


import com.oncore.middleware.model.Element;

/**
 * Created by steve on 2/12/16.
 */
public class FileElement extends Element {


    private String parentFoldId;

    public String getParentFoldId() {
        return parentFoldId;
    }

    public void setParentFoldId(String parentFoldId) {
        this.parentFoldId = parentFoldId;
    }
}
