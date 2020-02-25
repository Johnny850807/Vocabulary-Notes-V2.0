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

package tw.waterball.vocabnotes.models.dto;

import lombok.Data;
import tw.waterball.vocabnotes.models.entities.Member;

import javax.validation.constraints.*;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Data
public class MemberInfoDTO implements MemberInfo {
    @Size(min = 1, max=18)
    private String firstName;

    @Size(min = 1, max=18)
    private String lastName;

    @Min(1) @Max(150)
    private int age;

    @Email
    @Size(max=30)
    private String email;

    private int exp = 0;
    private int level = 1;

    @NotNull
    private Member.Role role;

}
