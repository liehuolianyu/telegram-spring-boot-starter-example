package com.github.xabgesagtx.example.Service.impl;


import com.github.xabgesagtx.example.dao.VideoListMapper;
import com.github.xabgesagtx.example.entity.VideoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoListServiceImpl {

    @Autowired
    VideoListMapper videoListMapper;

    /**
     * 插入视频列表
     * @param videoList
     * @return
     */
   public int insert(VideoList videoList){
        return videoListMapper.insert(videoList);
    }

    /**
     * 判断是否存在
     * @param fileSize
     * @return
     */
    public boolean isExists(Integer fileSize){
       boolean flag = false;
       if (videoListMapper.selectCountByfileId(fileSize)>0){
           flag = true;
       }
       return flag;
    }

    /**
     * 获取一条没有发送的视频
     * @param state
     * @return
     */
    public List<VideoList> selectNotSend(Integer state){
       return videoListMapper.selectByisSend(state);
    }

    public void updateIsSendByFileid(String fileId){
        videoListMapper.updateIsSendByFileid(fileId);
    }

}
