package kafka.kafkaConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.core.entity.Post;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Producer {

    @Value("${spring.kafka.topic.name}")
    private String orderTopic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public void sendMessage(Post post, String request) {
        String orderAsMessage = objectMapper.writeValueAsString(post);

        Message<String> message = MessageBuilder
                .withPayload(orderAsMessage)
                .setHeader("Method Request", request)
                .setHeader(KafkaHeaders.TOPIC, orderTopic)
                .build();

        kafkaTemplate.send(message);

        log.info("Producer send message {}", orderAsMessage);
    }
}
