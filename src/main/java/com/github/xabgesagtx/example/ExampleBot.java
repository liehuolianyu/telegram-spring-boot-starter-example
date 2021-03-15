package com.github.xabgesagtx.example;

import com.github.xabgesagtx.example.Service.DealCloudSee2;
import com.github.xabgesagtx.example.Service.DealPhotoMessage;
import com.github.xabgesagtx.example.Service.DealTextMessage;
import com.github.xabgesagtx.example.Service.DealVideoMessage;
import com.github.xabgesagtx.example.Service.impl.ChatListServiceImpl;
import com.github.xabgesagtx.example.Service.impl.GroupMemberServiceImpl;
import com.github.xabgesagtx.example.Service.impl.UserServiceimpl;
import com.github.xabgesagtx.example.Service.impl.VideoListServiceImpl;
import com.github.xabgesagtx.example.entity.ChatList;
import com.github.xabgesagtx.example.entity.VideoList;
import com.github.xabgesagtx.example.utils.OutputLine;
import com.github.xabgesagtx.example.utils.ScheduleUtils;
import com.github.xabgesagtx.example.utils.SensitiveWordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.groupadministration.RestrictChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * This example bot is an echo bot that just repeats the messages sent to him
 */
@Component
public class ExampleBot extends TelegramLongPollingBot implements ScheduleUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExampleBot.class);


    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    @Value("${bot.blackword}")
    private int blackword;


    @Autowired
    private DealPhotoMessage photoMessage;

    @Autowired
    private DealTextMessage textMessage;

    @Autowired
    private DealVideoMessage videoMessage;

    @Autowired
    UserServiceimpl userServiceimpl;

    @Autowired
    ChatListServiceImpl chatListService;

    @Autowired
    DealCloudSee2 cloudSee2;

    @Autowired
    VideoListServiceImpl videoListService;

    @Autowired
    GroupMemberServiceImpl groupMemberService;


    public ExampleBot() {
/*		DefaultBotOptions options = new DefaultBotOptions();
		options.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
		options.setProxyPort(666);
		options.setProxyHost("test.yangge666.top");*/
        super(new DefaultBotOptions());
    }


    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Async
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            //若不存在用户表则插入
            if (!userServiceimpl.isExists(message.getFrom().getId())) {
                userServiceimpl.insert(message);
            }

            //群组消息处理
            if (message.getChat().isGroupChat() || message.getChat().isSuperGroupChat()) {
                if (message.getNewChatMembers().size() > 0) {
                    groupMemberService.dealNewMember(message);
                }
                if (!groupMemberService.isExists(message)) {
                    groupMemberService.insert(message);
                }
                if (message.hasText()) {
                    if (SensitiveWordUtil.contains(message.getText())) {
                        if (groupMemberService.getBlackword(message) >= blackword) {
                            try {
                                execute(groupMemberService.kitoutMember(message));
                                execute(groupMemberService.sendWarning(message.getChatId(), "用户：" + message.getFrom().getFirstName() + "因发送违禁词超过三次被永久踢出群"));

                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        } else {
                            groupMemberService.blackwordAdd(message);
                            sendMessage(groupMemberService.sendWarning(message.getChatId(), "用户：" + message.getFrom().getFirstName() + ",您已发布了违禁词，若发满三次将被永久踢出群，请注意您的用词！！！"+"\n已撤回违禁消息"));

                                if (message.getChat().isSuperGroupChat()){
                                    restrictChatMember(groupMemberService.modifyUser(message));
                                    deleteMessage(groupMemberService.deleteMessage(message));
                                }


                        }
                    }else if (message.getText().startsWith("/scan")){

                    }
                }
            } else {



                //管理员操作功能
                if (userServiceimpl.isAdmin(message.getFrom().getId())) {
                    if (message.hasText()) {
                        if (message.getText().startsWith(OutputLine.scan)) {
                            sendMessage(cloudSee2.dealMessage(message,true));
                        } else if (message.getText().startsWith(OutputLine.addSensitiveWord)){
                            sendMessage(textMessage.add(message));
                        }


                            else if (message.getText().startsWith("/")) {

                            //只处理“/”开头的数据

                            sendMessage(textMessage.deal(message));

                        }else if (message.hasVideo()){


                        }

                            else {
                            if (SensitiveWordUtil.contains(message.getText())){
                                sendMessage(new SendMessage().setChatId(message.getChatId()).setText("包含敏感词"));
                            }
                        }
                    }


                    if (message.hasVideo()) {
                        videoMessage.deal2(message);


/*                        SendVideo sendVideo = new SendVideo().setChatId(message.getChatId()).setVideo(new InputFile(message.getVideo().getFileId()));
                        sendVideo.setCaption("<a href=\"http://www.runoob.com\">访问菜鸟教程!</a>");
                        sendVideo.setParseMode("HTML");
                        sendVideo(sendVideo);*/


                    }
                }


                //普通用户私聊处理

                else {
                    //处理文字消息
                    if (message.hasText()) {
                        //只处理“/”开头的数据
                        if (message.getText().startsWith("/")) {

                          sendMessage(textMessage.deal(message));
                        }
                    }
                    if (message.hasPhoto()) {

                        sendPhoto(photoMessage.deal(message));

                    }
                    if (message.hasEntities()) {

                    }
                    if (message.hasVideo()) {
                        sendVideo(videoMessage.deal(message));
                    }

                }
            }
        }

    }

    @PostConstruct
    public void start() {
        logger.info("username: {}, token: {}", username, token);
    }

    @Override
    public void timerSendVideo() {
        List<VideoList> videoLists = videoListService.selectNotSend(0);
        if (!CollectionUtils.isEmpty(videoLists)) {
            SendVideo sendVideo = new SendVideo();
            ReplyKeyboard keyboard = new ReplyKeyboardMarkup();
            List<ChatList> chatLists = chatListService.selectAll();
            if (!CollectionUtils.isEmpty(chatLists)) {
                for (ChatList chatList : chatLists) {
                    for (VideoList videoList : videoLists) {
                        sendVideo.setChatId(chatList.getId()).setVideo(new InputFile(videoList.getFileId()));
                               /* .setCaption(videoList.getFileDesc());*/

                        sendVideo(sendVideo);
                        videoListService.updateIsSendByFileid(videoList.getFileId());
                    }
                }
            }
        }
    }

    @Override
    public void timerSendMessage(String message) {
        List<ChatList> chatLists = chatListService.selectAll();
        for (ChatList chatList : chatLists){
           SendMessage sendMessage = new SendMessage();
           sendMessage.setChatId(chatList.getId()).setText(message);
           sendMessage(sendMessage);

        }
    }


    public void sendMessage(SendMessage sendMessage)  {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("发送文本<<"+sendMessage.getText().toString()+">>失败，具体原因：" + e.toString());
        }
    }


    public void sendVideo(SendVideo sendVideo){
        try {
            execute(sendVideo);
        } catch (TelegramApiException e) {
            logger.error("发送视频失败，具体原因：" + e.toString());
        }
    }

    public void sendPhoto(SendPhoto sendPhoto){
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            logger.error("发送图片失败，具体原因：" + e.toString());
        }
    }

    public void restrictChatMember(RestrictChatMember restrictMember){
        try {
            execute(restrictMember);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void deleteMessage(DeleteMessage deleteMessage){
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
