package com.github.xabgesagtx.example.entity;

import java.util.Date;

public class ScanRecord {
    private Integer id;

    private String startHead;

    private Integer userId;

    private Integer startNum;

    private Integer endNum;

    private Integer isFinish;

    private Date scanDate;

    private Date scanFinishDate;

    private Date createDate;

    private Integer downloadFlag;

    private Integer resultCount;


    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartHead() {
        return startHead;
    }

    public void setStartHead(String startHead) {
        this.startHead = startHead == null ? null : startHead.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public Integer getEndNum() {
        return endNum;
    }

    public void setEndNum(Integer endNum) {
        this.endNum = endNum;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    public Date getScanDate() {
        return scanDate;
    }

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }

    public Date getScanFinishDate() {
        return scanFinishDate;
    }

    public void setScanFinishDate(Date scanFinishDate) {
        this.scanFinishDate = scanFinishDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDownloadFlag() {
        return downloadFlag;
    }

    public void setDownloadFlag(Integer downloadFlag) {
        this.downloadFlag = downloadFlag;
    }
}