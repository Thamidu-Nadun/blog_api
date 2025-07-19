package nadun_blog.util;

public class SlugUtil {
    public static String toSlug(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return text.toLowerCase().replaceAll("[^a-z0-9\\s]", "")
                .trim()
                .replaceAll("\\s+", "-");
    }
}
