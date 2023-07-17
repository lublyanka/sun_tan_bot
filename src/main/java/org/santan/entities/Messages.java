package org.santan.entities;

public class Messages {
  public static final String TURN_TO_THE_NEXT_SIDE_MESSAGE =
      "Turn to the %s side for %3.1f minutes (level %d)";
  public static final String FINISH_MESSAGE =
      "You finished you session completely! Congratulations! \n"
          + "If you want you can start a new one by typing /go";
  public static final String TIMER_PAUSED = "Timer paused";
  public static final String SESSION_IS_NOT_ACTIVE = "Session is not active";
  public static final String RESET_COMPLETED_YOU_ARE_NOW_ON_THE_FIRST_LEVEL =
      "Reset completed. You are now on the first level";
  public static final String SOME_ERROR_HAPPENED_ACTIVE_TIMER_RELAUNCHED =
      "Some error happened, active timer relaunched";
  public static final String YOU_ARE_ALREADY_IN_A_SESSION = "You are already in a session!";
  public static final String HELP_MESSAGE =
      "Here are the available commands:\n"
          + "/start - Start the bot \n"
          + "/help - Show this help message \n"
          + "/go - Start suntan timer \n"
          + "/pause - Pause the timer\n"
          + "/reset - Reset the timer and start from the first level\n";
  public static final String START_MESSAGE =
      "Hello! Welcome to San Tan bot. \n"
          + "I am here to help you to get a suntan s fast as possible. \n"
          + "To get started, please type /help to see a list of available commands.";
}
