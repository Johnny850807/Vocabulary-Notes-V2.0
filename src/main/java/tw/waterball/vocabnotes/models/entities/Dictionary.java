package tw.waterball.vocabnotes.models.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString

@Entity
public class Dictionary {
    private Integer id;

    @NotNull
    @Size(min = 1, max = 80)
    private String title;

    @NotNull
    @Size(min = 1, max = 300)
    private String description;

    @ToString.Exclude
    private Member owner;

    @NotNull
    private Type type;

    @Singular
    private transient Set<WordGroup> wordGroups = new HashSet<>();

    public void addWordGroup(WordGroup wordGroup) {
        wordGroups.add(wordGroup);
    }

    public void removeWordGroup(WordGroup wordGroup) {
        wordGroups.remove(wordGroup);
    }

    public enum Type {
        PUBLIC, OWN
    }

}