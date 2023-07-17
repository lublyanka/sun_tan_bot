package org.santan;

import java.sql.Timestamp;
import java.util.Timer;
import lombok.Getter;
import org.santan.entities.Session;
import org.santan.services.TimerService;

public class TimerTask extends java.util.TimerTask {

  private final Timer timer = new Timer();
  private final TimerService timerService;
  @Getter
  public final Session session;


  public TimerTask(TimerService timerService, Session session) {
    this.timerService = timerService;
    this.session = session;
  }

  @Override
  public void run() {
    timerService.runTimer(this);
  }

  public Timestamp scheduleTimer(long delayMS) {
    Timestamp finishTimerDate = new Timestamp(System.currentTimeMillis() + delayMS);

    // Schedule the timer task
    timer.schedule(this, delayMS);
    return finishTimerDate;
  }
}
