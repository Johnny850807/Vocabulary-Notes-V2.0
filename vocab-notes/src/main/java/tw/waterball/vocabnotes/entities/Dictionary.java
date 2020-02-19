package tw.waterball.vocabnotes.entities;

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
@Table(name = "dictionary")
public class Dictionary {
    @Id
    private int id;


    @NotNull
    @Length(min = 1, max = 80)
    private String title;

    @NotNull
    @Length(min = 1, max = 300)
    private String description;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Member owner;

    private Type type;

    @Singular
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "dictionary_wordgroup",
        joinColumns = @JoinColumn(name = "dictionary_id"),
        inverseJoinColumns = @JoinColumn(name = "wordgroup_id"))
    private Set<WordGroup> wordGroups = new HashSet<>();


    public enum Type {
        PUBLIC, OWN
    }


}
