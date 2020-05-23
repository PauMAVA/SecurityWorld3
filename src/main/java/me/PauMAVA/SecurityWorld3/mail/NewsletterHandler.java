package me.PauMAVA.SecurityWorld3.mail;


import me.PauMAVA.SecurityWorld3.telegram.TelegramBotCore;

import java.util.HashSet;
import java.util.Set;

public class NewsletterHandler {

    private TelegramBotCore telegramBotCore;

    private MailSender mailSender;

    private Set<Long> waitingForEmail = new HashSet<>();

    public NewsletterHandler(TelegramBotCore telegramBotCore) {
        this.telegramBotCore = telegramBotCore;
        this.mailSender = new MailSender();
    }

    public void addToWaitingForEmail(long id) {
        this.waitingForEmail.add(id);
    }

    public void removeFromWaitingForEmail(long id) {
        this.waitingForEmail.remove(id);
    }

    public boolean isWaitingForEmail(long id) {
        return this.waitingForEmail.contains(id);
    }

    public boolean parseEmail(String text) {
        if (text.contains("@")) {
            return mailSender.sendPhase1Mail(text);
        }
        return false;
    }




}
