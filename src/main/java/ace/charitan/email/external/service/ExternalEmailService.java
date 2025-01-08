package ace.charitan.email.external.service;

import org.springframework.stereotype.Service;

public interface ExternalEmailService {
    void sendEmail(String to, String subject, String text);

}
