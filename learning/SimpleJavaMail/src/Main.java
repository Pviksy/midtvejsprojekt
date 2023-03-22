public class Main {

    public static void main(String[] args) {

        Email email = EmailBuilder.startingBlank();

        Mailer inhouseMailer = MailerBuilder
                .withSMTPServer("server", 25, "username", "password")
                .buildMailer();
    }
}
