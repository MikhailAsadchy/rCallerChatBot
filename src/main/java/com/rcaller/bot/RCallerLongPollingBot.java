package com.rcaller.bot;

import com.rcaller.service.healtcheck.RCallerHealtCheckService;
import com.rcaller.service.healtcheck.impl.RCallerHealtCheckServiceImpl;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


/**
 * Created by Mikhail_Asadchy (EPAM)
 */
public class RCallerLongPollingBot extends TelegramLongPollingBot {

   private String rcallerChatId;
   private String botUserName;
   private String botToken;

   private RCallerHealtCheckService rCallerHealtCheckService;

   public RCallerLongPollingBot(final RCallerHealtCheckService rCallerHealtCheckService, final String rcallerChatId,
         final String botUserName, final String botToken) {
      this.rCallerHealtCheckService = rCallerHealtCheckService;
      this.rcallerChatId = rcallerChatId;
      this.botUserName = botUserName;
      this.botToken = botToken;
   }

   public void reportHealthCheckStatus() {
      final String healthCheckStatus = rCallerHealtCheckService.getHealthCheckStatus();
      sendMessageToRCallerChat(healthCheckStatus);
   }

   @Override
   public void onUpdateReceived(final Update update) {
      reportHealthCheckStatus();
   }

   private void sendMessageToRCallerChat(final String message) {
      final SendMessage sendMessage = new SendMessage();
      sendMessage.setChatId(rcallerChatId);
      sendMessage.setText(message);
      try {
         sendMessage(sendMessage);
      }
      catch (TelegramApiException e) {
         e.printStackTrace();
      }
   }

   @Override
   public String getBotUsername() {
      return botUserName;
   }

   @Override
   public String getBotToken() {
      return botToken;
   }
}
