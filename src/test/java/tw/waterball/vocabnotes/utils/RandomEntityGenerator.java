package tw.waterball.vocabnotes.utils;

import tw.waterball.vocabnotes.models.entities.*;
import tw.waterball.vocabnotes.models.entities.Dictionary;

import java.util.*;

import static tw.waterball.vocabnotes.utils.RandomGenerator.randomHttpUrl;
import static tw.waterball.vocabnotes.utils.RandomGenerator.randomString;

public class RandomEntityGenerator {
    private final static Random RANDOM =new Random();

    public static Member randomMember(Member.Role role) {
        Member member = new Member();
        member.setRole(role);
        member.setAge(RANDOM.nextInt(80)+1);
        member.setEmail(randomString(5, 10) + "@email.com");
        member.setFirstName(randomString(5, 10));
        member.setLastName(randomString(5, 10));
        member.setPassword(randomString(128));
        member.setExp(RANDOM.nextInt(Level.getMaxLevel().getMinExp()));
        return member;
    }

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
        wordGroup.setTitle(RANDOM.nextBoolean() ? null : randomString(4, 15, false));
        minWordCount = Math.max(2, minWordCount);
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

    public static void main(String[] args) {
        Dictionary dictionary = randomDictionary(Dictionary.Type.PUBLIC,
                1, 15,
                2, 10);
        Member member = randomMember(Member.Role.ADMIN);
        dictionary.setOwner(member);
        System.out.println(dictionary);
    }
}
