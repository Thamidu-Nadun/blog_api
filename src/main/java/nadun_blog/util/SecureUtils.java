package nadun_blog.util;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

public class SecureUtils {

    /**
     * Generate New Secure Verification Token
     * 
     * @param email
     * @return
     */
    public static String generateVerificationToken(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        String token = generateToken(16);
        long time = Instant.now().toEpochMilli();

        String newToken = email + ":" + token + ":" + time;
        return Base64.getUrlEncoder().withoutPadding().encodeToString(newToken.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate Random token
     * 
     * @param length
     * @return
     */
    private static String generateToken(int length) {
        StringBuilder token = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; i++) {
            token.append(characters.charAt((int) Math.random() * characters.length()));
        }
        return token.toString();
    }
}
