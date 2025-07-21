package nadun_blog.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nadun_blog.DTO.Response;
import nadun_blog.DTO.ResDTOs.ArticleResDTO;
import nadun_blog.DTO.posts.ArticleDTO;
import nadun_blog.model.post.Article;
import nadun_blog.service.ArticleService;
import nadun_blog.util.ControllerUtil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/getarticles")
    public ResponseEntity<Response> getArticles() {
        Response response = new Response();
        try {
            List<Article> articles = articleService.getArticles();
            if (articles != null && !articles.isEmpty()) {

                modelMapper.typeMap(Article.class, ArticleResDTO.class)
                        .addMappings(mapper -> {
                            mapper.map(
                                    src -> src.getAuthor().getUsername(),
                                    ArticleResDTO::setAuthor);
                        });

                List<ArticleResDTO> articleDTOs = articles.stream()
                        .map(article -> modelMapper.map(article, ArticleResDTO.class))
                        .collect(Collectors.toList());
                response.setResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), articleDTOs);
            } else {
                response.setResponse(HttpStatus.NOT_FOUND.value(), "No articles found", new Article[0]);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), new Object[0]);
            return ControllerUtil.getInternalServerError(response);
        }
    }

    @PostMapping("/addarticle")
    public ResponseEntity<Response> addArticle(@Valid @RequestBody ArticleDTO articleDTO) {
        Response response = new Response();

        try {
            Article savedArticle = articleService.addArticle(articleDTO);
            ArticleResDTO savedArticleDTO = modelMapper.map(savedArticle, ArticleResDTO.class);
            savedArticleDTO.setAuthor(savedArticle.getAuthor().getUsername()); // Set Only User name of the user
            response.setResponse(HttpStatus.CREATED.value(),
                    HttpStatus.CREATED.getReasonPhrase(),
                    savedArticleDTO);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    new Object[0]);
            return ControllerUtil.getInternalServerError(response);
        }
    }

    @PutMapping("/updatearticle")
    public ResponseEntity<Response> updateArticle(@Valid @RequestParam(required = true) Long post_id,
            @RequestBody ArticleDTO article) {
        Response response = new Response();
        try {
            Article updatedArticle = articleService.updateArticle(post_id, article);

            if (updatedArticle != null) {
                ArticleResDTO articleResDTO = modelMapper.map(updatedArticle, ArticleResDTO.class);
                articleResDTO.setAuthor(updatedArticle.getAuthor().getUsername());

                response.setResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), articleResDTO);
            } else {
                response.setResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), article);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
            return ControllerUtil.getInternalServerError(response);
        }
    }

    @DeleteMapping("/deletearticle/{post_id}")
    public ResponseEntity<Response> deleteArticle(@PathVariable(required = true) Long post_id) {
        Response response = new Response();
        try {
            Article deletedArticle = articleService.deleteArticle(post_id);
            if (deletedArticle != null) {
                ArticleResDTO articleResDTO = modelMapper.map(deletedArticle, ArticleResDTO.class);
                articleResDTO.setAuthor(deletedArticle.getAuthor().getUsername());

                response.setResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), articleResDTO);
            } else {
                response.setResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), post_id);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.setResponse(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), post_id);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
            return ControllerUtil.getInternalServerError(response);
        }
    }

}
