package nadun_blog.DTO.ResDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nadun_blog.model.post.Media;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResDTO {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private Media cover_image;
    private String author;
    private String content;
}
