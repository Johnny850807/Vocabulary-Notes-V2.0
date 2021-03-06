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

package tw.waterball.vocabnotes.services;

import tw.waterball.vocabnotes.api.Requests;
import tw.waterball.vocabnotes.models.dto.DictionaryDTO;
import tw.waterball.vocabnotes.models.entities.Dictionary;

import java.util.List;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
public interface DictionaryService {

    void modifyDictionary(int dictionaryId, Requests.ModifyDictionary request);

    Dictionary createPublicDictionary(Requests.CreateDictionary request);

    Dictionary getDictionary(int dictionaryId);

    default List<DictionaryDTO> getPublicDictionaries() {
        return getPublicDictionaries(0, Integer.MAX_VALUE);
    }

    default List<DictionaryDTO> getPublicDictionaries(Integer offset) {
        return getPublicDictionaries(offset, Integer.MAX_VALUE);
    }

    List<DictionaryDTO> getPublicDictionaries(Integer offset, Integer limit);

    void deletePublicDictionary(int dictionaryId);

    void removeWordGroupFromDictionary(int wordGroupId, int dictionaryId);

    void addWordGroupIntoDictionary(int wordGroupId, int dictionaryId);
}
