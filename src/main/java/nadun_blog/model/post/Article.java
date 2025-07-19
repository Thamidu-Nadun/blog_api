package nadun_blog.model.post;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nadun_blog.model.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "articles")
public class Article extends Content {
    private String content;

    public Article(Long id, String title, String slug, String description, Media cover_image, User author,
            String content) {
        super(id, title, slug, description, cover_image, author);
        this.content = content;
    }
}
