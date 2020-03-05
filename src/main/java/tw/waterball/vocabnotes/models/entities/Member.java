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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import tw.waterball.vocabnotes.models.Level;
import tw.waterball.vocabnotes.services.dto.MemberDTO;
import tw.waterball.vocabnotes.services.dto.MemberWithDictionariesDTO;

import javax.persistence.Entity;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonProperty.*;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@NoArgsConstructor
@Getter @Setter @ToString

@Entity
public class Member implements IdEntity {
    private Integer id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String password;
    private int exp = 0;
    private int level = 1;
    private Role role;
    private transient List<Dictionary> ownDictionaries = new ArrayList<>();
    private transient Set<Dictionary> favoriteDictionaries = new HashSet<>();

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

    public Member(String firstName, String lastName, int age, String email, String password, Role role) {
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

    public void addFavoriteDictionary(Dictionary dictionary) {
        favoriteDictionaries.add(dictionary);
    }

    public void removeFavoriteDictionary(Dictionary dictionary) {
        favoriteDictionaries.remove(dictionary);
    }

    public void addOwnDictionary(Dictionary dictionary) {
        ownDictionaries.add(dictionary);
        dictionary.setOwner(this);
    }

    public void removeOwnDictionary(Dictionary dictionary) {
        ownDictionaries.remove(dictionary);
        dictionary.setOwner(null);
    }

}
