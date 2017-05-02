package com.spicy.wechat.entity.wechat;

import java.io.Serializable;

/**
 * Created by kennedy on 2016/10/9.
 */
public class Filter implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Boolean is_to_all;
    private String group_id;

    public Boolean getIs_to_all() {
        return is_to_all;
    }

    public void setIs_to_all(Boolean is_to_all) {
        this.is_to_all = is_to_all;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}
