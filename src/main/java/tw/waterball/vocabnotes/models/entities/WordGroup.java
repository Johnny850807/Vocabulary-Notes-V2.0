/*
 *  Copyright 2020 johnny850807 (Waterball)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package tw.waterball.vocabnotes.models.entities;

import lombok.*;
import tw.waterball.vocabnotes.models.dto.WordGroupDTO;

import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Builder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString

@Entity
public class WordGroup implements IdEntity{
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

    public String getTitle() {
        return title;
    }

    /**
     * If the title is not set, defaulted at all contained words separated by '/'.
     * For example a word group of {'apple', 'orange', 'lemon'}
     * is named "apple / orange / lemon" at default.
     */
    public static String getDisplayTitle(WordGroup wordGroup) {
        if (wordGroup.getTitle() == null || wordGroup.getTitle().isEmpty()) {
            return wordGroup.getWords().stream().map(Word::getName)
                    .collect(Collectors.joining(" / "));
        }
        return wordGroup.getTitle();
    }

    public boolean hasTitle() {
        return title != null;
    }


    public WordGroupDTO toDTO() {
        return WordGroupDTO.project(this);
    }

}
