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

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RequestMapping("/api/public")
@RestController
public class PublicVocabController {

    private final PublicVocabService publicVocabService;

    @Autowired
    public PublicVocabController(PublicVocabService publicVocabService) {
        this.publicVocabService = publicVocabService;
    }

    @RequestMapping(method = GET, path = "/dictionaries/{id}")
    public ResponseEntity<Dictionary> getDictionary(@PathVariable int id) {
        return ResponseEntity.ok(publicVocabService.getDictionary(id));
    }

    @RequestMapping(method = PATCH, path = "/dictionaries/{id}")
    public void patchDictionary(@PathVariable int id,
                                 @RequestBody @Valid Dictionary dictionary) {
        publicVocabService.patchDictionary(id, dictionary.getTitle(), dictionary.getDescription());
    }

    @RequestMapping(method = DELETE, path = "/dictionaries/{id}")
    public void deleteDictionary(@PathVariable int id) {
        publicVocabService.deleteDictionary(id);
    }

    @RequestMapping(method = GET, path = "/dictionaries")
    public List<Dictionary> getDictionaries(@RequestParam Integer limit,
                                            @RequestParam Integer offset) {
        return publicVocabService.getDictionaries(limit, offset);
    }

    @RequestMapping(method = POST, path = "/dictionaries")
    public Dictionary createDictionary(@RequestBody @Valid Dictionary dictionary) {
        return publicVocabService.createDictionary(dictionary);
    }


    @RequestMapping(method = GET, path = "/wordgroups/{id}")
    public WordGroup getWordGroup(@PathVariable int id) {
        return publicVocabService.getWordGroup(id);
    }

    @RequestMapping(method = PATCH, path = "/wordgroups/{id}")
    public void getWordGroup(@PathVariable int id,
                                  @RequestBody @Valid WordGroup wordGroup) {
        publicVocabService.patchWordGroup(id, wordGroup.getTitle());
    }

    @RequestMapping(method = DELETE, path = "/wordgroups/{id}")
    public void deleteWordGroup(@PathVariable int id) {
        publicVocabService.deleteWordGroup(id);
    }

    @RequestMapping(method = POST, path = "/wordgroups")
    public WordGroup createWordGroup(@RequestBody @Valid WordGroup wordGroup) {
        return publicVocabService.createWordGroup(wordGroup);
    }

    @RequestMapping(method = GET, path = "/word/{name}")
    public Word getWord(@PathVariable String name) {
        return publicVocabService.getWord(name);
    }

    @RequestMapping(method = DELETE, path = "/word/{name}")
    public void deleteWord(@PathVariable String name) {
        publicVocabService.deleteWord(name);
    }

    @RequestMapping(method = PATCH, path = "/word/{name}")
    public void changeImageUrlOfWord(@PathVariable String name,
                                     @RequestBody String imageUrl) {
        publicVocabService.changeImageUrlOfWord(name, imageUrl);
    }

    @RequestMapping(method = POST, path = "/word")
    public Word createWord(@RequestBody @Valid Word word) {
        return publicVocabService.createWord(word);
    }

    @RequestMapping(method = PUT, path = "/word/{id}")
    public void updateWord(@PathVariable int id,
                           @RequestBody @Valid Word word) {
        word.setId(id);
        publicVocabService.updateWord(word);
    }

    @RequestMapping(method = POST, path = "/wordgroup/{wordGroupId}/word/{wordName}")
    public void addWordIntoWordGroup(@PathVariable int wordGroupId,
                                     @PathVariable String wordName) {
        publicVocabService.addWordIntoWordGroup(wordName, wordGroupId);
    }

    @RequestMapping(method = POST, path = "/dictionaries/{dictionaryId}/wordgroups/{wordGroupId}")
    public void addWordGroupIntoDictionary(@PathVariable int dictionaryId,
                                           @PathVariable int wordGroupId) {
        publicVocabService.addWordGroupIntoDictionary(wordGroupId, dictionaryId);
    }

    @RequestMapping(method = DELETE, path = "/wordgroup/{wordGroupId}/word/{wordName}")
    public void removeWordFromWordGroup(@PathVariable int wordGroupId,
                                        @PathVariable String wordName) {
        publicVocabService.removeWordFromWordGroup(wordName, wordGroupId);
    }

    @RequestMapping(method = DELETE, path = "/dictionaries/{dictionaryId}/wordgroups/{wordGroupId}")
    public void removeWordGroupFromDictionary(@PathVariable int dictionaryId,
                                              @PathVariable int wordGroupId) {
        publicVocabService.removeWordGroupFromDictionary(wordGroupId, dictionaryId);
    }

}
