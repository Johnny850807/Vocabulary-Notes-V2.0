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

package tw.waterball.vocabnotes.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import tw.waterball.vocabnotes.BaseSpringTest;
import tw.waterball.vocabnotes.VocabNotesApplication;
import tw.waterball.vocabnotes.api.Requests;
import tw.waterball.vocabnotes.models.dto.*;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Member;
import tw.waterball.vocabnotes.models.entities.WordGroup;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author johnny850807 (Waterball)
 */
@Sql(scripts = {"classpath:clear.sql", "classpath:stub.sql"})
@ContextConfiguration(classes = VocabNotesApplication.class)
@AutoConfigureTestEntityManager
@Transactional
class StandardMemberServiceTest extends BaseSpringTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private StandardMemberService memberService;

    private MemberDTO stubMember = new MemberDTO(1, "Johnny", "Pan", 23,
            "johnny850807@gmail.com", 0, 1, Member.Role.MEMBER);
    private String stubPassword = "hashed";


    @Test
    void testCreateThenGetMember() {
        MemberDTO memberDTO = memberService.createMember(new Requests.RegisterMember(
                new Requests.CreateMember("firstName", "lastName", 1),
                new Credentials("email@email.com", "password")));

        commitAndRestartTransaction();

        MemberDTO memberGot = memberService.getMember(memberDTO.getId());
        assertEquals(memberDTO, memberGot);
    }

    @Test
    void testLoginMember() {
        MemberDTO memberLogin = memberService.login(stubMember.getEmail(), stubPassword);
        assertEquals(stubMember, memberLogin);
    }

    @Test
    void testUpdateMember() {
        memberService.updateMember(stubMember.getId(),
                new Requests.UpdateMember("update", "update", 15));

        commitAndRestartTransaction();

        MemberDTO updatedMember = memberService.getMember(stubMember.getId());
        assertEquals(stubMember.getId(), updatedMember.getId());
        assertEquals("update", updatedMember.getFirstName());
        assertEquals("update", updatedMember.getLastName());
        assertEquals(15, updatedMember.getAge());
    }

    @Test
    void testCreateOwnDictionary() {
        DictionaryDTO dictionaryDTO = memberService.createOwnDictionary(stubMember.getId(),
                new Requests.CreateDictionary("title", "description"));

        commitAndRestartTransaction();

        assertEquals(getOwnDictionaryFromEM(stubMember.getId(), dictionaryDTO.getId()),
                dictionaryDTO);
    }

    @Test
    void testReferenceWordGroup() {
        WordGroupDTO wordGroup = em.persistAndFlush(new WordGroup()).toDTO();
        memberService.referenceWordGroup(stubMember.getId(), 1, wordGroup.getId());
        commitAndRestartTransaction();
        assertEquals(getWordGroupsFromEM(1, wordGroup.getId()), wordGroup);
    }

    @Test
    void testRemoveWordGroupReference() {
        memberService.removeWordGroupReference(stubMember.getId(), 1, 1);
        commitAndRestartTransaction();
        assertNull(getWordGroupsFromEM(1, 1));
    }

    @Test
    void testFavoriteDictionary() {
        DictionaryDTO dictionary = em.persistAndFlush(new Dictionary("title", "description", Dictionary.Type.PUBLIC)).toDTO();
        memberService.favoriteDictionary(stubMember.getId(), dictionary.getId());
        commitAndRestartTransaction();
        assertEquals(getFavDictionaryFromEM(stubMember.getId(), dictionary.getId()),
                dictionary);
    }

    @Test
    void testRemoveFavoriteDictionary() {
        memberService.removeFavoriteDictionary(1, 1);
        commitAndRestartTransaction();
        assertNull(getFavDictionaryFromEM(stubMember.getId(), 1));
    }

    @Test
    void testGetOwnDictionaries() {
        List<DictionaryDTO> dictionaries = memberService.getOwnDictionaries(stubMember.getId());
        assertEquals(1, dictionaries.size());
        assertEquals(1, dictionaries.iterator().next().getId());
    }

    @Test
    void testDeleteOwnDictionary() {
        memberService.deleteOwnDictionary(stubMember.getId(),  1);
        commitAndRestartTransaction();
        assertNull(getOwnDictionaryFromEM(stubMember.getId(), 1));
    }

    private DictionaryDTO getFavDictionaryFromEM(int memberId, int dictionaryId) {
        List<Dictionary> result = em.getEntityManager().createQuery("SELECT d FROM Member m JOIN m.favoriteDictionaries d " +
                "WHERE m.id = ?1 and d.id = ?2", Dictionary.class)
                .setParameter(1, memberId)
                .setParameter(2, dictionaryId)
                .getResultList();
        return result.isEmpty() ? null : result.get(0).toDTO();
    }

    private DictionaryDTO getOwnDictionaryFromEM(int memberId, int dictionaryId) {
        List<Dictionary> result = em.getEntityManager().createQuery("SELECT d FROM Member m JOIN m.ownDictionaries d " +
                "WHERE m.id = ?1 and d.id = ?2", Dictionary.class)
                .setParameter(1, memberId)
                .setParameter(2, dictionaryId)
                .getResultList();
        return result.isEmpty() ? null : result.get(0).toDTO();
    }


    private WordGroupDTO getWordGroupsFromEM(int dictionaryId, int wordGroupId) {
        List<WordGroup> result = em.getEntityManager().createQuery(
                "SELECT wg FROM Dictionary d JOIN d.wordGroups wg " +
                "WHERE d.id = ?1 and wg.id = ?2", WordGroup.class)
                .setParameter(1, dictionaryId)
                .setParameter(2, wordGroupId)
                .getResultList();
        return result.isEmpty() ? null : result.get(0).toDTO();
    }
}