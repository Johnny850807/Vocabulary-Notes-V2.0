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


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import tw.waterball.vocabnotes.models.entities.Member;

import java.security.Key;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
public class StandardJwtService implements JwtService {

    @Override
    public String getJwt(JwtParams jwtParams) {
        ResourceBundle properties = ResourceBundle.getBundle("secret", Locale.getDefault());
        String secret = properties.getString("jwt.secret");
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setExpiration(jwtParams.getExpiration())
                .setClaims(jwtParams.getClaimMap())
                .signWith(key).compact();
    }

    public static void main(String[] args) {
        JwtService service = new StandardJwtService();
        String token = service.getJwt(
                new JwtParams(new Date(),
                        new JwtParams.Claim(Member.Role.MEMBER, 5)));

        System.out.println(token);
    }
}
