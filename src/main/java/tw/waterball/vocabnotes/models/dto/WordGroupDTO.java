package tw.waterball.vocabnotes.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;

import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data @AllArgsConstructor @NoArgsConstructor
public class WordGroupDTO {
    private Integer id;
    private String title;
    private Set<Word> words = new HashSet<>();

    public static WordGroupDTO project(WordGroup wordGroup) {
        return new WordGroupDTO(wordGroup.getId(), wordGroup.getTitle(), wordGroup.getWords());
    }
}
