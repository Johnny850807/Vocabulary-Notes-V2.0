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

package tw.waterball.vocabnotes.services.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.waterball.vocabnotes.models.entities.Member;

import java.util.HashMap;
import java.util.Map;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
public class TokenClaim implements TokenService.Params {
    public final static String MEMBER_ID = "memberId";
    public final static String ROLE = "role";
    private Member.Role role;
    private int memberId;

    public Map<String, String> getClaimMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put(TokenClaim.MEMBER_ID, String.valueOf(memberId));
        map.put(TokenClaim.ROLE, role.toString());
        return map;
    }

    @Override
    public String getString(String key) {
        switch (key) {
            case MEMBER_ID:
                return String.valueOf(memberId);
            case ROLE:
                return role.toString();
            default:
                throw new IllegalArgumentException("The key " + key + " not found.");
        }
    }

    @Override
    public int getInteger(String key) {
        switch (key) {
            case MEMBER_ID:
                return memberId;
            case ROLE:
                throw new IllegalArgumentException("The val of key " + key + " cannot be casted into Integer.");
            default:
                throw new IllegalArgumentException("The key " + key + " not found.");
        }
    }
}
