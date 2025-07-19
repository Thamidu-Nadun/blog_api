package nadun_blog.model.post;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nadun_blog.util.enums.MediaTypes;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Media {
    @NotBlank(message = "URL is mandatory")
    private String url;
    @Enumerated(EnumType.STRING)
    private MediaTypes type;
    @NotBlank(message = "Alt text is mandatory")
    private String alt_text;
}
