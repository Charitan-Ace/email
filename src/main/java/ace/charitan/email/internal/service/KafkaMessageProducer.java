package ace.charitan.email.internal.service;

import ace.charitan.common.dto.auth.AuthRequestByIdDto;
import ace.charitan.common.dto.auth.ExternalAuthDto;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Component
class KafkaMessageProducer {
    final private KafkaTemplate<String, Object> template;
    final private ReplyingKafkaTemplate<String, Object, Object> replyingTemplate;
    final private Logger logger = LoggerFactory.getLogger(this.getClass());

    private KafkaMessageProducer(KafkaTemplate<String, Object> kafkaTemplate, ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate) {
        this.template = kafkaTemplate;
        this.replyingTemplate = replyingKafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        logger.info("Email sent: {}", message);
        template.send(topic, message);
    }

    public Object sendReplying(String topic, Object data) throws InterruptedException {
        replyingTemplate.waitForAssignment(Duration.ofSeconds(10));

        ProducerRecord<String, Object> record = new ProducerRecord<>(topic, data);
        RequestReplyFuture<String, Object, Object> request = replyingTemplate.sendAndReceive(record);

        try {
            var send = request.getSendFuture().get();
            logger.info("Sent replying future request to {}, metadata {}", topic, send.getRecordMetadata());

            var result = request.get();
            logger.info("Request to {} has been replied, value size {}", topic, result.serializedValueSize());

            return result.value();
        } catch (ExecutionException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ExternalAuthDto getUser(String userId) throws InterruptedException {
        return (ExternalAuthDto) sendReplying("auth.get.id", new AuthRequestByIdDto(userId));
    }

}