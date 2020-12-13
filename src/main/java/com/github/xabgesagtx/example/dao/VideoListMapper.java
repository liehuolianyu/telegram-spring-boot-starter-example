package com.github.xabgesagtx.example.dao;

import com.github.xabgesagtx.example.entity.VideoList;

import java.util.List;

public interface VideoListMapper {
    int insert(VideoList record);

    int insertSelective(VideoList record);

    Integer selectCountByfileId(Integer fileSize);

    List<VideoList> selectByisSend(Integer state);

    Integer updateIsSendByFileid(String fileId);
}