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


import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import tw.waterball.vocabnotes.models.entities.Member;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Component
public class StandardJwtService implements JwtService {
    private final static Date DEFAULT_EXPIRATION = new Date(TimeUnit.HOURS.toMillis(1));
    private final static String secret;
    private static final SecretKey key;

    static{
        ResourceBundle properties = ResourceBundle.getBundle("secret", Locale.getDefault());
        secret = properties.getString("jwt.secret");
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public Date getDefaultExpirationTime() {
        return DEFAULT_EXPIRATION;
    }

    @Override
    public String getJwt(Claim claim) {
        return Jwts.builder()
                .setExpiration(DEFAULT_EXPIRATION)
                .setClaims(claim.getClaimMap())
                .signWith(key).compact();
    }

    // TODO
    @Override
    public JwtToken parse(String token) {
        Jwt jwt =  Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parse(token);
        return null;
    }

    public static void main(String[] args) {
        JwtService service = new StandardJwtService();
        String token = service.getJwt(
                new Claim(Member.Role.MEMBER, 5));

        System.out.println(token);
    }
}
