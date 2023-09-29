package consumer.adapter.input.config;

import consumer.adapter.input.persistence.repository.PostRepository;
import consumer.core.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final PostRepository repository;

    @KafkaListener(topics = "${spring.kafka.topic.name}")
    public void handle(ConsumerRecord<String, Post> event) {
        event.headers().headers("Http method").forEach(header -> {
            log.info("Http method: {}", new String(header.value()));
        });
        log.info("Post saved: {}", event.value());
        Post post = event.value();
        Post postToSave = repository.save(post);
        System.out.println(postToSave);
    }
}
