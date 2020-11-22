package com.github.xabgesagtx.example.Service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class DealVideoMessage {

    public SendVideo deal(Message message){
        SendVideo video = new SendVideo();
        video.setVideo(new InputFile(message.getVideo().getFileId()) );
        video.setChatId(message.getChatId());

        return video;
    }
}
