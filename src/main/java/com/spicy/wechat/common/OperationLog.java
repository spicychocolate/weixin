package com.spicy.wechat.common;

import java.io.Serializable;

// 操作日志
public class OperationLog implements Serializable {

    private static final long serialVersionUID = -3410153765799284975L;

    private String uuid;

    private String operationType;

    private String operationLog;

    private String createBy;

    private String createDate;

    private Integer pageNo;

    private Integer pageSize;

    private String billsNo; // 单据的操作日志里必须记录单据编号

    private String startDate; // 查询时，需要

    private String endDate; // 查询时候需要

    private String createUserName;

    private String nickName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationLog() {
        return operationLog;
    }

    public void setOperationLog(String operationLog) {
        this.operationLog = operationLog;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getBillsNo() {
        return billsNo;
    }

    public void setBillsNo(String billsNo) {
        this.billsNo = billsNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
