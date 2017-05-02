package com.spicy.wechat.common;

import java.io.Serializable;
import java.math.BigInteger;

public class PageRequest implements Serializable {
    private static final long serialVersionUID = -1197340126685312970L;
    private Integer pageSize;
    private Integer pageNo;
    private Integer startNo;
    private String keyword;
    private BigInteger loginedId;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getStartNo() {
        if (null != pageNo) {
            this.startNo = (pageNo - 1) * pageSize;
        }
        return startNo;
    }

    public BigInteger getLoginedId() {
        return loginedId;
    }

    public void setLoginedId(BigInteger loginedId) {
        this.loginedId = loginedId;
    }

}
