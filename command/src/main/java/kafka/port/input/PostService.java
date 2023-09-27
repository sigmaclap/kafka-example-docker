package kafka.port.input;


import kafka.core.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    Post createPost(Post post);

    Post updatePost(Post post, Long postId);

    void deletePost(Long postId);
}
