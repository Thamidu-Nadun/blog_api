package nadun_blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import nadun_blog.model.post.Article;

public interface ArticleRepo extends JpaRepository<Article, Long> {

}
