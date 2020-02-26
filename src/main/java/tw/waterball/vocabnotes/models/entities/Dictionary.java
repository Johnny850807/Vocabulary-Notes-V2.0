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

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString

@Entity
public class Dictionary implements IdEntity {
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
    private Set<WordGroup> wordGroups = new HashSet<>();


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
