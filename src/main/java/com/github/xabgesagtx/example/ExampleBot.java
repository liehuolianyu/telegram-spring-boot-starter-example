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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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
                    if (message.getText().contains(OutputLine.blackline1) || message.getText().contains(OutputLine.blackline2)) {
                        if (groupMemberService.getBlackword(message) > 3) {
                            try {
                                execute(groupMemberService.kitoutMember(message));
                                execute(groupMemberService.sendWarning(message.getChatId(), "用户：" + message.getFrom().getFirstName() + "因发送违禁词超过三次被永久踢出群"));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        } else {
                            groupMemberService.blackwordAdd(message);
                            try {
                                execute(groupMemberService.sendWarning(message.getChatId(), "用户：" + message.getFrom().getFirstName() + ",您已发布了违禁词，若发满三次将被永久踢出群，请注意您的用词"));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } else {



                //管理员操作功能
                if (userServiceimpl.isAdmin(message.getFrom().getId())) {
                    if (message.hasText()) {
                        if (message.getText().startsWith(OutputLine.scan)) {
                            try {
                                execute(cloudSee2.dealMessage(message));
                            } catch (TelegramApiException e) {
                                logger.error("扫描处理失败，原因为：" + e.toString());
                            }
                        } else if (message.getText().startsWith("/")) {

                            //只处理“/”开头的数据
                            try {
                                execute(textMessage.deal(message));
                            } catch (TelegramApiException e) {
                                logger.error("回复文本失败，具体原因：" + e.toString());
                            }
                        }
                    }


                    if (message.hasVideo()) {
                        videoMessage.deal2(message);
                    }
                }


                //普通用户私聊处理

                else {
                    //处理文字消息
                    if (message.hasText()) {
                        //只处理“/”开头的数据
                        if (message.getText().startsWith("/")) {
                            try {
                                execute(textMessage.deal(message));
                            } catch (TelegramApiException e) {
                                logger.error("回复文本失败，具体原因：" + e.toString());
                            }
                        }
                    }
                    if (message.hasPhoto()) {
                        try {
                            execute(photoMessage.deal(message));
                        } catch (TelegramApiException e) {
                            logger.error("回复图片失败，具体原因：" + e.toString());
                        }
                    }
                    if (message.hasEntities()) {

                    }
                    if (message.hasVideo()) {
                        try {
                            execute(videoMessage.deal(message));
                        } catch (TelegramApiException e) {
                            logger.error("回复视频失败，具体原因：" + e.toString());
                        }
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
            List<ChatList> chatLists = chatListService.selectAll();
            if (!CollectionUtils.isEmpty(chatLists)) {
                for (ChatList chatList : chatLists) {
                    for (VideoList videoList : videoLists) {
                        sendVideo.setChatId(chatList.getId()).setVideo(new InputFile(videoList.getFileId()))
                                .setCaption(videoList.getFileDesc());
                        try {
                            execute(sendVideo);
                            videoListService.updateIsSendByFileid(videoList.getFileId());
                        } catch (TelegramApiException e) {
                            logger.error("主动发送视频报错了：" + e.toString());
                        }
                    }
                }
            }
        }
    }

}
