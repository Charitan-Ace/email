package ace.charitan.email.external.service;

public interface ExternalEmailService {
    void sendEmail(String to, String subject, String text);

    void sendEmailByUserId(String id, String subject, String text);
}
