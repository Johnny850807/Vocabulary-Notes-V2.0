package tw.waterball.vocabnotes.models.entities;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode

@Entity
public class WordGroup {
    private Integer id;

    @NotNull
    @Size(min = 1, max = 50)
    private String title;

    @Singular
    private Set<Word> words = new HashSet<>();

}
