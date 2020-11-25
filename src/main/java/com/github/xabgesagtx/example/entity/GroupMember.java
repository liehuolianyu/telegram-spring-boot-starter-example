package com.github.xabgesagtx.example.entity;

public class GroupMember {
    private String chatId;

    private Integer userId;

    private Integer blackKeyword;

    private Integer state;

    private Integer photoCount;

    private Integer videoCount;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBlackKeyword() {
        return blackKeyword;
    }

    public void setBlackKeyword(Integer blackKeyword) {
        this.blackKeyword = blackKeyword;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(Integer photoCount) {
        this.photoCount = photoCount;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }
}