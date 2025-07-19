package nadun_blog.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SlugTest {

    @Test
    void slug() {
        assertEquals("hello-world-this-is-a-test", SlugUtil.toSlug("Hello World! This is a test."));
    }
}
