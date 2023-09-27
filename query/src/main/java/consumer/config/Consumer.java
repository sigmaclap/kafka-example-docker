package consumer.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import consumer.core.PostRepository;
import consumer.core.dto.PostDto;
import consumer.core.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class Consumer {

    private static final String orderTopic = "${spring.kafka.topic.name}";

    private final ObjectMapper objectMapper;
    private final PostRepository repository;
    private final ModelMapper modelMapper;

    @KafkaListener(topics = orderTopic)
    public void consumeMessage(String message, @Header("Method Request") String method) throws JsonProcessingException {
        log.info("message consumed {}", message);

        PostDto postDto = objectMapper.readValue(message, PostDto.class);
        Post post = modelMapper.map(postDto, Post.class);
        Post postToSave = repository.save(post);
        log.info("Post saved {}", postToSave);
        log.info("Request Method {}", method);
    }

}
