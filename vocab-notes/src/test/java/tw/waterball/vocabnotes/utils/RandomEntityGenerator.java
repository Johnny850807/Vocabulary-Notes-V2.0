package tw.waterball.vocabnotes.utils;

import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static tw.waterball.vocabnotes.utils.RandomGenerator.randomHttpUrl;
import static tw.waterball.vocabnotes.utils.RandomGenerator.randomString;

public class RandomEntityGenerator {
    private final static Random RANDOM =new Random();
    public static Dictionary randomDictionary(Dictionary.Type type, int minWordGroupCount, int maxWordGroupCount,
                                              int minWordCount, int maxWordCount) {
        Dictionary dictionary = new Dictionary();
        dictionary.setTitle(randomString(4, 20, true));
        dictionary.setDescription(randomString(0, 100, true));
        dictionary.setType(type);

        int wordGroupCount = RANDOM.nextInt(maxWordGroupCount-minWordGroupCount+1)+minWordGroupCount;
        Set<WordGroup> wordGroups = new HashSet<>();
        for (int i = 0; i < wordGroupCount; i++) {
            wordGroups.add(randomWordGroup(minWordCount, maxWordCount));
        }
        dictionary.setWordGroups(wordGroups);
        return dictionary;
    }

    public static WordGroup randomWordGroup(int minWordCount, int maxWordCount) {
        WordGroup wordGroup = new WordGroup();
        wordGroup.setTitle(randomString(0, 15, false));
        int wordCount = RANDOM.nextInt(maxWordCount-minWordCount+1)+minWordCount;
        Set<Word> words = new HashSet<>();
        for (int i = 0; i < wordCount; i++) {
            words.add(randomWord());
        }
        wordGroup.setWords(words);
        return wordGroup;
    }

    public static Word randomWord() {
        Word word = new Word();
        word.setName(randomString(1, 20, false));

        int synonymCount = RANDOM.nextInt(3);
        List<String> synonyms = new ArrayList<>(synonymCount);
        for (int i = 0; i < synonymCount; i++) {
            synonyms.add(randomString(3, 5, false));
        }
        word.setSynonyms(synonyms);
        word.setImageUrl(randomHttpUrl(false));
        return word;
    }
}