package kafka.core.service;

import kafka.adapter.output.config.KafkaProducer;
import kafka.adapter.output.persistence.repository.PostRepository;
import kafka.core.entity.Post;
import kafka.core.exception.PostNotFoundException;
import kafka.port.input.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            kafkaProducer.sendMessage(post, "GET");
        }
        return posts;
    }

    @Override
    public Post createPost(Post post) {
        Post postToSave = postRepository.save(post);
        kafkaProducer.sendMessage(postToSave, "POST");
        return postToSave;
    }

    @Override
    public Post updatePost(Post post, Long postId) {
        Post postDAO = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Not found postDAO"));
        if (post.getName() != null) postDAO.setName(post.getName());
        if (post.getDescription() != null) postDAO.setDescription(post.getDescription());
        kafkaProducer.sendMessage(postDAO, "PATCH");
        return postRepository.save(postDAO);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Not found post"));
        kafkaProducer.sendMessage(post, "DELETE");
        postRepository.delete(post);
    }
}
