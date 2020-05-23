package me.PauMAVA.SecurityWorld3;

import me.PauMAVA.SecurityWorld3.mail.MailChecker;
import me.PauMAVA.SecurityWorld3.mail.MailSender;
import me.PauMAVA.SecurityWorld3.telegram.TelegramBotCore;

public class SC3 {

    private MailSender sender;
    private MailChecker checker;

    public static void main(String[] args) {
        new SC3().start();
        new TelegramBotCore("885467198:AAFTuUlf3h7tKatowMxno4e4nVSdjlQUbvg");
    }

    private void start() {
        this.sender = new MailSender();
        this.checker = new MailChecker(sender);
        checker.startListening();
        printLogo();
    }

    public MailSender getSender() {
        return sender;
    }

    public MailChecker getChecker() {
        return checker;
    }

    private void printLogo() {
        System.out.println("                                       \n" +
                "                         .--,-``-.     \n" +
                "  .--.--.     ,----..   /   /     '.   \n" +
                " /  /    '.  /   /   \\ / ../        ;  \n" +
                "|  :  /`. / |   :     :\\ ``\\  .`-    ' \n" +
                ";  |  |--`  .   |  ;. / \\___\\/   \\   : \n" +
                "|  :  ;_    .   ; /--`       \\   :   | \n" +
                " \\  \\    `. ;   | ;          /  /   /  \n" +
                "  `----.   \\|   : |          \\  \\   \\  \n" +
                "  __ \\  \\  |.   | '___   ___ /   :   | \n" +
                " /  /`--'  /'   ; : .'| /   /\\   /   : \n" +
                "'--'.     / '   | '/  :/ ,,/  ',-    . \n" +
                "  `--'---'  |   :    / \\ ''\\        ;  \n" +
                "             \\   \\ .'   \\   \\     .'   \n" +
                "              `---`      `--`-,,-'     \n" +
                "           by PauMAVA (2020)                            ");
    }
}