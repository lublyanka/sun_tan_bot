package org.santan.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.event.ContextRefreshedEvent;

class OnStartBotTest {
  TimerService timerService = mock(TimerService.class);
  OnStartBot onStartBot = new OnStartBot(timerService);

  @Test
  void onApplicationEvent() {
    ContextRefreshedEvent event = mock(ContextRefreshedEvent.class);

    onStartBot.onApplicationEvent(event);
    verify(timerService, times(1)).onStart();
  }
}
