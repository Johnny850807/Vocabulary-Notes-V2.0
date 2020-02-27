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

package tw.waterball.vocabnotes.models.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tw.waterball.vocabnotes.BaseSpringTest;
import tw.waterball.vocabnotes.VocabNotesApplication;
import tw.waterball.vocabnotes.models.dto.DictionaryDTO;
import tw.waterball.vocabnotes.utils.PagingTrigger;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author johnny850807 (Waterball)
 */
@Sql(scripts = "classpath:dictionaries.sql")
@ContextConfiguration(classes = VocabNotesApplication.class)
@AutoConfigureTestEntityManager
@Transactional
class PagingDictionaryRepositoryImplTest extends BaseSpringTest {
    private final int PUBLIC_START_ID = 1;
    private final int OWN_START_ID = 14;
    private final int OWNER_ID = 1;

    @Autowired
    private DictionaryRepository dictionaryRepository;

    private HashSet<DictionaryDTO> collecting;

    @BeforeEach
    public void setup() {
        collecting = new HashSet<>();
    }

    @Test
    public void testPagingPublicDictionary() {
        PagingTrigger.perform(0, 3,
                dictionaryRepository::findPublicDictionaries,
                (off, lim, page) -> testPageResult(PUBLIC_START_ID, off, lim, page));

    }

    @Test
    public void testPagingOwnDictionary() {
        PagingTrigger.perform(0, 3,
                (off, lim) -> dictionaryRepository.findOwnDictionaries(OWNER_ID, off, lim),
                (off, lim, page) -> testPageResult(OWN_START_ID, off, lim, page));
    }

    private void testPageResult(int startId, int offset, int limit, List<DictionaryDTO> page) {
        assertTrue(page.size() <= limit);
        for (DictionaryDTO dictionary : page) {
            assertFalse(collecting.contains(dictionary));
        }
        collecting.addAll(page);
        for (int i = 0; i < page.size(); i++) {
            assertEquals(startId + offset + i, page.get(i).getId());
        }
    }

}