package me.PauMAVA.SecurityWorld3.telegram;

import com.pengrad.telegrambot.model.Update;

public class UpdateHandler {

    private TelegramBotCore telegramBotCore;

    UpdateHandler(TelegramBotCore telegramBotCore) {
        this.telegramBotCore = telegramBotCore;
    }

    void parseUpdate(Update update) {
        String text = update.message().text();
        long chatId = update.message().chat().id();
        System.out.println("Recieved update: chatId={" + chatId + "},text={" + text + "}");
        if (telegramBotCore.getNewsletterHandler().isWaitingForEmail(chatId)) {
            telegramBotCore.getNewsletterHandler().parseEmail(text);
            telegramBotCore.getNewsletterHandler().removeFromWaitingForEmail(chatId);
        } else if (text.equalsIgnoreCase("/newsletter")) {
            telegramBotCore.sendMessage(chatId, "SW50cm9kdWNlIHlvdXIgZW1haWw6");
            telegramBotCore.getNewsletterHandler().addToWaitingForEmail(chatId);
        }
    }




}
