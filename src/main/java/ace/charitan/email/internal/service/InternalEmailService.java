package ace.charitan.email.internal.service;

public interface InternalEmailService {
    void sendEmail(String to, String subject, String text);
}
