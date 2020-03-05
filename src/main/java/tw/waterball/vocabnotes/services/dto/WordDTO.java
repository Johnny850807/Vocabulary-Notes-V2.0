package tw.waterball.vocabnotes.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.waterball.vocabnotes.models.entities.Word;

@Data @AllArgsConstructor @NoArgsConstructor
public class WordDTO {
    private Integer id;
    private String name;
    private String synonyms = "";
    private String imageUrl;

    public static WordDTO project(Word w) {
        return new WordDTO(w.getId(), w.getName(), w.getSynonyms(), w.getImageUrl());
    }

}
