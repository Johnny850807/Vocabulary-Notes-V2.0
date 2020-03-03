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
import tw.waterball.vocabnotes.models.dto.WordGroupDTO;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;
import tw.waterball.vocabnotes.models.repositories.WordGroupRepository;
import tw.waterball.vocabnotes.services.exceptions.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Service
@Transactional
@SuppressWarnings("Duplicates")
public class StandardWordGroupService implements WordGroupService {
    private WordService wordService;
    private WordGroupRepository wordGroupRepository;

    @Autowired
    public StandardWordGroupService(WordService wordService,
                                    WordGroupRepository wordGroupRepository) {
        this.wordService = wordService;
        this.wordGroupRepository = wordGroupRepository;
    }

    @Override
    public WordGroupDTO getWordGroup(int wordGroupId) {
        return findWordGroupOrThrowNotFound(wordGroupId).toDTO();
    }

    @Override
    public WordGroupDTO createWordGroup(WordGroup wordGroup) {
        return wordGroupRepository.save(wordGroup).toDTO();
    }

    @Override
    public void patchWordGroup(int wordGroupId, String title) {
        WordGroup wordGroup = findWordGroupOrThrowNotFound(wordGroupId);
        wordGroup.setTitle(title);
        wordGroupRepository.save(wordGroup);
    }

    @Override
    public void deleteWordGroup(int wordGroupId) {
        wordGroupRepository.deleteById(wordGroupId);
    }


    @Override
    public void addWordIntoWordGroup(String wordName, int wordGroupId) {
        WordGroup wordGroup = findWordGroupOrThrowNotFound(wordGroupId);
        Word word = wordService.getWord(wordName);
        wordGroup.addWord(word);
        wordGroupRepository.save(wordGroup);
    }

    @Override
    public void removeWordFromWordGroup(String wordName, int wordGroupId) {
        WordGroup wordGroup = findWordGroupOrThrowNotFound(wordGroupId);
        Word word = wordService.getWord(wordName);
        wordGroup.removeWord(word);
        wordGroupRepository.save(wordGroup);
    }

    @Override
    public List<WordGroupDTO> getWordGroups(int dictionaryId, @Nullable Integer offset, @Nullable Integer limit) {
        offset = offset == null ? 0 : offset;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        if (limit == 0) {
            return Collections.emptyList();
        }
        return wordGroupRepository.findWordGroupsFromDictionary(dictionaryId, offset, limit)
                .stream().map(WordGroup::toDTO).collect(Collectors.toList());
    }

    private WordGroup findWordGroupOrThrowNotFound(int wordGroupId) {
        return wordGroupRepository.findById(wordGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("word group", wordGroupId));
    }

}
