package com.github.xabgesagtx.example.entity;

import java.util.Date;

public class ScanRecord {
    private Integer id;

    private Integer userId;

    private Integer startNum;

    private Integer endNum;

    private Integer isFinish;

    private Date scanDate;

    private Date scanFinishDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}