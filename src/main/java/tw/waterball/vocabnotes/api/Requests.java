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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import tw.waterball.vocabnotes.models.dto.Credentials;
import tw.waterball.vocabnotes.models.entities.Member;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
public final class Requests {
    private Requests(){}

    @Data
    @AllArgsConstructor @NoArgsConstructor
    public static class CreateDictionary {
        private String title;
        private String description;
    }

    @AllArgsConstructor @NoArgsConstructor
    @Setter @Accessors(fluent = true)
    public static class ModifyDictionary {
        @Size(min = 1, max = 80)
        private String title;
        @Size(min = 1, max = 300)
        private String description;

        public Optional<String> getTitle() {
            return Optional.ofNullable(title);
        }
        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }
    }

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class RegisterMember {
        @Valid
        @NotNull
        private CreateMember memberCreationInfo;
        @Valid
        @NotNull
        private Credentials credentials;

        public Member toMember() {
            return new Member(memberCreationInfo.getFirstName(),
                    memberCreationInfo.getLastName(), memberCreationInfo.getAge(),
                    credentials.getEmail(), credentials.getPassword(),
                    Member.Role.MEMBER);
        }
    }

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class CreateMember {
        @Size(min = 1, max=18)
        private String firstName;

        @Size(min = 1, max=18)
        private String lastName;

        @Min(1) @Max(150)
        private int age;
    }

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class UpdateMember {
        @Size(min = 1, max = 18)
        private String firstName;
        @Size(min = 1, max = 18)
        private String lastName;
        @Min(1) @Max(150)
        private int age;
    }

    @Data @AllArgsConstructor @NoArgsConstructor
    public static class ModifyWordGroup {
        @Size(min = 1, max = 50)
        private String title;
    }
}