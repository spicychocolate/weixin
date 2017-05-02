package com.spicy.wechat.entity.wechat;
import java.io.Serializable;

/**
 * Created by kennedy on 2016/10/9.
 */
public class SendAllMsg implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Filter filter;
    private Text text;
    private String msgtype;
    private String touser;

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }
}
