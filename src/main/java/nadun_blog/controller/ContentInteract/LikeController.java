package nadun_blog.controller.ContentInteract;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nadun_blog.DTO.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/like")
public class LikeController {

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getLikes(@PathVariable long id) {
        return new ResponseEntity<>(null);
    }

    @PostMapping("/do/{id}")
    public ResponseEntity<Response> addLike(@PathVariable long id) {
        return new ResponseEntity<>(null);
    }

}
