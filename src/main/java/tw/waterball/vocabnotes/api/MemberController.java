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

package tw.waterball.vocabnotes.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.waterball.vocabnotes.models.dto.Credentials;
import tw.waterball.vocabnotes.models.dto.DictionaryDTO;
import tw.waterball.vocabnotes.models.dto.MemberInfo;
import tw.waterball.vocabnotes.models.entities.Member;
import tw.waterball.vocabnotes.models.entities.WordGroup;
import tw.waterball.vocabnotes.services.DictionaryService;
import tw.waterball.vocabnotes.services.MemberService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@RequestMapping("/api/members")
@RestController
public class MemberController {
    private MemberService memberService;
    private DictionaryService dictionaryService;

    @Autowired
    public MemberController(MemberService memberService, DictionaryService dictionaryService) {
        this.memberService = memberService;
        this.dictionaryService = dictionaryService;
    }

    @PostMapping("/tokens")
    public Responses.TokenResponse createToken(@RequestBody @Valid Credentials credentials) {
        return memberService.createToken(credentials);
    }

    @PostMapping("/{memberId}/tokens")
    public Responses.TokenResponse renewToken(@PathVariable int memberId) {
        return memberService.renewToken(memberId);
    }

    @PostMapping
    public MemberInfo createMember(@RequestBody @Valid Requests.RegisterMember request) {
        return memberService.createMember(request.getCredentials(), request.getMember());
    }

    @GetMapping("/{memberId}")
    public Member getMember(@PathVariable int memberId) {
        return memberService.getMember(memberId);
    }

    @PutMapping("/{memberId}")
    public void updateMember(@PathVariable int memberId,
                             @RequestBody @Valid Requests.UpdateMember request) {
        memberService.updateMember(memberId, request);
    }


    @GetMapping("/{memberId}/own/dictionaries")
    public List<DictionaryDTO> getOwnDictionaries(@PathVariable int memberId,
                                                  @RequestParam(required = false) Integer offset,
                                                  @RequestParam(required = false) Integer limit) {
        return dictionaryService.getOwnDictionaries(memberId, offset, limit);
    }

    @PostMapping("/{memberId}/own/dictionaries")
    public DictionaryDTO createOwnDictionary(@PathVariable int memberId, @RequestBody Requests.CreateDictionary request) {
        return dictionaryService.createOwnDictionary(memberId, request);
    }

    /**
     * This api is equivalent to /api/dictionaries/{dictionaryId}
     */
    @GetMapping("/{memberId}/own/dictionaries/{dictionaryId}")
    public DictionaryDTO getOwnDictionary(@PathVariable int memberId,
                                                  @PathVariable int dictionaryId) {
        return dictionaryService.getDictionary(dictionaryId);
    }


    @DeleteMapping("/{memberId}/own/dictionaries/{dictionaryId}")
    public void deleteOwnDictionary(@PathVariable int memberId,
                                                     @PathVariable int dictionaryId) {
        dictionaryService.deleteOwnDictionary(memberId, dictionaryId);
    }

    @PostMapping("/{memberId}/public/dictionaries/{dictionaryId}/favorite")
    public void favoritePublicDictionary(@PathVariable int memberId,
                                         @PathVariable int dictionaryId) {

    }

    @PostMapping("/{memberId}/public/wordgroups/{wgId}")
    public void referenceWordGroup(@PathVariable int memberId,
                                   @PathVariable int wgId) {

    }

    @DeleteMapping("/{memberId}/public/wordgroups/{wgId}")
    public void removeWordGroupReference(@PathVariable int memberId,
                                         @PathVariable int wgId) {

    }

    @GetMapping("/{memberId}/own/dictionaries/wordgroups")
    public List<WordGroup> getWordGroupsFromOwnDictionary(@PathVariable int memberId,
                                                          @RequestParam(required = false) Integer limit,
                                                          @RequestParam(required = false) Integer offset) {
        return null;
    }

}
