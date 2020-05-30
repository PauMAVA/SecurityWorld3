package me.PauMAVA.SecurityWorld3.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import me.PauMAVA.SecurityWorld3.mail.NewsletterHandler;

import java.io.*;

public class TelegramBotCore {

    private TelegramBot telegramBot;

    private UpdateHandler updateHandler;

    private NewsletterHandler newsletterHandler;

    public TelegramBotCore(String apiToken) {
        this.telegramBot = new TelegramBot(apiToken);
        this.updateHandler = new UpdateHandler(this);
        this.newsletterHandler = new NewsletterHandler(this);
        listenForUpdates();
    }

    private void listenForUpdates() {
        this.telegramBot.setUpdatesListener(updates -> {
            for (Update update: updates) {
                try {
                    updateHandler.parseUpdate(update);
                } catch (Exception e) {
                    System.out.println("Bad update. Skipping....");
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        telegramBot.execute(sendMessage);
    }

    void sendImageAsFile(long chatId, String fileName) {
        SendDocument sendDocument = new SendDocument(chatId, new File(fileName));
        telegramBot.execute(sendDocument);
    }

    NewsletterHandler getNewsletterHandler() {
        return newsletterHandler;
    }
}
