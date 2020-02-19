package tw.waterball.vocabnotes.entities;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter()
@Setter
@Accessors(fluent = true)
@ToString
@EqualsAndHashCode

@Table(name = "wordgroup")
public class WordGroup {

    @Id
    private int id;

    @NotNull
    @Length(min = 1, max = 50)
    private String title;

    @ManyToMany
    private Set<Word> words = new HashSet<>();

}
