package com.rcaller.service.impl;

import javax.annotation.Resource;

import com.rcaller.bot.RCallerLongPollingBot;
import com.rcaller.service.BotActivator;
import com.rcaller.service.healtcheck.RCallerHealtCheckService;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;


/**
 * Created by Mikhail_Asadchy (EPAM)
 */
public class BotActivatorImpl implements BotActivator {


   private RCallerLongPollingBot rCallerLongPollingBot;

   @Resource
   private RCallerHealtCheckService rCallerHealtCheckServiceImpl;

   @Value("${rcaller.rcallerChatId}")
   private String rcallerChatId;
   @Value("${rcaller.botUserName}")
   private String botUserName;
   @Value("${rcaller.botToken}")
   private String botToken;


   @Override
   public void activateChatBot() {
      ApiContextInitializer.init();

      TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

      try {
         telegramBotsApi.registerBot(new RCallerLongPollingBot(rCallerHealtCheckServiceImpl, rcallerChatId, botUserName, botToken));
      }
      catch (TelegramApiRequestException e) {
         e.printStackTrace();
      }
   }
}
