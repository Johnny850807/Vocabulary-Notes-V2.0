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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.waterball.vocabnotes.api.Requests;
import tw.waterball.vocabnotes.models.dto.DictionaryDTO;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.WordGroup;
import tw.waterball.vocabnotes.models.repositories.DictionaryRepository;
import tw.waterball.vocabnotes.services.exceptions.ResourceNotFoundException;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Service
@Transactional
public class StandardDictionaryService implements DictionaryService {
    private MemberService memberService;
    private WordGroupService wordGroupService;
    private DictionaryRepository dictionaryRepository;

    @Autowired
    public StandardDictionaryService(MemberService memberService,
                                     WordGroupService wordGroupService,
                                     DictionaryRepository dictionaryRepository) {
        this.memberService = memberService;
        this.wordGroupService = wordGroupService;
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public void modifyDictionary(int dictionaryId, Requests.ModifyDictionary request) {
        Dictionary dictionary = findDictionaryOrThrowNotFound(dictionaryId);
        request.getTitle().ifPresent(dictionary::setTitle);
        request.getDescription().ifPresent(dictionary::setDescription);
        dictionaryRepository.save(dictionary);
    }

    @Override
    public DictionaryDTO createPublicDictionary(Requests.CreateDictionary request) {
        Dictionary dict = dictionaryRepository.save(Dictionary.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .type(Dictionary.Type.PUBLIC).build());
        return DictionaryDTO.project(dict);
    }



    @Override
    public DictionaryDTO getDictionary(int dictionaryId) {
        return DictionaryDTO.project(findDictionaryOrThrowNotFound(dictionaryId));
    }


    @Override
    @SuppressWarnings("Duplicates")
    public List<DictionaryDTO> getPublicDictionaries(Integer offset, Integer limit) {
        offset = offset == null ? 0 : offset;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        if (0 == limit) {
            return Collections.emptyList();
        }
        return dictionaryRepository.findPublicDictionaries(offset, limit);
    }


        @Override
    public void deletePublicDictionary(int dictionaryId) {
        dictionaryRepository.deleteById(dictionaryId);
    }


    @Override
    public void removeWordGroupFromDictionary(int wordGroupId, int dictionaryId) {
        Dictionary dictionary = findDictionaryOrThrowNotFound(dictionaryId);
        WordGroup wordGroup = wordGroupService.getWordGroup(wordGroupId);
        dictionary.removeWordGroup(wordGroup);
        dictionaryRepository.save(dictionary);
    }

    @Override
    public void addWordGroupIntoDictionary(int wordGroupId, int dictionaryId) {
        Dictionary dictionary = findDictionaryOrThrowNotFound(dictionaryId);
        WordGroup wordGroup =  wordGroupService.getWordGroup(wordGroupId);
        dictionary.addWordGroup(wordGroup);
        dictionaryRepository.save(dictionary);
    }

    private Dictionary findDictionaryOrThrowNotFound(int dictionaryId) {
        return dictionaryRepository.findById(dictionaryId)
                .orElseThrow(() -> new ResourceNotFoundException("dictionary", dictionaryId));
    }
}
