package org.santan.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor()
public class OnStartBot {

  private final TimerService timerService;

  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event) {
    timerService.onStart();

  }
}
