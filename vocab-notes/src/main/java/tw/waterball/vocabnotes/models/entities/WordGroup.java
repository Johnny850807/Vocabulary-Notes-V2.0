package tw.waterball.vocabnotes.models.entities;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString

@Entity
public class WordGroup {
    private Integer id;

    @Size(min = 1, max = 50)
    private String title;

    @Singular
    private Set<Word> words = new HashSet<>();

    public void addWord(Word word) {
        words.add(word);
    }

    public void removeAllWords() {
        words.clear();
    }

    public void removeWord(Word word) {
        words.remove(word);
    }

    /**
     * If the title is not set, defaulted at all contained words separated by '/'.
     * For example a word group of {'apple', 'orange', 'lemon'}
     * is named "apple / orange / lemon" at default.
     */
    public String getTitle() {
        if (title == null || title.isEmpty()) {
            return words.stream().map(Word::getName)
                    .collect(Collectors.joining(" / "));
        }
        return title;
    }

    public boolean hasTitle() {
        return title != null;
    }
}
