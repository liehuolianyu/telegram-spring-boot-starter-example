package com.github.xabgesagtx.example.Service;

import com.github.xabgesagtx.example.utils.OutputLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;



@Service
public class DealTextMessage {

    @Autowired
    DealCloudSee2 dealCloudSee;

    @Autowired
    DealGoLink goLink;

    public SendMessage deal(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        //停止加速器
/*        if("/stop".equals(message.getText())){
            sendMessage.setText(goLink.stopSpeedState());
        }
        //扫描
        else  if (message.getText().startsWith("H")){
            dealCloudSee.execute(message.getText());
            sendMessage.setText(OutputLine.line1);
        }
        else{*/
            sendMessage.setText(message.getText());
        return sendMessage;
    }

}
