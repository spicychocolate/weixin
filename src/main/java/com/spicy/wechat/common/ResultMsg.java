package com.spicy.wechat.common;

import java.io.Serializable;

public class ResultMsg implements Serializable {

    private static final long serialVersionUID = -9197926144517133339L;
    /**
     * 处理成功标记
     */
    private boolean success;
    /**
     * 返回的消息
     */
    private String msg;

    private String billNumber;

    private Object o; 

    public ResultMsg() {
    }

    public ResultMsg(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public ResultMsg(boolean success, String msg, String billNumber) {
        this.success = success;
        this.msg = msg;
        this.billNumber = billNumber;
    }
    
    public ResultMsg(Object o, boolean success, String msg) {
        this.success = success;
        this.msg = msg;
        this.o = o;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
}
