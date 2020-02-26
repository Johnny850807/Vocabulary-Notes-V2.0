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
import tw.waterball.vocabnotes.api.Responses;
import tw.waterball.vocabnotes.models.dto.Credentials;
import tw.waterball.vocabnotes.models.dto.DictionaryDTO;
import tw.waterball.vocabnotes.models.dto.MemberInfo;
import tw.waterball.vocabnotes.models.entities.Member;
import tw.waterball.vocabnotes.models.repositories.MemberRepository;

import java.util.List;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Service
public class StandardMemberService implements MemberService {
    private MemberRepository memberRepository;

    @Autowired
    public StandardMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Responses.TokenResponse createToken(Credentials credentials) {
        return null;
    }

    @Override
    public Responses.TokenResponse renewToken(int memberId) {
        return null;
    }

    @Override
    public Member getMember(int memberId) {
        return findMemberOrThrowNotFound(memberId);
    }

    @Override
    public MemberInfo createMember(Credentials credentials, MemberInfo member) {
        return null;
    }

    @Override
    public void updateMember(int memberId, Requests.UpdateMember request) {

    }

    private Member findMemberOrThrowNotFound(int memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("member", memberId));
    }
}
