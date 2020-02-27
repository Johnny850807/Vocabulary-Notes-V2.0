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
import tw.waterball.vocabnotes.models.dto.Credentials;
import tw.waterball.vocabnotes.models.dto.DictionaryDTO;
import tw.waterball.vocabnotes.models.dto.MemberDTO;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Member;
import tw.waterball.vocabnotes.models.entities.WordGroup;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                new Credentials("email@email.com", "password"))).toDTO();

        commitAndRestartTransaction();

        MemberDTO memberGot = memberService.getMember(memberDTO.getId()).toDTO();
        assertEquals(memberDTO, memberGot);
    }

    @Test
    void testLoginMember() {
        MemberDTO memberLogin = memberService.login(stubMember.getEmail(), stubPassword).toDTO();
        assertEquals(stubMember, memberLogin);
    }

    @Test
    void testUpdateMember() {
        memberService.updateMember(stubMember.getId(),
                new Requests.UpdateMember("update", "update", 15));

        commitAndRestartTransaction();

        MemberDTO updatedMember = memberService.getMember(stubMember.getId()).toDTO();
        assertEquals(stubMember.getId(), updatedMember.getId());
        assertEquals("update", updatedMember.getFirstName());
        assertEquals("update", updatedMember.getLastName());
        assertEquals(15, updatedMember.getAge());
    }

    @Test
    void createOwnDictionary() {
        DictionaryDTO dictionaryDTO = memberService.createOwnDictionary(stubMember.getId(),
                new Requests.CreateDictionary("title", "description")).toDTO();

        commitAndRestartTransaction();

        Member member = memberService.getMember(stubMember.getId());
        List<Dictionary> dictionaries = member.getOwnDictionaries();
        assertEquals(2, dictionaries.size());

        assertTrue(dictionaries.stream().anyMatch(d -> d.getId().equals(dictionaryDTO.getId())));
    }

    @Test
    void referenceWordGroup() {
        int wordGroupId = (int) em.persistAndGetId(new WordGroup());
        memberService.referenceWordGroup(stubMember.getId(), 1, wordGroupId);
        commitAndRestartTransaction();

        List<WordGroup> wordGroups = getWordGroupsByDictionaryId(1);
        assertEquals(2, wordGroups.size());
        assertTrue(wordGroups.stream().anyMatch(wg -> wg.getId() == wordGroupId));
    }

    @Test
    void removeWordGroupReference() {
        memberService.removeWordGroupReference(stubMember.getId(), 1, 1);

        List<WordGroup> wordGroups = getWordGroupsByDictionaryId(1);
        assertTrue(wordGroups.isEmpty());
    }

    @Test
    void favoriteDictionary() {
        int dictionaryId = (int) em.persistAndGetId(new Dictionary("title", "description", Dictionary.Type.PUBLIC));
        memberService.favoriteDictionary(1, dictionaryId);
        commitAndRestartTransaction();

        Member member = memberService.getMember(stubMember.getId());
        Set<Dictionary> fav = member.getFavoriteDictionaries();
        assertEquals(2, fav.size());
        assertTrue(fav.stream().anyMatch(d -> d.getId() == dictionaryId));
    }
    @Test
    void removeFavoriteDictionary() {
        memberService.removeFavoriteDictionary(1, 1);
        commitAndRestartTransaction();

        Member member = memberService.getMember(stubMember.getId());
        Set<Dictionary> fav = member.getFavoriteDictionaries();
        assertTrue(fav.isEmpty());
    }

    @Test
    void getOwnDictionaries() {
        List<DictionaryDTO> dictionaries = memberService.getOwnDictionaries(stubMember.getId());
        assertEquals(1, dictionaries.size());
        assertEquals(1, dictionaries.iterator().next().getId());
    }

    @Test
    void deleteOwnDictionary() {
        memberService.deleteOwnDictionary(stubMember.getId(),  1);
        commitAndRestartTransaction();

        Member member = memberService.getMember(stubMember.getId());
        assertTrue(member.getOwnDictionaries().isEmpty());
    }

    private List<WordGroup> getWordGroupsByDictionaryId(int dictionaryId) {
        return em.getEntityManager().createQuery(
                "SELECT wg FROM Dictionary d JOIN d.wordGroups wg " +
                        "WHERE d.id = ?1", WordGroup.class)
                .setParameter(1, dictionaryId).getResultList();
    }
}