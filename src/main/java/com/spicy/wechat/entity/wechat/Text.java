package com.spicy.wechat.entity.wechat;

import java.io.Serializable;

/**
 * Created by kennedy on 2016/10/9.
 */
public class Text implements Serializable {
    private static final long serialVersionUID = -2456803666625116815L;

    private String content;

    public Text() {
        super();
    }

    public Text(String content) {
        super();
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
