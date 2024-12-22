package ace.charitan.email.external.consumer;


import ace.charitan.email.external.service.ExternalEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class KafkaMessageConsumer {
    @Autowired
    private ExternalEmailService service;

    @KafkaListener(topics = "email-test", groupId = "email")
    public void listen(String message) {
        System.out.println("Email microservice received message: " + message);
    }

}
