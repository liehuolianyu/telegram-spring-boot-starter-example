package com.github.xabgesagtx.example.dao;

import com.github.xabgesagtx.example.entity.VideoList;

public interface VideoListMapper {
    int insert(VideoList record);

    int insertSelective(VideoList record);
}