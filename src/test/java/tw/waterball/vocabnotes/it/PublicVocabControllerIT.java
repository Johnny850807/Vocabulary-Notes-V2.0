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

package tw.waterball.vocabnotes.it;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tw.waterball.vocabnotes.VocabNotesApplication;
import tw.waterball.vocabnotes.api.PublicVocabController;
import tw.waterball.vocabnotes.api.PublicVocabController.DictionaryPatch;
import tw.waterball.vocabnotes.api.PublicVocabController.WordGroupPatch;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.WordGroup;
import tw.waterball.vocabnotes.utils.EntityEquality;
import tw.waterball.vocabnotes.utils.RandomEntityGenerator;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author johnny850807@gmail.com (Waterball))
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = VocabNotesApplication.class)
public class PublicVocabControllerIT {
    @Autowired
    private Gson gson;

    @Autowired
    private PublicVocabController publicVocabController;

    private MockMvc mockMvc;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(publicVocabController)
                .build();
    }

    private final static MediaType APPLICATION_JSON_UTF8 = new MediaType(APPLICATION_JSON, StandardCharsets.UTF_8);
    private final static String API_PREFIX = "/api/public";
    private final static String PATCH_DICT_TITLE = "Patch-Dict-title";
    private final static String PATCH_DICT_DESCRIPTION = "Patch-Dict-description";
    private final static String PATCH_WG_TITLE_PREFIX = "PP-";

    /**
     * Test with the following scenario:
     * 1. Test Dictionary
     * 1.1 Post a random dictionary 'D'
     * 1.2 Get 'D' and verify
     * 1.3 Patch 'D' with title 'Patch-Dict-title'
     * 1.4 Path 'D' with description 'Patch-Dict-description'
     * 1.5 Get 'D' and verify
     * 2. Test Word Groups (in parallel)
     * 2.1 Post 10 random Word groups 'WGs'
     * 2.2 Get every word group in 'WGs' and verify
     * 2.3 Patch every word group whose title is not null in 'WGs' with a prefix 'PP-' to its title
     * 2.4 Get every word group in 'WGs' and verify
     * 3. Test Words (in parallel)
     * 3.1 Post 10 random Words 'Wds'
     * 3.2 Get every word in 'Wds' and verify
     * 3.3 Patch every word in 'Wds' with imageUrl 'https://upload.wikimedia.org/wikipedia/commons/thumb/1/11/Test-Logo.svg/783px-Test-Logo.svg.png'
     * 3.4 Get every word in 'Wds' and verify
     * 3.5 Create a random word 'W'
     * 3.6 Put every word with new value 'W'
     * 3.7 Get every word in 'Wds' and verify
     * 4. Test associations
     * 4.1 Get word groups from 'D' and expect an empty list responded
     * 4.2 Post, adding every word group in 'WGs' into 'D'
     * 4.3 Get word groups from 'D' and verify
     * 4.4 Get word groups from 'D' with offset 0, limit 5 then offset 5, limit 5 and verify
     * 4.5 Delete, removing every word group in 'WGs' but the last one ('LAST_WG') into 'D'
     * 4.6 Get word groups from 'D' and expect only 'LAST_WG' responded
     * 4.7 Post, adding every word in 'Wds' into 'LAST_WG'
     * 4.8 Get 'LAST_WG' and verify
     * 4.9 Delete, removing every word in 'Wds' from 'LAST_WG'
     * 4.10 Get 'LAST_WG' and expect an empty list responded
     * 5. Test Deletion
     * 5.1 Delete 'D'
     * 5.2 Get 'D' and expect 404
     * 5.3 Delete every word group in 'WGs'
     * 5.4 Get every word group in 'WGs' and expect 404
     * 5.5 Delete every word in 'Wds'
     * 5.6 Get every word in 'Wds' and expect 404
     */
    @Test
    public void testAllEndpoints() throws Exception {
        testDictionary();
        testWordGroups();
    }

    private void testDictionary() throws Exception {
        Dictionary D = RandomEntityGenerator.randomDictionary(Dictionary.Type.PUBLIC,
                0, 0, 0, 0);

        D.setId(postDictionaryAndReturnId(D));
        assertMvcResponseCorrect(getDictionary(D.getId()), D);

        patchDictionary(D);
        assertMvcResponseCorrect(getDictionary(D.getId()), D);
    }

    private int postDictionaryAndReturnId(Dictionary D) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(apiPrefix("/dictionaries"))
                .content(gson.toJson(D)).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();
        Dictionary result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Dictionary.class);
        return result.getId();
    }

    private MvcResult getDictionary(int dictionaryId) throws Exception {
        return mockMvc.perform(get(apiPrefix("/dictionaries/{id}"), dictionaryId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();
    }

    private void patchDictionary(Dictionary D) throws Exception {
        mockMvc.perform(patch(apiPrefix("/dictionaries/{id}"), D.getId())
                .content(gson.toJson(new DictionaryPatch().title(PATCH_DICT_TITLE))).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        D.setTitle(PATCH_DICT_TITLE);

        mockMvc.perform(patch(apiPrefix("/dictionaries/{id}"), D.getId())
                .content(gson.toJson(new DictionaryPatch().description(PATCH_DICT_DESCRIPTION))).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        D.setDescription(PATCH_DICT_DESCRIPTION);
    }

    private void testWordGroups() {
        List<WordGroup> WGs = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            WGs.add(RandomEntityGenerator.randomWordGroup(0, 0));
        }

        WGs.parallelStream().forEach(wg -> {
            try {
                wg.setId(postWordGroupAndReturnId(wg));
                assertMvcResponseCorrect(getWordGroup(wg.getId()), wg);
                if (wg.getTitle() != null) {
                    patchWordGroup(wg);
                    assertMvcResponseCorrect(getWordGroup(wg.getId()), wg);
                }
            } catch (Exception e) {
                fail(e);
            }
        });
    }

    private int postWordGroupAndReturnId(WordGroup WG) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(apiPrefix("/wordgroups"))
                .content(gson.toJson(WG)).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();
        Dictionary result = gson.fromJson(mvcResult.getResponse().getContentAsString(), Dictionary.class);
        return result.getId();
    }

    private MvcResult getWordGroup(int wordGroupId) throws Exception {
        return mockMvc.perform(get(apiPrefix("/wordgroups/{id}"), wordGroupId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();
    }

    private void patchWordGroup(WordGroup wg) throws Exception {
        mockMvc.perform(patch(apiPrefix("/wordgroups/{id}"), wg.getId())
                .content(gson.toJson(new WordGroupPatch().title(PATCH_WG_TITLE_PREFIX + wg.getTitle()))).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        wg.setTitle(PATCH_WG_TITLE_PREFIX + wg.getTitle());
    }

    @SuppressWarnings("unchecked")
    private <T> void assertMvcResponseCorrect(MvcResult mvcResult, T expectedEntity) throws UnsupportedEncodingException {
        String json = mvcResult.getResponse().getContentAsString();
        Class<T> type = (Class<T>) expectedEntity.getClass();
        T actualEntity = gson.fromJson(json, type);
        assertTrue(EntityEquality.equals(actualEntity, expectedEntity, false));
    }


    private static String apiPrefix(String resourceUrlFormat, Object... params) {
        return API_PREFIX + String.format(resourceUrlFormat, params);
    }

}
