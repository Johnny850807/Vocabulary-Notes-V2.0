package tw.waterball.vocabnotes.entities;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.*;


@Getter() @Setter
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode

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


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member owner;

    private Type type;

    @ManyToMany
    private Set<WordGroup> wordGroups = new HashSet<>();


    public enum Type {
        PUBLIC, OWN
    }

}
