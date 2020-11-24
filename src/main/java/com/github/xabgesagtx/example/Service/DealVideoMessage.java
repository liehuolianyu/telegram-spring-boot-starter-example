package com.github.xabgesagtx.example.Service;

import com.github.xabgesagtx.example.Service.impl.ChatListServiceImpl;
import com.github.xabgesagtx.example.Service.impl.VideoListServiceImpl;
import com.github.xabgesagtx.example.entity.ChatList;
import com.github.xabgesagtx.example.entity.VideoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
public class DealVideoMessage {

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
     * 处理消息转发
     * @param message
     * @return
     */
    public List<SendVideo> deal2(Message message){
        List<SendVideo> sendVideos = null;
        List<ChatList> chatLists =  chatListService.selectAll();
        VideoList videoList = new VideoList();
        videoList.setFileId(message.getVideo().getFileId());
        videoList.setFileSize(message.getVideo().getFileSize());
        videoList.setFileType(message.getVideo().getMimeType());
        videoList.setFileDesc(message.getCaption());
        videoListService.insert(videoList);
        for (ChatList chatList : chatLists){
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(chatList.getId());
            sendVideo.setVideo(new InputFile(message.getVideo().getFileId()));
            sendVideo.setCaption(message.getCaption());
            sendVideos.add(sendVideo);
        }
        /*sendVideo.setChatId("-1001387318488");*/
        return sendVideos;
    }

}
