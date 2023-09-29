package kafka.adapter.output.persistence.repository;

import kafka.core.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}