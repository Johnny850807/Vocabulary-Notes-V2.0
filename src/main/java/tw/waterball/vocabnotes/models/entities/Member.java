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

package tw.waterball.vocabnotes.models.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@NoArgsConstructor
@Getter @Setter
@ToString

@Entity
public class Member implements IdEntity {
    private Integer id;

    @Size(min = 1, max=18)
    private String firstName;

    @Size(min = 1, max=18)
    private String lastName;

    @Min(1) @Max(150)
    private int age;

    @Email
    @Size(min = 1, max=50)
    private String email;

    @NotNull @NotBlank
    private String password;

    private int exp = 0;
    private int level = 1;

    @NotNull
    private Role role;

    @ToString.Exclude
    private transient List<Dictionary> ownDictionaries = new ArrayList<>();

    public enum Role {
        MEMBER, ADMIN
    }

    public Member(int id, String firstName,
                  String lastName,
                  int age, String email,
                  String password, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public void setExp(int exp) {
        this.exp = exp;
        this.level = Level.getLevelFromExp(exp).getNumber();
    }

    public void addDictionary(Dictionary dictionary) {
        ownDictionaries.add(dictionary);
        dictionary.setOwner(this);
    }

    public void removeDictionary(Dictionary dictionary) {
        ownDictionaries.remove(dictionary);
        dictionary.setOwner(null);
    }

}
