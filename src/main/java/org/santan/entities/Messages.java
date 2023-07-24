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
      "Here are the available commands:\n"
          + "/start - Start the bot \n"
          + "/help - Show this help message \n"
            + "/go - Start suntan timer \n"
          + "/pause - Pause the timer\n"
          + "/reset - Reset the timer and start from the first level\n";
  public static final String HELP_MESSAGE_ES =
      "Aquí están los comandos disponibles:\n"
          + "/start - Iniciar el bot \n"
          + "/help - Mostrar este mensaje de ayuda \n"
          + "/go - Iniciar el avisador \n"
          + "/pause - Pausar el avisador\n"
          + "/reset -  Restablecer el avisador y comenzar desde el primer nivel.\n";
  public static final String HELP_MESSAGE_RU =
      "Доступные команды:\n"
          + "/start - Запустить бот \n"
          + "/help - Показать это сообщение с командами\n"
          + "/go - Запустить таймер \n"
          + "/pause - Поставить таймер на паузу\n"
          + "/reset - Сбросить таймер и начать с начала\n";
  public static final String START_MESSAGE_EN =
      "Hello! Welcome to San Tan bot. \n"
          + "I am here to help you to get a suntan as fast as possible. \n"
          + "To get started, please type /help to see a list of available commands.";

  public static final String START_MESSAGE_ES =
      "Hola! Bienvenido a San Tan bot. \n"
          + "Estoy aquí para ayudarte a broncearte más rápido como posible. \n"
          + "Para empezar, por favor escribe /help para ver una lista de comandos disponibles.";

  public static final String START_MESSAGE_RU =
      "Привет! Добро пожаловать в San Tan bot. \n"
          + "Я здесь, чтобы помочь тебе получить загар как можно быстрее. \n"
          + "Для того чтобы начать, пожалуйста используй команду /help, чтобы увидеть список всех доступных команд.";

  public static String getSessionIsNotActiveMessageWithLocale(String language) {
    String returnMessageText;
    switch (language == null ? "en" : language) {
      case "es":
        returnMessageText = SESSION_IS_NOT_ACTIVE_ES;
        break;
      case "ru":
        returnMessageText = SESSION_IS_NOT_ACTIVE_RU;
        break;
      default:
        returnMessageText = SESSION_IS_NOT_ACTIVE_EN;
        break;
    }
    return returnMessageText;
  }

  public static String getResetCompletedMessageWithLocale(String language) {
    String returnMessageText;
    switch (language == null ? "en" : language) {
      case "es":
        returnMessageText = RESET_COMPLETED_YOU_ARE_NOW_ON_THE_FIRST_LEVEL_ES;
        break;
      case "ru":
        returnMessageText = RESET_COMPLETED_YOU_ARE_NOW_ON_THE_FIRST_LEVEL_RU;
        break;
      default:
        returnMessageText = RESET_COMPLETED_YOU_ARE_NOW_ON_THE_FIRST_LEVEL_EN;
        break;
    }
    return returnMessageText;
  }

  public static String getTimerPausedMessageWithLocale(String language) {
    String returnMessageText;
    switch (language == null ? "en" : language) {
      case "es":
        returnMessageText = TIMER_PAUSED_ES;
        break;

      case "ru":
        returnMessageText = TIMER_PAUSED_RU;
        break;

      default:
        returnMessageText = TIMER_PAUSED_EN;
        break;
    }
    return returnMessageText;
  }

  public static String getStartMessageWithLocale(String language) {
    String returnMessageText;
    switch (language == null ? "en" : language) {
      case "es":
        returnMessageText = START_MESSAGE_ES;
        break;
      case "ru":
        returnMessageText = START_MESSAGE_RU;
        break;
      default:
        returnMessageText = START_MESSAGE_EN;
        break;
    }
    return returnMessageText;
  }

  public static String getHelpMessageWithLocale(String language) {
    String returnMessageText;
    switch (language == null ? "en" : language) {
      case "es":
        returnMessageText = HELP_MESSAGE_ES;
        break;
      case "ru":
        returnMessageText = HELP_MESSAGE_RU;
        break;
      default:
        returnMessageText = HELP_MESSAGE_EN;
        break;
    }
    return returnMessageText;
  }

  public static String getAlreadyInSessionMessageWithLocale(String language) {
    String returnMessageText;
    switch (language == null ? "en" : language) {
      case "es":
        returnMessageText = YOU_ARE_ALREADY_IN_A_SESSION_ES;
        break;
      case "ru":
        returnMessageText = YOU_ARE_ALREADY_IN_A_SESSION_RU;
        break;
      default:
        returnMessageText = YOU_ARE_ALREADY_IN_A_SESSION_EN;
        break;
    }
    return returnMessageText;
  }

  public static String getSomeErrorMessageWIthLocale(Session session) {
    String returnMessageText;
    switch (session.getLang() == null ? "en" : session. getLang()) {
        case "es":
            returnMessageText = SOME_ERROR_HAPPENED_ACTIVE_TIMER_RELAUNCHED_ES;
            break;
        case "ru":
          returnMessageText = SOME_ERROR_HAPPENED_ACTIVE_TIMER_RELAUNCHED_RU;
            break;
      default:
        returnMessageText = SOME_ERROR_HAPPENED_ACTIVE_TIMER_RELAUNCHED_EN;
        break;
    }
    return returnMessageText;
  }

  public static String getFinishMessageWithLocale(String language) {
    String returnMessageText ;
    switch (language == null? "en" : language) {
      case "es":
            returnMessageText = FINISH_MESSAGE_ES;
            break;
        case "ru":
            returnMessageText = FINISH_MESSAGE_RU;
            break;
        default:
            returnMessageText = FINISH_MESSAGE_EN;
            break;
    }
    return returnMessageText;
  }
}
