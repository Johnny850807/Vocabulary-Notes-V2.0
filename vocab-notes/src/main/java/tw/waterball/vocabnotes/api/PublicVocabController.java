package tw.waterball.vocabnotes.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;
import tw.waterball.vocabnotes.services.PublicVocabService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
                                 @RequestBody @Valid Dictionary dictionary) {
        publicVocabService.patchDictionary(id, Optional.ofNullable(dictionary.getTitle()),
                Optional.ofNullable(dictionary.getDescription()));
    }

    @DeleteMapping("/dictionaries/{id}")
    public void deleteDictionary(@PathVariable int id) {
        publicVocabService.deleteDictionary(id);
    }

    @GetMapping("/dictionaries")
    public List<Dictionary> getDictionaries(@RequestParam Integer limit,
                                            @RequestParam Integer offset) {
        return publicVocabService.getDictionaries(limit, offset);
    }

    @PostMapping("/dictionaries")
    public Dictionary createDictionary(@RequestBody @Valid Dictionary dictionary) {
        return publicVocabService.createDictionary(dictionary);
    }

    @GetMapping("/wordgroups/{id}")
    public WordGroup getWordGroup(@PathVariable int id) {
        return publicVocabService.getWordGroup(id);
    }

    @PatchMapping("/wordgroups/{id}")
    public void getWordGroup(@PathVariable int id,
                                  @RequestBody @Valid WordGroup wordGroup) {
        publicVocabService.patchWordGroup(id, wordGroup.getTitle());
    }

    @DeleteMapping("/wordgroups/{id}")
    public void deleteWordGroup(@PathVariable int id) {
        publicVocabService.deleteWordGroup(id);
    }

    @PostMapping("/wordgroups")
    public WordGroup createWordGroup(@RequestBody @Valid WordGroup wordGroup) {
        return publicVocabService.createWordGroup(wordGroup);
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
    public Word createWord(@RequestBody @Valid Word word) {
        return publicVocabService.createWord(word);
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
    public List<WordGroup> getWordGroups(@PathVariable int dictionaryId, Integer offset, Integer limit) {
        return publicVocabService.getWordGroups(dictionaryId, offset, limit);
    }

}
