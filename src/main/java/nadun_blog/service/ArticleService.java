package nadun_blog.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nadun_blog.DTO.posts.ArticleDTO;
import nadun_blog.model.User;
import nadun_blog.model.post.Article;
import nadun_blog.model.post.Media;
import nadun_blog.repo.ArticleRepo;
import nadun_blog.repo.UserRepo;
import nadun_blog.util.SlugUtil;
import nadun_blog.util.enums.MediaTypes;
import nadun_blog.util.exceptions.DataAccessFailureException;

@Service
public class ArticleService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ArticleRepo articleRepo;
    @Autowired
    private UserRepo userRepo;

    /**
     * This method retrieves all articles from the database.
     * 
     * @return List<Article> | null
     * @author nadun
     */
    public List<Article> getArticles() {
        try {
            return articleRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Save a new article to the database.
     * 
     * @param article
     * @return Article
     * @author nadun
     */
    public Article addArticle(ArticleDTO article) {
        try {
            User author = userRepo.findById(article.getAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("Author Not Found"));

            Article newArticle = new Article(null, article.getTitle(), "", article.getDescription(),
                    article.getCover_image(),
                    author, article.getContent());
            newArticle.getCover_image().setType(MediaTypes.IMAGE);
            newArticle.setSlug(SlugUtil.toSlug(newArticle.getTitle()));
            return articleRepo.save(newArticle);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * Update article safely. [PATCH]
     * 
     * @param id
     * @param article
     * @return
     */
    public Article updateArticle(Long id, ArticleDTO article) {
        try {
            Article post = articleRepo.findById(id).orElse(null);
            if (post != null) {
                Article updatedArticle = new Article(
                        post.getId(),
                        article.getTitle() != null ? article.getTitle() : post.getTitle(),
                        post.getSlug(),
                        article.getDescription() != null ? article.getDescription() : post.getDescription(),
                        article.getCover_image() != null || article.getCover_image().getClass() != Media.class
                                ? article.getCover_image()
                                : post.getCover_image(),
                        post.getAuthor(),
                        article.getContent() != null ? article.getContent() : post.getContent());
                updatedArticle.setSlug(SlugUtil.toSlug(updatedArticle.getTitle()));

                return articleRepo.save(updatedArticle);
            } else {
                throw new DataAccessFailureException();
            }
        } catch (DataAccessFailureException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Delete User
     * 
     * @param post_id
     * @return
     */
    public Article deleteArticle(Long post_id) {
        try {
            Article post = articleRepo.findById(post_id).orElse(null);
            if (post != null) {

                articleRepo.deleteById(post_id);
                return post;
            } else {
                throw new DataAccessFailureException();
            }
        } catch (Exception e) {
            return null;
        }
    }
}
