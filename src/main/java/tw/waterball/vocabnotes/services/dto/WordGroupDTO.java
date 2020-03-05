package tw.waterball.vocabnotes.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;

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
