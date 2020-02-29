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

package tw.waterball.vocabnotes.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.waterball.vocabnotes.models.entities.Dictionary;

import java.lang.ref.WeakReference;

/**
 * @author johnny850807@gmail.com (Waterball))
 */

@Data @AllArgsConstructor @NoArgsConstructor
public class DictionaryDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer ownerId;
    private Dictionary.Type type;


    public static DictionaryDTO project(Dictionary dictionary) {
        return new DictionaryDTO(dictionary.getId(), dictionary.getTitle(),
                dictionary.getDescription(),
                dictionary.getOwner() == null ? null : dictionary.getOwner().getId(),
                dictionary.getType());
    }
}