package nadun_blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import nadun_blog.model.CommentArticle;

public interface CommentRepo extends JpaRepository<CommentArticle, Long> {
    Long countByPostId(Long postId);

}
