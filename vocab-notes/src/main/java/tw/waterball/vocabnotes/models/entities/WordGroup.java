package tw.waterball.vocabnotes.models.entities;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "wordgroup")
public class WordGroup {

    @Id
    private Integer id;

    @NotNull
    @Length(min = 1, max = 50)
    private String title;

    @Singular
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "wordgroup_word",
            joinColumns = @JoinColumn(name = "wordgroup_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id"))
    private Set<Word> words = new HashSet<>();

}
