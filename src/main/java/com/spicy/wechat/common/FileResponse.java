package com.spicy.wechat.common;


public class FileResponse {
    // 原文件名 包含文件后缀
    private String fileName; 
    // 新文件名 包含文件后缀
    private String newFileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }
}
