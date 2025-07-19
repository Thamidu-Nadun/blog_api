package nadun_blog.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ControllerUtil {

    /**
     * INTERNAL SERVER ERROR Response
     * 
     * @param <T>
     * @param response
     * @return
     */
    public static <T> ResponseEntity<T> getInternalServerError(T response) {
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
