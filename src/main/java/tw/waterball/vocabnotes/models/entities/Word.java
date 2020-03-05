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

import com.sun.istack.NotNull;
import lombok.*;
import tw.waterball.vocabnotes.models.validation.annotations.UrlConstraint;

import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString

@Entity
public class Word implements IdEntity {
    private Integer id;
    private String name;
    private String synonyms = "";
    private String imageUrl;

    public Word(String name, String synonyms, String imageUrl) {
        this.name = name;
        this.synonyms = synonyms;
        this.imageUrl = imageUrl;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = String.join(", ", synonyms);
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }
}
