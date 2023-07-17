package org.santan.services;

import org.santan.SanTanBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OnStartBot {

  @Autowired private TimerService timerService;
  @Autowired private SanTanBot sanTanBot;

  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event) {
    timerService.onStart(sendMessage -> sanTanBot.executeSafe(sendMessage));
  }
}
