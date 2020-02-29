package tw.waterball.vocabnotes.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import tw.waterball.vocabnotes.BaseSpringTest;
import tw.waterball.vocabnotes.VocabNotesApplication;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;
import tw.waterball.vocabnotes.utils.EntityEquality;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = {"classpath:clear.sql", "classpath:stub.sql"})
@ContextConfiguration(classes = VocabNotesApplication.class)
@AutoConfigureTestEntityManager
@Transactional
class StandardWordGroupServiceTest extends BaseSpringTest {
    @Autowired
    private StandardWordGroupService wordGroupService;

    @Autowired
    private TestEntityManager em;

    @Test
    void testGetWordGroup() {
        WordGroup expected = em.find(WordGroup.class, 1);
        assertEntityEquals(expected, wordGroupService.getWordGroup(1));
    }

    @Test
    void testCreateWordGroup() {
        WordGroup created = em.persistAndFlush(new WordGroup());
        commitAndRestartTransaction();

        assertEntityEquals(created, wordGroupService.getWordGroup(created.getId()));
    }

    @Test
    void testPatchWordGroup() {
        wordGroupService.patchWordGroup(1, "patched");
        commitAndRestartTransaction();

        assertEquals("patched",
                em.find(WordGroup.class, 1).getTitle());
    }

    @Test
    void testDeleteWordGroup() {
        wordGroupService.deleteWordGroup(1);
        commitAndRestartTransaction();

        assertNull(em.find(WordGroup.class, 1));

        assertTrue(em.find(Dictionary.class, 1).getWordGroups().isEmpty(),
                "The dictionary-wordGroup association is not deleted after" +
                        " the involved wordGroup is deleted.");
    }

    @Test
    void testAddWordIntoWordGroup() {
        Word newWord = new Word("newWord", "", "https://sample.com/image.png");
        em.persistAndFlush(newWord);
        wordGroupService.addWordIntoWordGroup("newWord", 1);
        commitAndRestartTransaction();

        WordGroup wordGroup = em.find(WordGroup.class, 1);
        assertTrue(EntityEquality.contains(wordGroup.getWords(), newWord));
    }

    @Test
    void testRemoveWordFromWordGroup() {
        wordGroupService.removeWordFromWordGroup("lease", 1);
        commitAndRestartTransaction();

        WordGroup wordGroup = em.find(WordGroup.class, 1);
        assertEquals(2, wordGroup.getWords().size());
    }

    @Test
    void testGetWordGroups() {
        List<WordGroup> wordGroups = wordGroupService.getWordGroups(1);
        assertEntityEquals(em.find(WordGroup.class, 1), wordGroups.get(0));
    }
}