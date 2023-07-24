package org.santan.entities;

public class Messages {
  public static final String FINISH_MESSAGE_EN =
      "You finished you session completely! Congratulations! \n"
          + "If you want you can start a new one by typing /go";
  public static final String FINISH_MESSAGE_ES =
          "¡Terminaste tu sesión completa! ¡Felicidades! \n"
                  + "Si lo desea, puede iniciar uno nuevo escribiendo /go";
  public static final String FINISH_MESSAGE_RU =
          "Вы завершили сеанс полностью! Поздравляем! \n"
                  + "Если вы хотите, вы можете начать новую, набрав /go";
  public static final String TIMER_PAUSED_EN = "Timer paused";
  public static final String TIMER_PAUSED_ES = "El avisador está pausado";
  public static final String TIMER_PAUSED_RU = "Таймер поставлен на паузу";
  public static final String SESSION_IS_NOT_ACTIVE_EN = "Session is not active";
  public static final String SESSION_IS_NOT_ACTIVE_ES = "La sesión no está activa";
  public static final String SESSION_IS_NOT_ACTIVE_RU = "Сессия не активна";
  public static final String RESET_COMPLETED_YOU_ARE_NOW_ON_THE_FIRST_LEVEL_EN =
      "Reset completed. You are now on the first level";
  public static final String RESET_COMPLETED_YOU_ARE_NOW_ON_THE_FIRST_LEVEL_ES =
      "Restablecimiento completado. Ahora estás en el primer nivel.";
  public static final String RESET_COMPLETED_YOU_ARE_NOW_ON_THE_FIRST_LEVEL_RU =
      "Сброс завершен. Вы сейчас на первом уровне";
  public static final String SOME_ERROR_HAPPENED_ACTIVE_TIMER_RELAUNCHED_EN =
      "Some error happened, active timer relaunched";
  public static final String SOME_ERROR_HAPPENED_ACTIVE_TIMER_RELAUNCHED_ES =
          "Un error occurió, el avisador activo ha reiniciado";
  public static final String SOME_ERROR_HAPPENED_ACTIVE_TIMER_RELAUNCHED_RU =
          "Произошла ошибка, таймер перезапущен";
  public static final String YOU_ARE_ALREADY_IN_A_SESSION_EN = "You are already in a session!";
  public static final String YOU_ARE_ALREADY_IN_A_SESSION_ES = "Ya estás en una sesión!";
  public static final String YOU_ARE_ALREADY_IN_A_SESSION_RU = "Вы уже в сессии!";
  public static final String HELP_MESSAGE_EN =
          """
                  Here are the available commands:
                  /start - Start the bot\s
                  /help - Show this help message\s
                  /go - Start suntan timer\s
                  /pause - Pause the timer
                  /reset - Reset the timer and start from the first level
                  """;
  public static final String HELP_MESSAGE_ES =
          """
                  Aquí están los comandos disponibles:
                  /start - Iniciar el bot\s
                  /help - Mostrar este mensaje de ayuda\s
                  /go - Iniciar el avisador\s
                  /pause - Pausar el avisador
                  /reset -  Restablecer el avisador y comenzar desde el primer nivel.
                  """;
  public static final String HELP_MESSAGE_RU =
          """
                  Доступные команды:
                  /start - Запустить бот\s
                  /help - Показать это сообщение с командами
                  /go - Запустить таймер\s
                  /pause - Поставить таймер на паузу
                  /reset - Сбросить таймер и начать с начала
                  """;
  public static final String START_MESSAGE_EN =
          """
                  Hello! Welcome to San Tan bot.\s
                  I am here to help you to get a suntan as fast as possible.\s
                  To get started, please type /help to see a list of available commands.""";

  public static final String START_MESSAGE_ES =
          """
                  Hola! Bienvenido a San Tan bot.\s
                  Estoy aquí para ayudarte a broncearte más rápido como posible.\s
                  Para empezar, por favor escribe /help para ver una lista de comandos disponibles.""";

  public static final String START_MESSAGE_RU =
          """
                  Привет! Добро пожаловать в San Tan bot.\s
                  Я здесь, чтобы помочь тебе получить загар как можно быстрее.\s
                  Для того чтобы начать, пожалуйста используй команду /help, чтобы увидеть список всех доступных команд.""";

  public static String getSessionIsNotActiveMessageWithLocale(String language) {
    return switch (language == null ? "en" : language) {
      case "es" -> SESSION_IS_NOT_ACTIVE_ES;
      case "ru" -> SESSION_IS_NOT_ACTIVE_RU;
      default -> SESSION_IS_NOT_ACTIVE_EN;
    };
  }

  public static String getResetCompletedMessageWithLocale(String language) {
    return switch (language == null ? "en" : language) {
      case "es" -> RESET_COMPLETED_YOU_ARE_NOW_ON_THE_FIRST_LEVEL_ES;
      case "ru" -> RESET_COMPLETED_YOU_ARE_NOW_ON_THE_FIRST_LEVEL_RU;
      default -> RESET_COMPLETED_YOU_ARE_NOW_ON_THE_FIRST_LEVEL_EN;
    };
  }

  public static String getTimerPausedMessageWithLocale(String language) {
    return switch (language == null ? "en" : language) {
      case "es" -> TIMER_PAUSED_ES;
      case "ru" -> TIMER_PAUSED_RU;
      default -> TIMER_PAUSED_EN;
    };
  }

  public static String getStartMessageWithLocale(String language) {
    String returnMessageText = switch (language == null ? "en" : language) {
      case "es" -> START_MESSAGE_ES;
      case "ru" -> START_MESSAGE_RU;
      default -> START_MESSAGE_EN;
    };
    return returnMessageText;
  }

  public static String getHelpMessageWithLocale(String language) {
    return switch (language == null ? "en" : language) {
      case "es" -> HELP_MESSAGE_ES;
      case "ru" -> HELP_MESSAGE_RU;
      default -> HELP_MESSAGE_EN;
    };
  }

  public static String getAlreadyInSessionMessageWithLocale(String language) {
    return switch (language == null ? "en" : language) {
      case "es" -> YOU_ARE_ALREADY_IN_A_SESSION_ES;
      case "ru" -> YOU_ARE_ALREADY_IN_A_SESSION_RU;
      default -> YOU_ARE_ALREADY_IN_A_SESSION_EN;
    };
  }

  public static String getSomeErrorMessageWIthLocale(Session session) {
    return switch (session.getLang() == null ? "en" : session.getLang()) {
      case "es" -> SOME_ERROR_HAPPENED_ACTIVE_TIMER_RELAUNCHED_ES;
      case "ru" -> SOME_ERROR_HAPPENED_ACTIVE_TIMER_RELAUNCHED_RU;
      default -> SOME_ERROR_HAPPENED_ACTIVE_TIMER_RELAUNCHED_EN;
    };
  }

  public static String getFinishMessageWithLocale(String language) {
    return switch (language == null ? "en" : language) {
      case "es" -> FINISH_MESSAGE_ES;
      case "ru" -> FINISH_MESSAGE_RU;
      default -> FINISH_MESSAGE_EN;
    };
  }
}
