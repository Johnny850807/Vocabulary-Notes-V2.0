package tw.waterball.vocabnotes.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import tw.waterball.vocabnotes.BaseSpringTest;
import tw.waterball.vocabnotes.VocabNotesApplication;
import tw.waterball.vocabnotes.services.dto.Requests;
import tw.waterball.vocabnotes.services.dto.DictionaryDTO;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.WordGroup;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tw.waterball.vocabnotes.services.dto.DictionaryDTO.project;

@Sql(scripts = {"classpath:clear.sql", "classpath:stub.sql"})
@ContextConfiguration(classes = VocabNotesApplication.class)
@AutoConfigureTestEntityManager
@Transactional
class StandardDictionaryServiceTest extends BaseSpringTest {
    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private TestEntityManager em;

    @Test
    void modifyDictionary() {
        DictionaryDTO dictionaryDTO = project(em.find(Dictionary.class, 1));
        dictionaryDTO.setTitle("modified-title");

        dictionaryService.modifyDictionary(1,
                new Requests.DictionaryInfo(dictionaryDTO.getTitle(), null));

        commitAndRestartTransaction();
        assertEquals(dictionaryDTO, project(em.find(Dictionary.class, 1)));

        dictionaryDTO.setDescription("modified-description");
        dictionaryService.modifyDictionary(1,
                new Requests.DictionaryInfo(null, dictionaryDTO.getDescription()));

        commitAndRestartTransaction();
        assertEquals(dictionaryDTO, project(em.find(Dictionary.class, 1)));
    }

    @Test
    void createPublicDictionary() {
        DictionaryDTO created = dictionaryService.createPublicDictionary(
                new Requests.DictionaryInfo("title", "descritpion"));
        commitAndRestartTransaction();
        assertEquals(project(em.find(Dictionary.class, created.getId())), created);
    }

    @Test
    void getDictionary() {
        DictionaryDTO dictionaryDTO = dictionaryService.getDictionary(1);
        assertEquals(project(em.find(Dictionary.class, 1)), dictionaryDTO);
    }

    @Test
    void getPublicDictionaries() {
        List<DictionaryDTO> dictionaries = dictionaryService.getPublicDictionaries();
        DictionaryDTO expectedStub = project(em.find(Dictionary.class, 1));
        assertEquals(1, dictionaries.size());
        assertEquals(expectedStub, dictionaries.get(0));

        DictionaryDTO created = project(em.persistAndFlush(
                new Dictionary("t", "d", Dictionary.Type.PUBLIC)));

        commitAndRestartTransaction();
        dictionaries = dictionaryService.getPublicDictionaries();

        assertEqualsIgnoreOrder(Arrays.asList(expectedStub, created), dictionaries);
    }

    @Test
    void deletePublicDictionary() {
        dictionaryService.deletePublicDictionary(1);
        Dictionary found = em.find(Dictionary.class, 1);
        assertNull(found);
    }

    @Test
    void removeWordGroupFromDictionary() {
        dictionaryService.removeWordGroupFromDictionary(1, 1);
        commitAndRestartTransaction();

        Dictionary dictionary = em.find(Dictionary.class, 1);
        assertTrue(dictionary.getWordGroups().isEmpty());
    }

    @Test
    void addWordGroupIntoDictionary() {
        WordGroup newWordGroup = em.persistAndFlush(new WordGroup());
        dictionaryService.addWordGroupIntoDictionary(newWordGroup.getId(), 1);
        commitAndRestartTransaction();

        Dictionary dictionary = em.find(Dictionary.class, 1);
        assertEquals(2, dictionary.getWordGroups().size());
        assertTrue(dictionary.getWordGroups().contains(
                em.find(WordGroup.class, newWordGroup.getId())));

    }
}