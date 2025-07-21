package nadun_blog.service;

import java.sql.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nadun_blog.DTO.CommentDTO;
import nadun_blog.model.CommentArticle;
import nadun_blog.model.User;
import nadun_blog.model.post.Article;
import nadun_blog.repo.ArticleRepo;
import nadun_blog.repo.CommentRepo;
import nadun_blog.repo.UserRepo;
import nadun_blog.util.exceptions.DataAccessFailureException;

@Service
public class CommentService {
    @Autowired
    private ArticleRepo articleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentRepo commentRepo;

    /**
     * Get the number of comments for a specific post.
     * 
     * @param postId:Long
     * @return Number of Comments for the post.
     */
    public Long getComments(Long postId) {
        return commentRepo.countByPostId(postId);
    }

    /**
     * Add a comment to a post.
     * 
     * @param postId:Long
     * @param userId:UUID
     * @param comment:String
     * @return CommentArticle object if successful, null if an error occurs.
     */
    public CommentDTO addComment(Long postId, UUID userId, String comment) {
        try {
            Article post = articleRepo.findById(postId)
                    .orElseThrow(() -> new DataAccessFailureException("Post not found"));
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new DataAccessFailureException("User not found"));

            Date createdAt = new Date(System.currentTimeMillis());
            CommentArticle savedComment = commentRepo.save(new CommentArticle(null, post, user, createdAt, comment));
            if (savedComment != null) {

                return new CommentDTO(savedComment.getId(), savedComment.getPost().getId(),
                        savedComment.getUser().getId(),
                        savedComment.getCreatedAt(), savedComment.getComment());
            } else {
                return null;
            }

        } catch (DataAccessFailureException e) {
            return null;
        }
    }
}
