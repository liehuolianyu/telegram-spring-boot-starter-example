package com.github.xabgesagtx.example.utils;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ScheduleUtils {

    public void timerSendVideo();

    public void scheduleSendMessage(Integer userid,String text);

}
