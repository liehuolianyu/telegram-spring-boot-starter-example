package com.github.xabgesagtx.example.Service;

import com.github.xabgesagtx.example.entity.SensitiveWord;
import com.github.xabgesagtx.example.utils.OutputLine;
import com.github.xabgesagtx.example.utils.SensitiveWordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashSet;


@Service
public class DealTextMessage {

    @Autowired
    DealCloudSee2 dealCloudSee;

    @Autowired
    DealGoLink goLink;


    @Autowired
    SensitiveWordService sensitiveWordService;

    public SendMessage deal(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        if (message.getText().equals(OutputLine.getchat)){
            sendMessage.setText("https://t.me/joinchat/Guv0lEednyPhC9m1BTGeeQ");
        }
        else {
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
        }
        return sendMessage;
    }

    public SendMessage add(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        SensitiveWord sensitiveWord = new SensitiveWord();

        String sensitiveName =  message.getText().replace(OutputLine.addSensitiveWord,"");
        if (!StringUtils.isEmpty(sensitiveName)){
            sensitiveWord.setName(sensitiveName);
            sensitiveWord.setRemark("管理员添加");
            sensitiveWordService.insert(sensitiveWord);
            sendMessage.setText("添加违禁词成功");
            SensitiveWordUtil.init(new HashSet<>(sensitiveWordService.getAllSensitiveWord()));
        }else {
            sendMessage.setText("添加违禁词失败");
        }

        return sendMessage;
    }

}
