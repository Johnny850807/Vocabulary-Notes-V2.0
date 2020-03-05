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

package tw.waterball.vocabnotes.services.dto;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import tw.waterball.vocabnotes.models.Credentials;
import tw.waterball.vocabnotes.models.entities.Member;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
public final class Requests {
    private Requests() { }

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class DictionaryInfo {
        @Size(min = 1, max = 80)
        protected String title;
        @Size(min = 1, max = 300)
        protected String description;
    }


    @Data @AllArgsConstructor @NoArgsConstructor
    public static class RegisterMember {
        @Valid
        @NotNull
        private MemberInfo memberInfo;
        @Valid
        @NotNull
        private Credentials credentials;

        public Member toMember() {
            return new Member(memberInfo.getFirstName(),
                    memberInfo.getLastName(), memberInfo.getAge(),
                    credentials.getEmail(), credentials.getPassword(),
                    Member.Role.MEMBER);
        }
    }

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class MemberInfo {
        @Size(min = 1, max = 18)
        private String firstName;

        @Size(min = 1, max = 18)
        private String lastName;

        @Range(min = 1, max = 150)
        private int age;
    }

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class ModifyWordGroup {
        @Size(min = 1, max = 50)
        private String title;
    }
}