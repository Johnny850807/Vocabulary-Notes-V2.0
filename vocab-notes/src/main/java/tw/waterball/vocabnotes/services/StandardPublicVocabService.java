package tw.waterball.vocabnotes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;
import tw.waterball.vocabnotes.models.repositories.DictionaryRepository;
import tw.waterball.vocabnotes.models.repositories.OffsetPageRequest;

import java.util.List;

@Service
public class StandardPublicVocabService implements PublicVocabService {
    private final static String DICTIONARY = "dictionary";
    private final static String WORD_GROUP = "word";
    private final static String WORD = "word-group";
    private DictionaryRepository dictionaryRepository;

    @Autowired
    public StandardPublicVocabService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public Dictionary getDictionary(int dictionaryId) {
        return dictionaryRepository.findById(dictionaryId)
                .orElseThrow(() -> new PublicVocabNotFoundException(DICTIONARY, dictionaryId));
    }

    @Override
    public List<Dictionary> getDictionaries(Integer offset, Integer limit) {
        offset = offset == null ? 0 : offset;
        if (limit == null) {
            return dictionaryRepository.findAll(new OffsetPageRequest(offset)).toList();
        }
        return dictionaryRepository.findAll(new OffsetPageRequest(offset, limit)).toList();
    }

    @Override
    public WordGroup getWordGroup(int wordGroupId) {
        return null;
    }

    @Override
    public Word getWord(String wordName) {
        return null;
    }

    @Override
    public Dictionary createDictionary(Dictionary dictionary) {
        return null;
    }

    @Override
    public void patchDictionary(int dictionaryId, String title, String description) {

    }

    @Override
    public void deleteDictionary(int dictionaryId) {

    }

    @Override
    public WordGroup createWordGroup(WordGroup wordGroup) {
        return null;
    }

    @Override
    public void patchWordGroup(int wordGroupId, String title) {

    }

    @Override
    public void deleteWordGroup(int wordGroupId) {

    }

    @Override
    public Word createWord(Word word) {
        return null;
    }

    @Override
    public void changeImageUrlOfWord(String wordName, String imageUrl) {

    }

    @Override
    public void updateWord(Word word) {

    }

    @Override
    public void deleteWord(String wordName) {

    }

    @Override
    public void addWordIntoWordGroup(String wordName, int wordGroupId) {

    }

    @Override
    public void addWordGroupIntoDictionary(int wordGroupId, int dictionaryId) {

    }

    @Override
    public void removeWordFromWordGroup(String wordName, int wordGroupId) {

    }

    @Override
    public void removeWordGroupFromDictionary(int wordGroupId, int dictionaryId) {

    }
}
