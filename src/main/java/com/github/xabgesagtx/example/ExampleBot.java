package com.github.xabgesagtx.example;

import com.github.xabgesagtx.example.Service.DealPhotoMessage;
import com.github.xabgesagtx.example.Service.DealTextMessage;
import com.github.xabgesagtx.example.Service.DealVideoMessage;
import com.github.xabgesagtx.example.Service.impl.UserServiceimpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * This example bot is an echo bot that just repeats the messages sent to him
 *
 */
@Component
public class ExampleBot extends TelegramLongPollingBot {
	
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

	public  ExampleBot(){
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
			userServiceimpl.insert(update.getMessage());
			Message message = update.getMessage();
			//处理文字消息
			if (message.hasText()){
				//只处理“/”开头的数据
				if (message.getText().startsWith("/")){
					try {
						execute(textMessage.deal(message));
					} catch (TelegramApiException e) {
						logger.info("回复文本失败，具体原因："+e.toString());
					}
				}
			}
			if (message.hasPhoto()){
				try {
					execute(photoMessage.deal(message));
				} catch (TelegramApiException e) {
					logger.info("回复图片失败，具体原因："+e.toString());
				}
			}
			if (message.hasEntities()){


			}
			if(message.hasVideo()){
				try {
					execute(videoMessage.deal(message));
				} catch (TelegramApiException e) {
					logger.info("回复视频失败，具体原因："+e.toString());
				}
			}

		}
	}

	@PostConstruct
	public void start() {
		logger.info("username: {}, token: {}", username, token);
	}




}
