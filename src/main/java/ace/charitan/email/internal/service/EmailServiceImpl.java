package ace.charitan.email.internal.service;

import ace.charitan.email.external.service.ExternalEmailService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
class EmailServiceImpl implements ExternalEmailService, InternalEmailService {
    final private JavaMailSender mailSender;
    final private KafkaMessageProducer producer;
    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    private EmailServiceImpl(JavaMailSender mailSender, KafkaMessageProducer producer) {
        this.mailSender = mailSender;
        this.producer = producer;
    }

    @Override
    public void sendEmail(String to, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false);
            helper.setFrom("noreply@charitan.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
            logger.info("Sent an email to [{}]", to);
        } catch (Exception e) {
            logger.error("An error occurred while sending an email to [{}], details: {}", to, e.getMessage());
        }
    }

    @Override
    public void sendEmailByUserId(String id, String subject, String text) {
        try {
            var user = producer.getUser(id);
            sendEmail(user.email(), subject, text);
        } catch (InterruptedException e) {
            logger.error("Cannot get user by ID {}", id);
        }
    }
}
