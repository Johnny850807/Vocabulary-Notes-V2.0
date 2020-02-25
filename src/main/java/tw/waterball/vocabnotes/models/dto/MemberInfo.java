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

/**
 * @author johnny850807@gmail.com (Waterball))
 */
public interface MemberInfo {
    String getFirstName();

    String getLastName();

    int getAge();

    String getEmail();

    int getExp();

    int getLevel();

    tw.waterball.vocabnotes.models.entities.Member.Role getRole();

    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setAge(int age);

    void setEmail(String email);

    void setExp(int exp);

    void setLevel(int level);

    void setRole(tw.waterball.vocabnotes.models.entities.Member.Role role);
}
