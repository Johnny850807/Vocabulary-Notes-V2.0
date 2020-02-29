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

package tw.waterball.vocabnotes.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.waterball.vocabnotes.models.dto.DictionaryDTO;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;
import tw.waterball.vocabnotes.services.DictionaryService;
import tw.waterball.vocabnotes.services.WordGroupService;
import tw.waterball.vocabnotes.services.WordService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@RequestMapping("/api/public")
@RestController
public class PublicVocabController {
    private final DictionaryService dictionaryService;
    private final WordGroupService wordGroupService;
    private final WordService wordService;

    @Autowired
    public PublicVocabController(DictionaryService dictionaryService,
                                 WordGroupService wordGroupService,
                                 WordService wordService) {
        this.dictionaryService = dictionaryService;
        this.wordGroupService = wordGroupService;
        this.wordService = wordService;
    }

    @GetMapping("/dictionaries/{id}")
    public Dictionary getDictionary(@PathVariable int id) {
        return dictionaryService.getDictionary(id);
    }

    @PatchMapping("/dictionaries/{id}")
    public void patchDictionary(@PathVariable int id,
                                 @RequestBody @Valid Requests.ModifyDictionary request) {
        dictionaryService.modifyDictionary(id, request);
    }

    @DeleteMapping("/dictionaries/{id}")
    public void deleteDictionary(@PathVariable int id) {
        dictionaryService.deletePublicDictionary(id);
    }

    @GetMapping("/dictionaries")
    public List<DictionaryDTO> getDictionaries(@RequestParam(required = false) Integer offset,
                                            @RequestParam(required = false) Integer limit) {
        return dictionaryService.getPublicDictionaries(offset, limit);
    }

    @PostMapping("/dictionaries")
    public Dictionary createDictionary(@RequestBody @Valid Requests.CreateDictionary request) {
        return dictionaryService.createPublicDictionary(request);
    }

    @GetMapping("/wordgroups/{id}")
    public WordGroup getWordGroup(@PathVariable int id) {
        return wordGroupService.getWordGroup(id);
    }

    @PatchMapping("/wordgroups/{id}")
    public void patchWordGroup(@PathVariable int id,
                                  @RequestBody @Valid Requests.ModifyWordGroup request) {
        wordGroupService.patchWordGroup(id, request.getTitle());
    }

    @DeleteMapping("/wordgroups/{id}")
    public void deleteWordGroup(@PathVariable int id) {
        wordGroupService.deleteWordGroup(id);
    }

    @PostMapping("/wordgroups")
    public ResponseEntity createWordGroup(@RequestBody @Valid WordGroup wordGroup) {
        if (wordGroup.getId() != null) {
            return ResponseEntity.badRequest().body("You must not provide the WordGroup's id while creating it.");
        }
        return ResponseEntity.ok(wordGroupService.createWordGroup(wordGroup));
    }

    @GetMapping("/words/{name}")
    public Word getWord(@PathVariable String name) {
        return wordService.getWord(name);
    }

    @DeleteMapping("/words/{name}")
    public void deleteWord(@PathVariable String name) {
        wordService.deleteWord(name);
    }

    @PatchMapping("/words/{name}")
    public void changeImageUrlOfWord(@PathVariable String name,
                                     @RequestBody String imageUrl) {
        wordService.changeImageUrlOfWord(name, imageUrl);
    }

    @PostMapping("/words")
    public ResponseEntity createWord(@RequestBody @Valid Word word) {
        if (word.getId() != null) {
            return ResponseEntity.badRequest().body("You must not provide the Word's id while creating it.");
        }
        return ResponseEntity.ok(wordService.createWord(word));
    }

    @PutMapping("/words/{id}")
    public void updateWord(@PathVariable int id,
                           @RequestBody @Valid Word word) {
        word.setId(id);
        wordService.updateWord(word);
    }

    @PostMapping("/wordgroups/{wordGroupId}/words/{wordName}")
    public void addWordIntoWordGroup(@PathVariable int wordGroupId,
                                     @PathVariable String wordName) {
        wordGroupService.addWordIntoWordGroup(wordName, wordGroupId);
    }

    @PostMapping("/dictionaries/{dictionaryId}/wordgroups/{wordGroupId}")
    public void addWordGroupIntoDictionary(@PathVariable int dictionaryId,
                                           @PathVariable int wordGroupId) {
        dictionaryService.addWordGroupIntoDictionary(wordGroupId, dictionaryId);
    }

    @DeleteMapping("/wordgroups/{wordGroupId}/words/{wordName}")
    public void removeWordFromWordGroup(@PathVariable int wordGroupId,
                                        @PathVariable String wordName) {
        wordGroupService.removeWordFromWordGroup(wordName, wordGroupId);
    }

    @DeleteMapping("/dictionaries/{dictionaryId}/wordgroups/{wordGroupId}")
    public void removeWordGroupFromDictionary(@PathVariable int dictionaryId,
                                              @PathVariable int wordGroupId) {
        dictionaryService.removeWordGroupFromDictionary(wordGroupId, dictionaryId);
    }

    @GetMapping("/dictionaries/{dictionaryId}/wordgroups")
    public List<WordGroup> getWordGroups(@PathVariable int dictionaryId,
                                         @RequestParam(required = false) Integer offset,
                                         @RequestParam(required = false) Integer limit) {
        return wordGroupService.getWordGroups(dictionaryId, offset, limit);
    }
}
