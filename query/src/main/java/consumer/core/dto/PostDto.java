package consumer.core.dto;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class PostDto {
    Long id;
    String name;
    String description;
}
