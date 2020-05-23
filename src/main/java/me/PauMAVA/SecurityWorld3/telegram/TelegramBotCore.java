package me.PauMAVA.SecurityWorld3.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import me.PauMAVA.SecurityWorld3.mail.NewsletterHandler;

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

    NewsletterHandler getNewsletterHandler() {
        return newsletterHandler;
    }
}
