package kafka.kafkaConfig;

import kafka.core.entity.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducer {

    @Value("${spring.kafka.topic.name}")
    private String topicJsonName;

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private KafkaTemplate<String, Post> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Post> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Post data, String request) {

        LOGGER.info(String.format("Message sent -> %s", data.toString()));

        Message<Post> message = MessageBuilder
                .withPayload(data)
                .setHeader("Method Request", request)
                .setHeader(KafkaHeaders.TOPIC, topicJsonName)
                .build();

        kafkaTemplate.send(message);
    }
}
