package ace.charitan.email.external.consumer;

import ace.charitan.common.dto.email.auth.EmailAuthCreationDto;
import ace.charitan.common.dto.email.auth.EmailAuthVerificationDto;
import ace.charitan.common.dto.email.donation.EmailDonationCreationDto;
import ace.charitan.common.dto.email.payment.EmailPaymentHaltedProjectCancelSubscriptionEmailDto;
import ace.charitan.common.dto.email.project.EmailProjectApproveDto;
import ace.charitan.common.dto.email.project.EmailProjectHaltCharityDto;
import ace.charitan.common.dto.email.project.EmailProjectHaltDonorDto;
import ace.charitan.common.dto.email.subscription.EmailNewProjectSubscription.EmailNewProjectSubscriptionRequestDto;
import ace.charitan.email.external.service.ExternalEmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class KafkaMessageConsumer {
    final private ExternalEmailService service;

    private KafkaMessageConsumer(ExternalEmailService service) {
        this.service = service;
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

    @KafkaListener(topics = "email.donation.create")
    public void listen(EmailDonationCreationDto dto) {
        service.sendEmailByUserId(dto.recipient(), dto.subject(), dto.body());
    }

    @KafkaListener(topics = "email.subscription.cancel")
    public void listen(EmailPaymentHaltedProjectCancelSubscriptionEmailDto dto) {
        service.sendEmailByUserId(dto.recipient(), dto.subject(), dto.body());
    }

    @KafkaListener(topics = "subscription-email-new-project")
    public void listen(EmailNewProjectSubscriptionRequestDto dto) {
        for (String donorId : dto.getDonorIdList()) {
            service.sendEmailByUserId(donorId, "Email notification for New Project", "A new project of interest has been created." + dto.getProject().getTitle());
        }
    }
}
