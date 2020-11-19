package com.github.xabgesagtx.example;

import com.github.xabgesagtx.example.Service.DealCloudSee2;
import com.github.xabgesagtx.example.Service.DealGoLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.github.xabgesagtx.example.utils.*;
import javax.annotation.PostConstruct;

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
	private  DealCloudSee2 dealCloudSee;

	@Autowired
	private DealGoLink goLink;



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
	
	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage()) {
			Message message = update.getMessage();
			SendMessage response = new SendMessage();
			Long chatId = message.getChatId();
			response.setChatId(chatId);
			String text = message.getText();
			//停止加速器
			if("/stop".equals(message.getText())){
				response.setText(goLink.stopSpeedState());
			}
			//扫描
			else  if (text.startsWith("H")){
				dealCloudSee.execute(text);
				response.setText(OutputLine.line1);
			}
				else{
				response.setText(text);
			}
			try {
				execute(response);
				logger.info("Sent message \"{}\" to {}", response.getText(), chatId);
			} catch (TelegramApiException e) {
				logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
			}
		}
	}

	@PostConstruct
	public void start() {
		logger.info("username: {}, token: {}", username, token);
	}

}
