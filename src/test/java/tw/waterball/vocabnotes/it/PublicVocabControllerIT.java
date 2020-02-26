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
import com.google.gson.JsonElement;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
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
import tw.waterball.vocabnotes.api.Requests;
import tw.waterball.vocabnotes.api.Requests.ModifyDictionary;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.IdEntity;
import tw.waterball.vocabnotes.models.entities.Word;
import tw.waterball.vocabnotes.models.entities.WordGroup;
import tw.waterball.vocabnotes.utils.EntityEquality;
import tw.waterball.vocabnotes.utils.PermutationUtils;
import tw.waterball.vocabnotes.utils.RandomEntityGenerator;
import tw.waterball.vocabnotes.utils.functional.ExceptionalConsumer;
import tw.waterball.vocabnotes.utils.functional.ExceptionalRunnable;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This Integration Test targets the PublicVocabController with the following scenarios.
 *
 * First, define 5 scenarios.
 * Three of them are 'Creation Scenarios' which are numbered 1, 2 and 3.
 *
 * Creation Scenarios:
 *
 * 1. Test Dictionary
 * 1.1 Post a random dictionary 'D'
 * 1.2 Get 'D' and verify
 * 1.3 Patch 'D' with title 'Patch-Dict-title'
 * 1.4 Path 'D' with description 'Patch-Dict-description'
 * 1.5 Get 'D' and verify
 *
 * 2. Test Word Groups (in parallel)
 * 2.1 Post 10 random Word groups 'WGs'
 * 2.2 Get every word group in 'WGs' and verify
 * 2.3 Patch every word group whose title is not null in 'WGs' with a prefix 'PP-' to its title
 * 2.4 Get every word group in 'WGs' and verify
 *
 * 3. Test Words (in parallel)
 * 3.1 Post 10 random Words 'Wds'
 * 3.2 Get every word in 'Wds' and verify
 * 3.3 Patch every word in 'Wds' with imageUrl 'https://upload.wikimedia.org/wikipedia/commons/thumb/1/11/Test-Logo.svg/783px-Test-Logo.svg.png'
 * 3.4 Get every word in 'Wds' and verify
 * 3.5 Create a random word 'W'
 * 3.6 Put every word with new value 'W'
 * 3.7 Get every word in 'Wds' and verify
 *
 * Associations:
 *
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
 *
 * Deletion Scenarios:
 *
 * 5. Test Deletion
 * There are three test-cases 5.A, 5.B, 5.C
 * 5.A
 * 5.A.1 Delete 'D'
 * 5.A.2 Get 'D' and expect 404
 * 5.A.3 Get every word groups in 'WGs' and verify
 * 5.A.4 Get every word in 'WDs' and verify
 * 5.B
 * 5.B.1 Delete every word group in 'WGs'
 * 5.B.2 Get every word group in 'WGs' and expect 404
 * 5.B.3 Get 'D' and verify
 * 5.B.4 Get every word in 'WDs' and verify
 * 5.C
 * 5.C.1 Delete every word in 'Wds'
 * 5.C.2 Get every word in 'Wds' and expect 404
 * 5.C.3 Get 'D' and verify
 * 5.C.4 Get every word in 'WGs' and verify
 *
 * The integration test is performed according to the following steps:
 *
 * Let 'Delete-Scenarios' be all the permutation of {5.A, 5.B, 5.C}
 * (So there are 6 Test Deletion scenarios in 'Delete-Scenarios'.)
 *
 * For each Test Deletion scenario 'd' in 'Delete-Scenarios', perform following two steps:
 * (1) Shuffle[1, 2, 3], 'd'
 * (2) Shuffle[1, 2, 3], 4, 'd'
 *
 * Where shuffle(1, 2, 3) means to determine the order of [1, 2, 3] by shuffling
 *
 * TODO make clean code and provide a good test scenario explanation document
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

    private Dictionary D;
    private List<WordGroup> WGs;
    private List<Word> Wds;

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
    private final static String PATCH_WD_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/11/Test-Logo.svg/783px-Test-Logo.svg.png";


    @Test
    public void integrationTest() throws Exception {
        List<List<ExceptionalRunnable>> deletionScenarioPermutation =
                PermutationUtils.permutation(Arrays.asList(this::deletionTestCaseA, this::deletionTestCaseB, this::deletionTestCaseC));

        for (List<ExceptionalRunnable> deletionScenario : deletionScenarioPermutation) {
            runDeletionTestScenario(deletionScenario, false);
            runDeletionTestScenario(deletionScenario, true);
        }
    }

    private void runDeletionTestScenario(List<ExceptionalRunnable> deletionScenario, boolean createAssociations) throws Exception {
        List<ExceptionalRunnable> creationScenarios = Arrays.asList(this::testDictionary, this::testWordGroups, this::testWords);
        Collections.shuffle(creationScenarios);
        for (ExceptionalRunnable creationScenario : creationScenarios) {
            creationScenario.run();
        }
        if (createAssociations) {
            testAssociations();
        }
        for (ExceptionalRunnable deleteScenario : deletionScenario) {
            deleteScenario.run();
        }
    }

    private void testDictionary() throws Exception {
        D = RandomEntityGenerator.randomDictionary(Dictionary.Type.PUBLIC,
                0, 0, 0, 0);

        D.setId(postEntityAndReturnId(D, "/dictionaries"));
        assertMvcResultCorrect(getEntityResult("/dictionaries/{id}", D.getId()), D);

        patchDictionary(D);
        assertMvcResultCorrect(getEntityResult("/dictionaries/{id}", D.getId()), D);
    }

    private void patchDictionary(Dictionary D) throws Exception {
        patchEntity(new ModifyDictionary().title(PATCH_DICT_TITLE), "/dictionaries/{id}", D.getId());
        D.setTitle(PATCH_DICT_TITLE);
        patchEntity(new ModifyDictionary().description(PATCH_DICT_DESCRIPTION), "/dictionaries/{id}", D.getId());
        D.setDescription(PATCH_DICT_DESCRIPTION);
    }

    private void testWordGroups() {
        WGs = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            WGs.add(RandomEntityGenerator.randomWordGroup(0, 0));
        }

        parallel(WGs, wg -> {
            wg.setId(postEntityAndReturnId(wg, "/wordgroups"));
            assertMvcResultCorrect(getEntityResult("/wordgroups/{id}", wg.getId()), wg);
            if (wg.getTitle() != null) {
                patchEntity(new Requests.ModifyWordGroup(PATCH_WG_TITLE_PREFIX + wg.getTitle()),
                        "/wordgroups/{id}", wg.getId());
                wg.setTitle(PATCH_WG_TITLE_PREFIX + wg.getTitle());
                assertMvcResultCorrect(getEntityResult("/wordgroups/{id}", wg.getId()), wg);
            }
        });
    }

    private void testWords() {
        Wds = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Wds.add(RandomEntityGenerator.randomWord());
        }

        parallel(Wds, wd -> {
            wd.setId(postEntityAndReturnId(wd, "/words"));
            assertMvcResultCorrect(getEntityResult("/words/{name}", wd.getName()), wd);

            patchEntity(PATCH_WD_IMAGE_URL, "/words/{name}", wd.getName());
            wd.setImageUrl(PATCH_WD_IMAGE_URL);
            assertMvcResultCorrect(getEntityResult("/words/{name}", wd.getName()), wd);

            Word W = RandomEntityGenerator.randomWord();
            putEntity(W, "/words/{id}", wd.getId());
            W.setId(wd.getId());
            assertMvcResultCorrect(getEntityResult("/words/{name}", W.getName()), W);

            putEntity(wd, "/words/{id}", wd.getId());
            assertMvcResultCorrect(getEntityResult("/words/{name}", wd.getName()), wd);
        });
    }

    private void testAssociations() throws Exception {
        final Type WGs_TYPE = new TypeToken<List<WordGroup>>() {}.getType();
        List<WordGroup> expectEmpty = getEntity(WGs_TYPE, "/dictionaries/{id}/wordgroups", D.getId());

        assertTrue(expectEmpty.isEmpty(), "The dictionary with no word groups should respond with empty word group list.");

        parallel(WGs, wg -> postToUrl("/dictionaries/{dictId}/wordgroups/{wgId}", D.getId(), wg.getId()));

        assertEqualLists(WGs, getEntity(WGs_TYPE, "/dictionaries/{id}/wordgroups", D.getId()));

        assertPaginationCorrect(WGs_TYPE,WGs,5, "/dictionaries/{id}/wordgroups?offset={off}&&limit={lim}", D.getId());

        WordGroup LAST_WG = WGs.get(WGs.size()-1);
        parallel(WGs.subList(0, WGs.size()-1), wg ->
                deleteToUrl("/dictionaries/{dictId}/wordgroups/{wgId}", D.getId(), wg.getId()));

        assertEqualLists(singletonList(LAST_WG),
                getEntity(WGs_TYPE, "/dictionaries/{id}/wordgroups", D.getId()));

        foreach(Wds, wd -> {
            LAST_WG.addWord(wd);
            postToUrl("/wordgroups/{wgId}/words/{wdName}", LAST_WG.getId(), wd.getName());
        });

        assertMvcResultCorrect(getEntityResult("/wordgroups/{wgId}", LAST_WG.getId()), LAST_WG, true);

        parallel(Wds, wd -> {
            LAST_WG.removeWord(wd);
            deleteToUrl("/wordgroups/{wgId}/words/{wdName}", LAST_WG.getId(), wd.getName());
        });

        assertMvcResultCorrect(getEntityResult("/wordgroups/{wgId}", LAST_WG.getId()), LAST_WG, true);
    }

    private void deletionTestCaseA() throws Exception {
        deleteToUrl("/dictionaries/{id}", D.getId());
        assertUrlNotFound("/dictionaries/{id}", D.getId());
    }

    private void deletionTestCaseB() throws Exception {
        parallel(WGs, wg -> deleteToUrl("/wordgroups/{id}", wg.getId()));
        parallel(WGs, wg -> assertUrlNotFound("/wordgroups/{id}", wg.getId()));
    }

    private void deletionTestCaseC() throws Exception {
        parallel(Wds, wd -> deleteToUrl("/words/{name}", wd.getName()));
        parallel(Wds, wd -> assertUrlNotFound("/words/{name}", wd.getName()));
    }

    private void assertUrlNotFound(String url, Object ...urlParams) throws Exception {
        mockMvc.perform(get(apiPrefix(url), urlParams))
                .andExpect(status().isNotFound());
    }

    private MvcResult postToUrl(String url, Object... urlParams) throws Exception {
        return mockMvc.perform(post(apiPrefix(url), urlParams))
                .andExpect(status().isOk())
                .andReturn();
    }

    private int postEntityAndReturnId(IdEntity idEntity, String url) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(apiPrefix(url))
                .content(gson.toJson(idEntity)).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();

        JsonElement jsonElement = Streams.parse(new JsonReader(new StringReader(mvcResult.getResponse().getContentAsString())));

        return jsonElement.getAsJsonObject().get("id").getAsInt();
    }

    private MvcResult deleteToUrl(String url, Object ...pks) throws Exception {
        return mockMvc.perform(delete(apiPrefix(url), pks))
                .andExpect(status().isOk())
                .andReturn();
    }

    private <T> T getEntity(Type type, String url, Object ...pks) throws Exception {
        MvcResult mvcResult = getEntityResult(url, pks);
        return gson.fromJson(mvcResult.getResponse().getContentAsString(), type);
    }

    private MvcResult getEntityResult(String url, Object ...pks) throws Exception {
        return mockMvc.perform(get(apiPrefix(url), pks))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();
    }

    private void patchEntity(Object bodyObj, String url, Object ...pks) throws Exception {
        mockMvc.perform(patch(apiPrefix(url), pks)
                .content(gson.toJson(bodyObj)).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private void patchEntity(String textPatch, String url, Object ...pks) throws Exception {
        mockMvc.perform(patch(apiPrefix(url), pks)
                .content(textPatch).contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }

    private void putEntity(IdEntity newEntity, String url, Object ...pks) throws Exception {
        mockMvc.perform(put(apiPrefix(url), pks)
                .content(gson.toJson(newEntity)).contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private <T extends IdEntity> void assertPaginationCorrect(Type listType, List<T> expected, int limit, String url, Object... pks) throws Exception {
        Object[] urlParams = new Object[pks.length+2];  //extend with offset, limit
        System.arraycopy(pks, 0, urlParams, 0, pks.length);

        List<T> pagingCollection = new ArrayList<>(expected.size());
        for (int i = 0; i < expected.size(); i+= limit) {
            urlParams[urlParams.length-2] = i; // offset
            urlParams[urlParams.length-1] = limit;
            List<T> page = getEntity(listType, url, urlParams);
            assertEquals(limit, page.size());
            assertTrue(EntityEquality.containsAll(expected, page), "Paged item not existing in expected list.");
            for (T t : page) {
                assertFalse(EntityEquality.contains(pagingCollection, t), "Pages overlapping.");
            }
            pagingCollection.addAll(page);
        }
        assertTrue(EntityEquality.equals(expected, pagingCollection), "Paging final result not matched to the expected list.");
    }

    private void assertEqualLists(Collection<? extends IdEntity> c1, Collection<? extends IdEntity> c2) {
        assertTrue(EntityEquality.equals(c1, c2));
    }

    private <T extends IdEntity> void assertMvcResultCorrect(MvcResult mvcResult, T expectedEntity) throws UnsupportedEncodingException {
        assertMvcResultCorrect(mvcResult, expectedEntity, false);
    }

    @SuppressWarnings("unchecked")
    private <T extends IdEntity> void assertMvcResultCorrect(MvcResult mvcResult, T expectedEntity, boolean testAssociations) throws UnsupportedEncodingException {
        String json = mvcResult.getResponse().getContentAsString();
        Class<T> type = (Class<T>) expectedEntity.getClass();
        T actualEntity = gson.fromJson(json, type);
        assertTrue(EntityEquality.equals(actualEntity, expectedEntity, testAssociations));
    }

    private <T> void foreach(List<T> list, ExceptionalConsumer<T> consumer) {
        list.forEach(t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                fail(e);
            }
        });
    }

    private <T> void parallel(List<T> list, ExceptionalConsumer<T> consumer) {
        list.parallelStream().forEach(t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                fail(e);
            }
        });
    }

    private static String apiPrefix(String resourceUrlFormat, Object... params) {
        return API_PREFIX + String.format(resourceUrlFormat, params);
    }

}
