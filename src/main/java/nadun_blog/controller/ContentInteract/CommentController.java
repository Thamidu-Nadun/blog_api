package nadun_blog.controller.ContentInteract;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import nadun_blog.DTO.CommentDTO;
import nadun_blog.DTO.Response;
import nadun_blog.service.CommentService;
import nadun_blog.util.ControllerUtil;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/get/{postId}")
    ResponseEntity<Response> getComments(@Valid @PathVariable(required = true) Long postId) {
        Response response = new Response();
        try {
            Long comment_count = commentService.getComments(postId);
            response.setResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), new HashMap<String, Long>() {
                {
                    put("comment_count", comment_count);
                }
            });
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "Post not found or no comments available");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/do/{postId}")
    public ResponseEntity<Response> addComment(@Valid @PathVariable(required = true) Long postId,
            @RequestParam(required = true) String comment, @RequestParam(required = true) UUID userId) {
        Response response = new Response();
        try {
            CommentDTO savedComment = commentService.addComment(postId, userId, comment);
            if (savedComment != null) {
                response.setResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), savedComment);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            response.setResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "postId:" + postId + " or userId:" + userId + " not found or comment could not be added");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {

            response.setResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "An error occurred while adding the comment");
            return ControllerUtil.getInternalServerError(response);
        }
    }
}
