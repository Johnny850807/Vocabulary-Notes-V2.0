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

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.waterball.vocabnotes.api.Requests;
import tw.waterball.vocabnotes.api.Responses;
import tw.waterball.vocabnotes.models.dto.Credentials;
import tw.waterball.vocabnotes.models.dto.DictionaryDTO;
import tw.waterball.vocabnotes.models.dto.MemberInfo;
import tw.waterball.vocabnotes.models.entities.Dictionary;
import tw.waterball.vocabnotes.models.entities.Member;
import tw.waterball.vocabnotes.models.repositories.DictionaryRepository;
import tw.waterball.vocabnotes.models.repositories.MemberRepository;
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
    private JwtService jwtService;
    private MemberRepository memberRepository;
    private DictionaryRepository dictionaryRepository;

    @Autowired
    public StandardMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberInfo createMember(Requests.RegisterMember request) throws DuplicateEmailException {
        if (memberRepository.existsByEmail(request.getCredentials().getEmail())) {
            throw new DuplicateEmailException(request.getCredentials().getEmail());
        }
        return memberRepository.save(request.toMember());
    }

    @Override
    public Responses.TokenResponse login(Credentials credentials) throws EmailNotFoundException, PasswordNotCorrectException {
        Member member = memberRepository.findByEmail(credentials.getEmail())
                .orElseThrow(() -> new EmailNotFoundException(credentials.getEmail()));

        if (!member.getPassword().equals(credentials.getPassword())) {
            throw new PasswordNotCorrectException(credentials.getPassword());
        }

        String token = jwtService.getJwt(new JwtService.Claim(member.getRole(), member.getId()));
        return new Responses.TokenResponse(token, jwtService.getDefaultExpirationTime().getTime(), member.getId());
    }

    @Override
    public Responses.TokenResponse renewToken(String token) {
        JwtService.JwtToken jwt = jwtService.parse(token);
        int memberId = jwt.getClaim().getMemberId();
        String renewedToken = jwtService.getJwt(new JwtService.Claim(jwt.getClaim().getRole(), memberId));
        return new Responses.TokenResponse(renewedToken, jwtService.getDefaultExpirationTime().getTime(), memberId);
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
    public DictionaryDTO createOwnDictionary(int ownerId, Requests.CreateDictionary request) {
        Member owner = getMember(ownerId);
        Dictionary dict = dictionaryRepository.save(Dictionary.builder()
                .owner(owner)
                .title(request.getTitle())
                .description(request.getDescription())
                .type(Dictionary.Type.OWN).build());
        return DictionaryDTO.project(dict);
    }

    @Override
    public void referenceWordGroup(int memberId, int dictionaryId, int wordGroupId) {

    }

    @Override
    public void removeWordGroupReference(int memberId, int dictionaryId, int wordGroupId) {

    }

    @Override
    public void favoriteDictionary(int memberId, int dictionaryId) {
        Member member = findMemberByIdOrThrowNotFound(memberId);
        Dictionary dictionary = findDictionaryByIdOrThrowNotFound(dictionaryId);
        // TODO favorite
    }

    private Dictionary findDictionaryByIdOrThrowNotFound(int dictionaryId) {
        return dictionaryRepository.findById(dictionaryId)
                .orElseThrow(() -> new ResourceNotFoundException("dictionary", dictionaryId));
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

    private Member findMemberByIdOrThrowNotFound(int memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("memberCreationInfo", memberId));
    }
}
