package org.santan.services;

import org.santan.SanTanBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class OnStartBot {

  @Autowired SessionService sessionService;

  @Autowired private TimerService timerService;
  @Autowired private SanTanBot send;

  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event) {
    timerService.onStart(a -> send.executeSafe(a));
  }
}
