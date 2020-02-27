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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.waterball.vocabnotes.api.Requests;
import tw.waterball.vocabnotes.models.dto.DictionaryDTO;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Member;
import tw.waterball.vocabnotes.models.entities.WordGroup;
import tw.waterball.vocabnotes.models.repositories.DictionaryRepository;
import tw.waterball.vocabnotes.models.repositories.MemberRepository;
import tw.waterball.vocabnotes.models.repositories.WordGroupRepository;
import tw.waterball.vocabnotes.services.exceptions.DuplicateEmailException;
import tw.waterball.vocabnotes.services.exceptions.EmailNotFoundException;
import tw.waterball.vocabnotes.services.exceptions.PasswordNotCorrectException;
import tw.waterball.vocabnotes.services.exceptions.ResourceNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Service
public class StandardMemberService implements MemberService {
    private MemberRepository memberRepository;
    private DictionaryRepository dictionaryRepository;
    private WordGroupRepository wordGroupRepository;

    @Autowired
    public StandardMemberService(MemberRepository memberRepository,
                                 DictionaryRepository dictionaryRepository,
                                 WordGroupRepository wordGroupRepository) {
        this.memberRepository = memberRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.wordGroupRepository = wordGroupRepository;
    }

    @Override
    public Member createMember(Requests.RegisterMember request) throws DuplicateEmailException {
        if (memberRepository.existsByEmail(request.getCredentials().getEmail())) {
            throw new DuplicateEmailException(request.getCredentials().getEmail());
        }
        return memberRepository.save(request.toMember());
    }

    @Override
    public Member login(String email, String password) throws EmailNotFoundException, PasswordNotCorrectException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));

        if (!member.getPassword().equals(password)) {
            throw new PasswordNotCorrectException(password);
        }

        return member;
    }


    @Override
    public Member getMember(int memberId) {
        return findMemberByIdOrThrowNotFound(memberId);
    }

    @Override
    public void updateMember(int memberId, Requests.UpdateMember request) {
        try {
            Member member = memberRepository.getOne(memberId);
            member.setAge(request.getAge());
            member.setFirstName(request.getFirstName());
            member.setLastName(request.getLastName());
            memberRepository.save(member);
        } catch (EntityNotFoundException err) {
            throw new ResourceNotFoundException("member", memberId);
        }
    }

    @Override
    public Dictionary createOwnDictionary(int ownerId, Requests.CreateDictionary request) {
        Member owner = findMemberByIdOrThrowNotFound(ownerId);
        Dictionary ownDict = Dictionary.builder()
                                .title(request.getTitle())
                                .description(request.getDescription())
                                .type(Dictionary.Type.OWN).build();
        owner.addOwnDictionary(ownDict);
        ownDict = dictionaryRepository.save(ownDict);
        return ownDict;
    }

    @Override
    public void referenceWordGroup(int memberId, int dictionaryId, int wordGroupId) {
        validateMemberOwnDictionary(memberId, dictionaryId);
        Dictionary dictionary = findDictionaryByIdOrThrowNotFound(dictionaryId);
        dictionary.addWordGroup(findWordGroupByIdOrThrowNotFound(wordGroupId));
    }

    @Override
    public void removeWordGroupReference(int memberId, int dictionaryId, int wordGroupId) {
        validateMemberOwnDictionary(memberId, dictionaryId);
        Dictionary dictionary = findDictionaryByIdOrThrowNotFound(dictionaryId);
        dictionary.removeWordGroup(findWordGroupByIdOrThrowNotFound(wordGroupId));
    }

    @Override
    public void favoriteDictionary(int memberId, int dictionaryId) {
        Member member = findMemberByIdOrThrowNotFound(memberId);
        Dictionary dictionary = findDictionaryByIdOrThrowNotFound(dictionaryId);
        member.addFavoriteDictionary(dictionary);
        memberRepository.save(member);
    }

    @Override
    public void removeFavoriteDictionary(int memberId, int dictionaryId) {
        Member member = findMemberByIdOrThrowNotFound(memberId);
        Dictionary dictionary = findDictionaryByIdOrThrowNotFound(dictionaryId);
        member.removeFavoriteDictionary(dictionary);
        memberRepository.save(member);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public List<DictionaryDTO> getOwnDictionaries(int memberId, Integer offset, Integer limit) {
        offset = offset == null ? 0 : offset;
        limit = limit == null ? Integer.MAX_VALUE : limit;
        if (0 == limit) {
            return Collections.emptyList();
        }
        return dictionaryRepository.findOwnDictionaries(memberId, offset, limit);
    }

    @Override
    public void deleteOwnDictionary(int memberId, int dictionaryId) {
        validateMemberOwnDictionary(memberId, dictionaryId);
        dictionaryRepository.deleteById(dictionaryId);
    }

    private void validateMemberOwnDictionary(int memberId, int dictionaryId) {
        if (!dictionaryRepository.existsByOwnerId(memberId)) {
            throw new DictionaryNotOwnException(memberId, dictionaryId);
        }
    }

    private Dictionary findDictionaryByIdOrThrowNotFound(int dictionaryId) {
        return dictionaryRepository.findById(dictionaryId)
                .orElseThrow(() -> new ResourceNotFoundException("dictionary", dictionaryId));
    }

    private WordGroup findWordGroupByIdOrThrowNotFound(int wordGroupId) {
        return wordGroupRepository.findById(wordGroupId)
                .orElseThrow(() -> new ResourceNotFoundException("wordGroup", wordGroupId));
    }

    private Member findMemberByIdOrThrowNotFound(int memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("memberCreationInfo", memberId));
    }
}
