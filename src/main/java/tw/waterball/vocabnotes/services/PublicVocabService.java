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
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;

import java.util.List;
import java.util.Optional;


/**
 * @author johnny850807@gmail.com (Waterball))
 */
public interface PublicVocabService {
    Dictionary getDictionary(int dictionaryId);
    List<Dictionary> getDictionaries(@Nullable Integer offset, @Nullable Integer limit);
    WordGroup getWordGroup(int wordGroupId);
    Word getWord(String wordName);

    Dictionary createDictionary(Dictionary dictionary);

    void modifyDictionary(int dictionaryId, @Nullable String title, @Nullable String description);
    void deleteDictionary(int dictionaryId);

    WordGroup createWordGroup(WordGroup wordGroup);
    void patchWordGroup(int wordGroupId, String title);
    void deleteWordGroup(int wordGroupId);

    Word createWord(Word word);
    void changeImageUrlOfWord(String wordName, String imageUrl);
    void updateWord(Word word);
    void deleteWord(String wordName);

    void addWordIntoWordGroup(String wordName, int wordGroupId);
    void addWordGroupIntoDictionary(int wordGroupId, int dictionaryId);
    void removeWordFromWordGroup(String wordName, int wordGroupId);
    void removeWordGroupFromDictionary(int wordGroupId, int dictionaryId);
    List<WordGroup> getWordGroups(int dictionaryId, @Nullable Integer offset, @Nullable Integer limit);

}
