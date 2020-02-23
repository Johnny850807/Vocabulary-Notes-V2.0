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

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;
import tw.waterball.vocabnotes.services.PublicVocabService;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@RequestMapping("/api/public")
@RestController
public class PublicVocabController {

    private final PublicVocabService publicVocabService;

    @Autowired
    public PublicVocabController(PublicVocabService publicVocabService) {
        this.publicVocabService = publicVocabService;
    }

    @GetMapping("/dictionaries/{id}")
    public Dictionary getDictionary(@PathVariable int id) {
        return publicVocabService.getDictionary(id);
    }

    @PatchMapping("/dictionaries/{id}")
    public void patchDictionary(@PathVariable int id,
                                 @RequestBody @Valid DictionaryPatch dictionaryPatch) {
        publicVocabService.modifyDictionary(id, dictionaryPatch.title, dictionaryPatch.description);
    }

    @Accessors(fluent = true) @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DictionaryPatch {
        @Size(min = 1, max = 80)
        public String title;
        @Size(min = 1, max = 300)
        public String description;
    }

    @DeleteMapping("/dictionaries/{id}")
    public void deleteDictionary(@PathVariable int id) {
        publicVocabService.deleteDictionary(id);
    }

    @GetMapping("/dictionaries")
    public List<Dictionary> getDictionaries(@RequestParam(required = false) Integer limit,
                                            @RequestParam(required = false) Integer offset) {
        return publicVocabService.getDictionaries(offset, limit);
    }

    @PostMapping("/dictionaries")
    public ResponseEntity createDictionary(@RequestBody @Valid Dictionary dictionary) {
        if (dictionary.getId() != null) {
            return ResponseEntity.badRequest().body("You must not provide the Dictionary's id while creating it.");
        }
        return ResponseEntity.ok(publicVocabService.createDictionary(dictionary));
    }

    @GetMapping("/wordgroups/{id}")
    public WordGroup getWordGroup(@PathVariable int id) {
        return publicVocabService.getWordGroup(id);
    }

    @PatchMapping("/wordgroups/{id}")
    public void patchWordGroup(@PathVariable int id,
                                  @RequestBody @Valid WordGroupPatch wordGroupPatch) {
        publicVocabService.patchWordGroup(id, wordGroupPatch.title);
    }

    @Accessors(fluent = true) @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WordGroupPatch {
        @Size(min = 1, max = 50)
        public String title;
    }

    @DeleteMapping("/wordgroups/{id}")
    public void deleteWordGroup(@PathVariable int id) {
        publicVocabService.deleteWordGroup(id);
    }

    @PostMapping("/wordgroups")
    public ResponseEntity createWordGroup(@RequestBody @Valid WordGroup wordGroup) {
        if (wordGroup.getId() != null) {
            return ResponseEntity.badRequest().body("You must not provide the WordGroup's id while creating it.");
        }
        return ResponseEntity.ok(publicVocabService.createWordGroup(wordGroup));
    }

    @GetMapping("/word/{name}")
    public Word getWord(@PathVariable String name) {
        return publicVocabService.getWord(name);
    }

    @DeleteMapping("/word/{name}")
    public void deleteWord(@PathVariable String name) {
        publicVocabService.deleteWord(name);
    }

    @PatchMapping("/word/{name}")
    public void changeImageUrlOfWord(@PathVariable String name,
                                     @RequestBody String imageUrl) {
        publicVocabService.changeImageUrlOfWord(name, imageUrl);
    }

    @PostMapping("/word")
    public ResponseEntity createWord(@RequestBody @Valid Word word) {
        if (word.getId() != null) {
            return ResponseEntity.badRequest().body("You must not provide the Word's id while creating it.");
        }
        return ResponseEntity.ok(publicVocabService.createWord(word));
    }

    @PutMapping("/word/{id}")
    public void updateWord(@PathVariable int id,
                           @RequestBody @Valid Word word) {
        word.setId(id);
        publicVocabService.updateWord(word);
    }

    @PostMapping("/wordgroup/{wordGroupId}/word/{wordName}")
    public void addWordIntoWordGroup(@PathVariable int wordGroupId,
                                     @PathVariable String wordName) {
        publicVocabService.addWordIntoWordGroup(wordName, wordGroupId);
    }

    @PostMapping("/dictionaries/{dictionaryId}/wordgroups/{wordGroupId}")
    public void addWordGroupIntoDictionary(@PathVariable int dictionaryId,
                                           @PathVariable int wordGroupId) {
        publicVocabService.addWordGroupIntoDictionary(wordGroupId, dictionaryId);
    }

    @DeleteMapping("/wordgroup/{wordGroupId}/word/{wordName}")
    public void removeWordFromWordGroup(@PathVariable int wordGroupId,
                                        @PathVariable String wordName) {
        publicVocabService.removeWordFromWordGroup(wordName, wordGroupId);
    }

    @DeleteMapping("/dictionaries/{dictionaryId}/wordgroups/{wordGroupId}")
    public void removeWordGroupFromDictionary(@PathVariable int dictionaryId,
                                              @PathVariable int wordGroupId) {
        publicVocabService.removeWordGroupFromDictionary(wordGroupId, dictionaryId);
    }

    @GetMapping("/dictionaries/{dictionaryId}/wordgroups")
    public List<WordGroup> getWordGroups(@PathVariable int dictionaryId,
                                         @RequestParam(required = false) Integer offset,
                                         @RequestParam(required = false) Integer limit) {
        return publicVocabService.getWordGroups(dictionaryId, offset, limit);
    }

}
