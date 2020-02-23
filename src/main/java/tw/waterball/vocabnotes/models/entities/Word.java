package tw.waterball.vocabnotes.models.entities;

import com.sun.istack.NotNull;
import lombok.*;
import tw.waterball.vocabnotes.utils.RegexUtils;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString

@Entity
public class Word {
    private Integer id;

    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    @Size(max = 80)
    private String synonyms = "";

    @NotNull
    // TODO validate URL via Apache's UrlValidator
    private String imageUrl;

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = String.join(", ", synonyms);
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }
}
