package nadun_blog.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private int code;
    private String message;
    private Object data;

    /**
     * Set response with user's values.
     * This is not a constructor, but a method to set the response values.
     * 
     * @param code
     * @param message
     * @param data
     * 
     * @author nadun
     */
    public void setResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
