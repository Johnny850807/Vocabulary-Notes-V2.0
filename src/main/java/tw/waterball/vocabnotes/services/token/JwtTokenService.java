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

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import tw.waterball.vocabnotes.models.entities.Member;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * @author johnny850807@gmail.com (Waterball))
 */
@Service
public class JwtTokenService implements TokenService {
    private final static Date DEFAULT_EXPIRATION = new Date(TimeUnit.HOURS.toMillis(1));
    private final static String secret;
    private static final SecretKey key;

    static {
        ResourceBundle properties = ResourceBundle.getBundle("secret", Locale.getDefault());
        secret = properties.getString("jwt.secret");
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public Date getDefaultExpirationTime() {
        return DEFAULT_EXPIRATION;
    }

    @Override
    public JwtToken createToken(Params params) {
        Member.Role role = Member.Role.valueOf(params.getString(TokenClaim.ROLE));
        final int memberId = params.getInteger(TokenClaim.MEMBER_ID);
        final Date expirationDate = new Date(System.currentTimeMillis() + DEFAULT_EXPIRATION.getTime());
        return new JwtTokenImpl(expirationDate, new TokenClaim(role, memberId));
    }

    @Override
    public JwtToken renewToken(String token) {
        JwtToken jwt = parse(token);
        return createToken(jwt.getClaim());
    }

    // TODO
    private JwtToken parse(String token) {
        Jwt jwt = Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parse(token);
        return null;
    }


    private static class JwtTokenImpl implements JwtToken {
        private final Date expirationDate;
        private TokenClaim tokenClaim;

        public JwtTokenImpl(Date expirationDate, TokenClaim tokenClaim) {
            this.expirationDate = expirationDate;
            this.tokenClaim = tokenClaim;
        }

        @Override
        public TokenClaim getClaim() {
            return tokenClaim;
        }

        @Override
        public Date getExpirationTime() {
            return expirationDate;
        }

        @Override
        public int getMemberId() {
            return tokenClaim.getMemberId();
        }

        @Override
        public String toString() {
            return Jwts.builder()
                    .setExpiration(getExpirationTime())
                    .setClaims(getClaim().getClaimMap())
                    .signWith(key).compact();
        }
    }
}
