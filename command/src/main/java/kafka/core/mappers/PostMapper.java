package kafka.core.mappers;


import kafka.core.dto.PostDto;
import kafka.core.entity.Post;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PostMapper {
    public Post toPost(PostDto postDto) {
        return Post.builder()
                .id(postDto.getId())
                .name(postDto.getName())
                .description(postDto.getDescription())
                .build();
    }

    public PostDto toDtoPost(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .name(post.getName())
                .description(post.getDescription())
                .build();
    }
}
