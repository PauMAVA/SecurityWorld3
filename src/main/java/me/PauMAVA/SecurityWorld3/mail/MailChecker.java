package me.PauMAVA.SecurityWorld3.mail;

import me.PauMAVA.SecurityWorld3.SC3;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class MailChecker implements Runnable {

    private static final String EMAIL = "sqlatsc3@gmail.com";
    private static final String PASSWORD = "4aaYgM734yvA48a";
    private static final String SMTP_SERVER = "smtp.gmail.com";

    private static final String SMTP_PROPERTIES = "/smtp.properties";
    private static final String PROTOCOL = "imaps";

    private SC3 core;

    private MailSender sender;

    private Session session;

    private Store store;

    private volatile boolean listen = false;

    public MailChecker(SC3 core, MailSender sender) {
        this.core = core;
        this.sender = sender;
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(SMTP_PROPERTIES));
            this.session = Session.getDefaultInstance(properties, null);
            this.store = session.getStore(PROTOCOL);
            store.connect(SMTP_SERVER, EMAIL, PASSWORD);
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }

    public void startListening() {
        this.listen = true;
        new Thread(this).start();
    }

    public void stopListening() {
        this.listen = false;
    }

    @Override
    public void run() {
        while (listen) {
            try {
                Folder folder = store.getFolder("inbox");
                folder.open(Folder.READ_WRITE);
                for (Message message: folder.getMessages()) {
                    processMessage(message);
                    message.setFlag(Flags.Flag.DELETED, true);
                }
                folder.close(true);
            } catch (MessagingException | IOException e) {
                e.printStackTrace();
            }
            sleep(1);
        }
    }

    private void processMessage(Message message) throws MessagingException, IOException {
        String text = getMessageText(message);
        String elegibleQuery = text.split("\n")[0];
        System.out.println(elegibleQuery);
        String response = core.getSqlapi().executeStatement("SELECT * FROM usernames WHERE user_id = " + elegibleQuery);
        if (response == null || response.isEmpty()) {
            response = "No such user with id:" + elegibleQuery + "...";
        }
        Address[] to = message.getFrom();
        System.out.println("Recieved email: " + message.getSubject() + "\n - From: " + Arrays.toString(to) + "\n - Content: " + text);

        sender.sendMail(to, "WW91ciBxdWVyeSByZXN1bHRz==", response);
    }

    private String getMessageText(Message message) throws IOException, MessagingException {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("text/html")) {
           return extractFromHtml((String) message.getContent());
        } else if (message.isMimeType("multipart/*")) {
            return extractFromMultipart((MimeMultipart) message.getContent());
        }
        return "";
    }

    private String extractFromMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain") || bodyPart.isMimeType("text/html")) {
                sb.append(bodyPart.getContent());
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                sb.append(extractFromMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return sb.toString();
    }

    private String extractFromHtml(String html) {
        return org.jsoup.Jsoup.parse(html).text();
    }



    private void sleep(int timeout) {
        try {
            Thread.sleep(timeout * 1000);
        } catch (InterruptedException e) {
            System.out.println("Unable to timeout:" + timeout + "!");
            e.printStackTrace();
        }
    }
}
