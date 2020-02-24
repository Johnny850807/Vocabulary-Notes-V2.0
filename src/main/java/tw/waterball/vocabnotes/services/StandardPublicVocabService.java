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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.waterball.vocabnotes.api.exceptions.BadRequestException;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;
import tw.waterball.vocabnotes.models.repositories.DictionaryRepository;
import tw.waterball.vocabnotes.models.repositories.OffsetPageRequest;
import tw.waterball.vocabnotes.models.repositories.WordGroupRepository;
import tw.waterball.vocabnotes.models.repositories.WordRepository;
import tw.waterball.vocabnotes.utils.RegexUtils;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Service
@Transactional
@SuppressWarnings("Duplicates")
public class StandardPublicVocabService implements PublicVocabService {
    private final static String DICTIONARY = "dictionary";
    private final static String WORD_GROUP = "word-group";
    private final static String WORD = "word";
    private DictionaryRepository dictionaryRepository;
    private WordGroupRepository wordGroupRepository;
    private WordRepository wordRepository;

    @Autowired
    public StandardPublicVocabService(DictionaryRepository dictionaryRepository,
                                      WordGroupRepository wordGroupRepository,
                                      WordRepository wordRepository) {
        this.dictionaryRepository = dictionaryRepository;
        this.wordGroupRepository = wordGroupRepository;
        this.wordRepository = wordRepository;
    }

    @Override
    public Dictionary getDictionary(int dictionaryId) {
        return dictionaryRepository.findById(dictionaryId)
                .orElseThrow(() -> new PublicVocabNotFoundException(DICTIONARY, dictionaryId));
    }

    @Override
    public List<Dictionary> getDictionaries(@Nullable Integer offset, @Nullable Integer limit) {
        offset = offset == null ? 0 : offset;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        if (0 == limit) {
            return Collections.emptyList();
        }
        return dictionaryRepository.findAll(new OffsetPageRequest(offset, limit)).toList();
    }

    @Override
    public WordGroup getWordGroup(int wordGroupId) {
        return wordGroupRepository.findById(wordGroupId)
                .orElseThrow(()-> new PublicVocabNotFoundException(WORD_GROUP, wordGroupId));
    }

    @Override
    public Word getWord(String wordName) {
        return wordRepository.findByName(wordName)
                .orElseThrow(()-> new PublicVocabNotFoundException(WORD, wordName));
    }

    @Override
    public Dictionary createDictionary(Dictionary dictionary) {
        return dictionaryRepository.save(dictionary);
    }

    @Override
    public void modifyDictionary(int dictionaryId, @Nullable String title, @Nullable String description) {
        Dictionary dictionary = getDictionary(dictionaryId);
        if (title != null) {
            dictionary.setTitle(title);
        }
        if (description != null) {
            dictionary.setDescription(description);
        }
        dictionaryRepository.save(dictionary);
    }

    @Override
    public void deleteDictionary(int dictionaryId) {
        dictionaryRepository.deleteById(dictionaryId);
    }

    @Override
    public WordGroup createWordGroup(WordGroup wordGroup) {
        return wordGroupRepository.save(wordGroup);
    }

    @Override
    public void patchWordGroup(int wordGroupId, String title) {
        WordGroup wordGroup = getWordGroup(wordGroupId);
        wordGroup.setTitle(title);
        wordGroupRepository.save(wordGroup);
    }

    @Override
    public void deleteWordGroup(int wordGroupId) {
        WordGroup wordGroup = getWordGroup(wordGroupId);
        wordGroup.removeAllWords();
        wordGroupRepository.delete(wordGroup);
    }

    @Override
    public Word createWord(Word word) {
        return wordRepository.save(word);
    }

    @Override
    public void changeImageUrlOfWord(String wordName, String imageUrl) {
        Word word = getWord(wordName);
        word.setImageUrl(imageUrl);
        if (!RegexUtils.isValidUrl(imageUrl)) {
            throw new BadRequestException("Invalid imageUrl: "+imageUrl);
        }
        wordRepository.save(word);
    }

    @Override
    public void updateWord(Word word) {
        wordRepository.save(word);
    }

    @Override
    public void deleteWord(String wordName) {
        wordRepository.deleteWordByName(wordName);
    }

    @Override
    public void addWordIntoWordGroup(String wordName, int wordGroupId) {
        WordGroup wordGroup = getWordGroup(wordGroupId);
        Word word = getWord(wordName);
        wordGroup.addWord(word);
        wordGroupRepository.save(wordGroup);
    }

    @Override
    public void addWordGroupIntoDictionary(int wordGroupId, int dictionaryId) {
        Dictionary dictionary = getDictionary(dictionaryId);
        WordGroup wordGroup = getWordGroup(wordGroupId);
        dictionary.addWordGroup(wordGroup);
        dictionaryRepository.save(dictionary);
    }

    @Override
    public void removeWordFromWordGroup(String wordName, int wordGroupId) {
        WordGroup wordGroup = getWordGroup(wordGroupId);
        Word word = getWord(wordName);
        wordGroup.removeWord(word);
        wordGroupRepository.save(wordGroup);
    }

    @Override
    public void removeWordGroupFromDictionary(int wordGroupId, int dictionaryId) {
        Dictionary dictionary = getDictionary(dictionaryId);
        WordGroup wordGroup = getWordGroup(wordGroupId);
        dictionary.removeWordGroup(wordGroup);
        dictionaryRepository.save(dictionary);
    }

    @Override
    public List<WordGroup> getWordGroups(int dictionaryId, @Nullable Integer offset, @Nullable Integer limit) {
        offset = offset == null ? 0 : offset;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        if (limit == 0) {
            return Collections.emptyList();
        }
        return dictionaryRepository.findWordGroupsFromDictionary(dictionaryId, offset, limit);
    }

}
