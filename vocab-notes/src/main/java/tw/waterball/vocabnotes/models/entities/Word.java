package tw.waterball.vocabnotes.models.entities;

import com.sun.istack.NotNull;
import lombok.*;
import tw.waterball.vocabnotes.utils.RegexUtils;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
    @Pattern(regexp = RegexUtils.URL_PATTERN)
    private String imageUrl;
}
