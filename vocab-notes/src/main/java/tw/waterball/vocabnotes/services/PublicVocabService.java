package tw.waterball.vocabnotes.services;

import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;

import java.util.List;


public interface PublicVocabService {
    Dictionary getDictionary(int dictionaryId);
    List<Dictionary> getDictionaries(Integer offset, Integer limit);
    WordGroup getWordGroup(int wordGroupId);
    Word getWord(String wordName);


    Dictionary createDictionary(Dictionary dictionary);
    void patchDictionary(int dictionaryId, String title, String description);
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

}
