package ace.charitan.email.internal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class KafkaMessageProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        System.out.println("Email microservice sent message: " + message);
        kafkaTemplate.send(topic, message);
    }

}