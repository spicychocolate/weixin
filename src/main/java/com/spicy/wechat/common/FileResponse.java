package com.spicy.wechat.common;

/**
 * @Description: 上传文件返回对象
 * @date: 2016年10月12日 上午11:05:16
 * @author: zengt
 * @version: 1.0
 */
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
