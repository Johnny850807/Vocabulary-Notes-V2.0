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

import org.jetbrains.annotations.Nullable;
import tw.waterball.vocabnotes.api.Requests;
import tw.waterball.vocabnotes.models.dto.DictionaryDTO;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;

import java.util.List;


/**
 * @author johnny850807@gmail.com (Waterball))
 */
public interface WordGroupService {
    WordGroup getWordGroup(int wordGroupId);
    WordGroup createWordGroup(WordGroup wordGroup);
    void patchWordGroup(int wordGroupId, String title);
    void deleteWordGroup(int wordGroupId);
    void addWordIntoWordGroup(String wordName, int wordGroupId);
    void removeWordFromWordGroup(String wordName, int wordGroupId);
    List<WordGroup> getWordGroups(int dictionaryId, @Nullable Integer offset, @Nullable Integer limit);

}
