package com;

import com.rcaller.service.BotActivator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by Mikhail_Asadchy (EPAM)
 */
public class Main {

   public static void main(String[] args) {
      ApplicationContext app = new ClassPathXmlApplicationContext("spring.xml");

      BotActivator botActivator = (BotActivator) app.getBean("botActivatorImpl");

      botActivator.activateChatBot();
   }

}
