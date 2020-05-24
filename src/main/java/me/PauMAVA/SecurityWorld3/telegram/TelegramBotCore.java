package me.PauMAVA.SecurityWorld3.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import me.PauMAVA.SecurityWorld3.mail.NewsletterHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
                updateHandler.parseUpdate(update);
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

    private byte[] toByteArray(InputStream in) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while((in.read(buffer)) != -1) {
                bos.write(buffer);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    NewsletterHandler getNewsletterHandler() {
        return newsletterHandler;
    }
}
