package com.github.xabgesagtx.example.Service.impl;


import com.github.xabgesagtx.example.dao.VideoListMapper;
import com.github.xabgesagtx.example.entity.VideoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoListServiceImpl {

    @Autowired
    VideoListMapper videoListMapper;

   public int insert(VideoList videoList){
        return videoListMapper.insert(videoList);
    }


    public boolean isExists(Integer fileSize){
       boolean flag = false;
       if (videoListMapper.selectCountByfileId(fileSize)>0){
           flag = true;
       }
       return flag;
    }

}
