package ace.charitan.email.external.consumer;

import ace.charitan.common.dto.donation.SendDonationNotificationDto;
import ace.charitan.common.dto.email.auth.EmailAuthCreationDto;
import ace.charitan.common.dto.email.auth.EmailAuthVerificationDto;
import ace.charitan.common.dto.email.project.EmailProjectApproveDto;
import ace.charitan.common.dto.email.project.EmailProjectHaltCharityDto;
import ace.charitan.common.dto.email.project.EmailProjectHaltDonorDto;
import ace.charitan.email.external.service.ExternalEmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class KafkaMessageConsumer {
    final private ExternalEmailService service;

    private KafkaMessageConsumer(ExternalEmailService service) {
        this.service = service;
    }

    @KafkaListener(topics = "donation-email")
    public void listen(SendDonationNotificationDto dto) {
        service.sendEmail("thienphucdoantran@gmail.com", "lol", "lol");
    }

    @KafkaListener(topics = "email.auth.creation")
    public void listen(EmailAuthCreationDto dto) {
        service.sendEmail(dto.recipient(), dto.subject(), dto.body());
    }

    @KafkaListener(topics = "email.auth.verification")
    public void listen(EmailAuthVerificationDto dto) {
        service.sendEmail(dto.recipient(), dto.subject(), dto.body());
    }

    @KafkaListener(topics = "email.project.approval")
    public void listen(EmailProjectApproveDto dto) {
        service.sendEmail(dto.recipient(), dto.subject(), dto.body());
    }

    @KafkaListener(topics = "email.project.halt.charity")
    public void listen(EmailProjectHaltCharityDto dto) {
        service.sendEmailByUserId(dto.recipient(), dto.subject(), dto.body());
    }

    @KafkaListener(topics = "email.project.halt.donor")
    public void listen(EmailProjectHaltDonorDto dto) {
        service.sendEmailByUserId(dto.recipient(), dto.subject(), dto.body());
    }
}
