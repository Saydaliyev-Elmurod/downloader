package org.example.bot.util;

import lombok.extern.log4j.Log4j2;
import org.example.bot.config.Bot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Log4j2
public class Sender {
  public static void send(Object o, Bot bot) {
    try {
      if (o instanceof SendPhoto) {
        bot.execute((SendPhoto) o);
      } else if (o instanceof SendMessage) {
        bot.execute((SendMessage) o);
      } else if (o instanceof EditMessageText) {
        bot.execute((EditMessageText) o);
      } else if (o instanceof AnswerCallbackQuery) {
        bot.execute((AnswerCallbackQuery) o);
      } else if (o instanceof SendLocation) {
        bot.execute((SendLocation) o);
      }
    } catch (Exception ignored) {
    }
  }

  public static void editMsgEntity(
      Long chatId,
      Integer messageId,
      List<MessageEntity> entities,
      String message,
      InlineKeyboardMarkup button,
      Bot bot) {
    EditMessageText sendMessage = new EditMessageText();
    sendMessage.setMessageId(messageId);
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    sendMessage.setEntities(entities);
    sendMessage.setReplyMarkup(button);
    bot.send(sendMessage);
    try {
      bot.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
  }

  public static void editCaptionStyle(
      Long chatId, Integer messageId, String message, InlineKeyboardMarkup button, Bot bot) {
    EditMessageCaption editMessageCaption = new EditMessageCaption();
    editMessageCaption.setChatId(chatId);
    editMessageCaption.setMessageId(messageId);
    editMessageCaption.setCaption(message);
    editMessageCaption.setReplyMarkup(button);
    editMessageCaption.setParseMode("HTML");
    try {
      bot.execute(editMessageCaption);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
  }

  public static void editMessageStyle(
      Long chatId, Integer messageId, String message, InlineKeyboardMarkup button, Bot bot) {
    EditMessageText editMessageCaption = new EditMessageText();
    editMessageCaption.setChatId(chatId);
    editMessageCaption.setMessageId(messageId);
    editMessageCaption.setText(message);
    editMessageCaption.setReplyMarkup(button);
    editMessageCaption.setParseMode("HTML");
    try {
      bot.execute(editMessageCaption);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
  }

  public static void editMessageStyleHtml(Long chatId, Integer messageId, String message, Bot bot) {
    EditMessageText editMessageCaption = new EditMessageText();
    editMessageCaption.setChatId(chatId);
    editMessageCaption.setMessageId(messageId);
    editMessageCaption.setText(message);
    editMessageCaption.setParseMode("HTML");
    try {
      bot.execute(editMessageCaption);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
  }

  public static void editMessageStyleHtml(
      Long chatId, Integer messageId, String message, InlineKeyboardMarkup button, Bot bot) {
    EditMessageText editMessageCaption = new EditMessageText();
    editMessageCaption.setChatId(chatId);
    editMessageCaption.setMessageId(messageId);
    editMessageCaption.setText(message);
    editMessageCaption.setReplyMarkup(button);
    editMessageCaption.setParseMode("HTML");
    try {
      bot.execute(editMessageCaption);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
  }


  public static Message sendMsg(
      Long chatId, String message, ReplyKeyboard replyKeyboard, Bot bot, Integer replyId) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    sendMessage.setReplyMarkup(replyKeyboard);
    sendMessage.setReplyToMessageId(replyId);
    try {
      return bot.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
    return null;
  }

  public static Message sendMsg(Long chatId, String message, ReplyKeyboard replyKeyboard, Bot bot) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    sendMessage.setReplyMarkup(replyKeyboard);
    try {
      return bot.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
    return null;
  }

  public static Message sendMsg(
      Long chatId, String message, InlineKeyboardMarkup replyKeyboard, Bot bot) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    sendMessage.setReplyMarkup(replyKeyboard);
    try {
      return bot.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
    return null;
  }

  public static Integer sendMsgStyle(
      Long chatId, String message, ReplyKeyboard replyKeyboard, Bot bot, Integer replyId) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    sendMessage.setParseMode("HTML");
    sendMessage.setReplyMarkup(replyKeyboard);
    sendMessage.setReplyToMessageId(replyId);
    try {
      return bot.execute(sendMessage).getMessageId();
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
    return -1;
  }

  public static Integer sendMsgStyle(
      Long chatId, String message, ReplyKeyboard replyKeyboard, Bot bot) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    sendMessage.setParseMode("HTML");
    sendMessage.setReplyMarkup(replyKeyboard);
    try {
      return bot.execute(sendMessage).getMessageId();
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
    return -1;
  }

  public static Integer sendMsgStyleHtml(
      Long chatId, String message, ReplyKeyboard replyKeyboard, Bot bot) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    sendMessage.setParseMode("HTML");
    sendMessage.setReplyMarkup(replyKeyboard);
    try {
      return bot.execute(sendMessage).getMessageId();
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
    return -1;
  }

  public static Integer sendMsgStyleHtml(Long chatId, String message, Bot bot) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    sendMessage.setParseMode("HTML");
    try {
      return bot.execute(sendMessage).getMessageId();
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
    return -1;
  }

  public static Message sendMsg(Long chatId, String message, Bot bot) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    try {
      return bot.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
    return null;
  }

  public static Message sendMsg(Long chatId, String message, Bot bot, Integer replyId) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    sendMessage.setReplyToMessageId(replyId);
    try {
      return bot.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
    return null;
  }

  public static Message sendMsgStyle(Long chatId, String message, Bot bot, Integer replyId) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    sendMessage.setParseMode("HTML");
    sendMessage.setReplyToMessageId(replyId);
    try {
      return bot.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
    return null;
  }

  public static Message sendMsgStyle(Long chatId, String message, Bot bot) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    sendMessage.setParseMode("HTML");
    try {
      return bot.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
    return null;
  }

  public static Message sendMsgMarkdown(Long chatId, String message, Bot bot) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    sendMessage.setParseMode("MARKDOWN");
    try {
      return bot.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
    return null;
  }

  public static void sendMsgMarkdown(Long chatId, String message, Bot bot, Integer threadId) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    sendMessage.enableNotification();
    //    sendMessage.setProtectContent(true);
    sendMessage.setMessageThreadId(threadId);
    sendMessage.setParseMode("MARKDOWN");
    try {
      bot.execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
  }

  public static void deleteMsg(Integer messageId, long chatId, Bot bot) {
    try {
      DeleteMessage deleteMsg = new DeleteMessage();
      deleteMsg.setMessageId(messageId);
      deleteMsg.setChatId(Long.toString(chatId));
      bot.execute(deleteMsg);
    } catch (Exception e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
  }

  public static Message sendMsg(long chatId, String text, Bot bot) {
    SendMessage sendMsg = new SendMessage();
    sendMsg.setChatId(chatId);
    sendMsg.setText(text);
    try {
      return bot.execute(sendMsg);
    } catch (TelegramApiException e) {
      log.error(
          "Can't send message chat id = {} cause {} {}", chatId, e.getCause(), e.getMessage());
    }
    return null;
  }

  public static void sendAnsCall(String callBackId, String text, Boolean alert, Bot bot) {
    AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
    answerCallbackQuery.setCallbackQueryId(callBackId);
    answerCallbackQuery.setText(text);
    answerCallbackQuery.setShowAlert(alert);
    try {
      bot.execute(answerCallbackQuery);
    } catch (TelegramApiException e) {
      log.error("Can't send answer to callback");
    }
  }
}
