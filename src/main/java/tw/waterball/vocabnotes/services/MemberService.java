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

import tw.waterball.vocabnotes.api.Requests;
import tw.waterball.vocabnotes.api.Responses;
import tw.waterball.vocabnotes.models.dto.Credentials;
import tw.waterball.vocabnotes.models.dto.DictionaryDTO;
import tw.waterball.vocabnotes.models.dto.MemberInfo;
import tw.waterball.vocabnotes.models.entities.Member;
import tw.waterball.vocabnotes.services.exceptions.DuplicateEmailException;
import tw.waterball.vocabnotes.services.exceptions.EmailNotFoundException;
import tw.waterball.vocabnotes.services.exceptions.PasswordNotCorrectException;

import java.util.List;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
public interface MemberService {

    Responses.TokenResponse login(Credentials credentials) throws EmailNotFoundException, PasswordNotCorrectException;

    Responses.TokenResponse renewToken(String token);

    Member getMember(int memberId);

    MemberInfo createMember(Requests.RegisterMember request) throws DuplicateEmailException;

    void updateMember(int memberId, Requests.UpdateMember request);

    void favoriteDictionary(int memberId, int dictionaryId);

    void deleteOwnDictionary(int memberId, int dictionaryId);

    List<DictionaryDTO> getOwnDictionaries(int memberId, Integer offset, Integer limit);

    DictionaryDTO createOwnDictionary(int ownerId, Requests.CreateDictionary request);

    void referenceWordGroup(int memberId, int dictionaryId, int wordGroupId);

    void removeWordGroupReference(int memberId, int dictionaryId, int wordGroupId);
}
