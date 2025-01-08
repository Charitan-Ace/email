package ace.charitan.email.external.consumer;


import ace.charitan.common.dto.donation.SendDonationNotificationDto;
import ace.charitan.email.external.service.ExternalEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class KafkaMessageConsumer {
    @Autowired
    private ExternalEmailService service;

    @KafkaListener(topics = "donation-email", groupId = "email")
    public void listen(SendDonationNotificationDto dto) {
        service.sendEmail("thienphucdoantran@gmail.com", "lol", "lol");
    }

}
