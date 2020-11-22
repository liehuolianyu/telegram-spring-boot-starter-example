package com.github.xabgesagtx.example.Service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.util.Comparator;
import java.util.List;

@Service
public class DealPhotoMessage {

    public SendPhoto deal(Message message){
        SendPhoto photo = new SendPhoto();
        List<PhotoSize> photos = message.getPhoto();
        String f_id = photos.stream()
                .max(Comparator.comparing(PhotoSize::getFileSize))
                .orElseThrow().getFileId();
        photo.setChatId(message.getChatId());
        photo.setPhoto(new InputFile(f_id));
        return photo;
    }
}
