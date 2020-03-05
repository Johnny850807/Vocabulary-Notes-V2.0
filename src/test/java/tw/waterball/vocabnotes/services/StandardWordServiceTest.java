package tw.waterball.vocabnotes.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import tw.waterball.vocabnotes.BaseSpringTest;
import tw.waterball.vocabnotes.VocabNotesApplication;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.services.dto.WordDTO;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = {"classpath:clear.sql", "classpath:stub.sql"})
@ContextConfiguration(classes = VocabNotesApplication.class)
@AutoConfigureTestEntityManager
@Transactional
class StandardWordServiceTest extends BaseSpringTest {
    @Autowired
    private StandardWordService wordService;

    @Autowired
    private TestEntityManager em;

    @Test
    void testCreateWord() {
        Word createdWord = wordService.createWord(new Word("newWord", "", "http://sample.com/image.png"));
        commitAndRestartTransaction();

        assertEntityEquals(em.find(Word.class, createdWord.getId()),
                createdWord);
    }

    @Test
    void testChangeImageUrlOfWord() {
        String newURL = "http://sample.com/image.png";
        wordService.changeImageUrlOfWord("lease", newURL);
        commitAndRestartTransaction();

        assertEquals(newURL, em.find(Word.class, 1).getImageUrl());
    }

    @Test
    void testUpdateWord() {
        Word word = em.find(Word.class, 1);
        word.setName("updated");
        wordService.updateWord(word);
        commitAndRestartTransaction();

        assertEntityEquals(word, em.find(Word.class, 1));
    }

    @Test
    void testDeleteWord() {
        wordService.deleteWord("lease");
        commitAndRestartTransaction();

        assertNull(em.find(Word.class, 1));

        wordService.deleteWord("treaty");
        commitAndRestartTransaction();

        assertNull(em.find(Word.class, 2));

        wordService.deleteWord("stipulation");
        commitAndRestartTransaction();

        assertNull(em.find(Word.class, 3));
    }

    @Test
    void testGetWord() {
        assertEquals(WordDTO.project(em.find(Word.class, 1)),
                wordService.getWord("lease"));
    }
}