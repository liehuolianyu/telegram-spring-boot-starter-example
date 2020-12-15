package com.github.xabgesagtx.example.Service;

import com.github.xabgesagtx.example.ExampleBot;
import com.github.xabgesagtx.example.Service.impl.ChatListServiceImpl;
import com.github.xabgesagtx.example.Service.impl.VideoListServiceImpl;
import com.github.xabgesagtx.example.entity.ChatList;
import com.github.xabgesagtx.example.entity.VideoList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealVideoMessage {

    private static final Logger logger = LoggerFactory.getLogger(ExampleBot.class);



    @Autowired
    VideoListServiceImpl videoListService;

    @Autowired
    ChatListServiceImpl chatListService;

    public SendVideo deal(Message message){
        SendVideo video = new SendVideo();
        video.setVideo(new InputFile(message.getVideo().getFileId()) );
        video.setChatId(message.getChatId());

        return video;
    }

    /**
     * 转存视频消息
     * @param message
     * @return
     */
    public void deal2(Message message){

/*        List<ChatList> chatLists =  chatListService.selectAll();*/
        VideoList videoList = new VideoList();
        if (!videoListService.isExists(message.getVideo().getFileSize())){
            videoList.setFileId(message.getVideo().getFileId());
            videoList.setFileSize(message.getVideo().getFileSize());
            videoList.setFileType(message.getVideo().getMimeType());
            videoList.setFileDesc(message.getCaption());
            videoListService.insert(videoList);
        }
/*        for (ChatList chatList : chatLists){
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(chatList.getId());
            sendVideo.setVideo(new InputFile(message.getVideo().getFileId()));
            sendVideo.setCaption(message.getCaption());
            sendVideos.add(sendVideo);
        }*/
        /*sendVideo.setChatId("-1001387318488");*/
    }


    /**
     * 定时任务取视频表中获取数据发送
     * @param message
     * @return
     */
/*    public void deal3( ){
        List<VideoList> videoLists =   videoListService.selectNotSend(0);
        if (!CollectionUtils.isEmpty(videoLists) ){
            SendVideo sendVideo =new SendVideo();
            List<ChatList> chatLists = chatListService.selectAll();
            if (!CollectionUtils.isEmpty(chatLists) ){
                for (ChatList chatList : chatLists){
                    for (VideoList videoList : videoLists){
                        sendVideo.setChatId(chatList.getId()).setVideo(new InputFile(videoList.getFileId()))
                                .setCaption(videoList.getFileDesc()) ;
                        try {
                            execute(sendVideo);
                        } catch (TelegramApiException e) {
                            logger.error("主动发送视频报错了：" + e.toString());
                        }
                    }
                }
            }
        }

}*/
}
