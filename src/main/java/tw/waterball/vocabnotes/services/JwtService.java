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

import lombok.AllArgsConstructor;
import lombok.Data;
import tw.waterball.vocabnotes.models.entities.Member;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
public interface JwtService {
    String getJwt(JwtParams jwtParams);

    @Data @AllArgsConstructor
    class JwtParams {
        private Date expiration;
        private Claim claim;

        @Data @AllArgsConstructor
        public static class Claim {
            private final static String MEMBER_ID = "memberId";
            private final static String ROLE = "role";
            private Member.Role role;
            private int memberId;

            public Map<String, String> getClaimMap() {
                HashMap<String, String> map = new HashMap<>();
                map.put(JwtParams.Claim.MEMBER_ID, String.valueOf(memberId));
                map.put(Claim.ROLE, role.toString());
                return map;
            }
        }

        public Map<String, String> getClaimMap() {
            return claim.getClaimMap();
        }
    }
}
