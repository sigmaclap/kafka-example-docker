package kafka.adapter.output.config;

import kafka.core.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, Post> kafkaTemplate;

    public void sendMessage(Post post, String httpMethod) {
        Message<Post> message = MessageBuilder
                .withPayload(post)
                .setHeader("Http method", httpMethod)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();
        kafkaTemplate.send(message);
        log.info("Отправлено сообщение номер {}", message);
    }

}
