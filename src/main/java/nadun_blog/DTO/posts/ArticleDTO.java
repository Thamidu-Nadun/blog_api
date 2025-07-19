package nadun_blog.DTO.posts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO extends ContentDTO {
    @NotBlank(message = "Content is mandatory")
    private String content;
}
