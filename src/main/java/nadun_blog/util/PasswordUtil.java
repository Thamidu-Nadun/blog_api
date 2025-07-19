package nadun_blog.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PasswordUtil {
    /**
     * Hash Password
     * 
     * @param password
     * @return
     */
    public static String hashPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }
}
