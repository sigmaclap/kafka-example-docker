package consumer.core.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "posts")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String description;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Post post = (Post) object;
        return Objects.equals(id, post.id) && Objects.equals(name, post.name) && Objects.equals(description, post.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
