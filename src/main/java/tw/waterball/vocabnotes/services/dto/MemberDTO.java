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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import tw.waterball.vocabnotes.models.entities.Member;

import javax.validation.constraints.*;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private int age;

    @Email
    @Size(max = 30)
    private String email;

    private int exp = 0;
    private int level = 1;

    @NotNull
    private Member.Role role;

    public static MemberDTO project(Member m) {
        return new MemberDTO(m.getId(), m.getFirstName(), m.getLastName(),
                m.getAge(), m.getEmail(), m.getExp(), m.getLevel(), m.getRole());
    }

}
