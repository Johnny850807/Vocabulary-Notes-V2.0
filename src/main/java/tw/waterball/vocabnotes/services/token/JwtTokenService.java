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

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;
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
@Service
public class JwtTokenService implements TokenService {
    private final static Date DEFAULT_EXPIRATION = new Date(TimeUnit.HOURS.toMillis(1));
    private final static String DEFAULT_SECRET;
    private final SecretKey key;
    private final Date expiration;

    static {
        ResourceBundle properties = ResourceBundle.getBundle("secret", Locale.getDefault());
        DEFAULT_SECRET = properties.getString("jwt.secret");
    }

    public JwtTokenService() {
        this(DEFAULT_SECRET, DEFAULT_EXPIRATION);
    }

    public JwtTokenService(String secret) {
        this(secret, DEFAULT_EXPIRATION);
    }

    public JwtTokenService(String secret, Date expiration) {
        key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
    }

    @Override
    public Date getDefaultExpirationTime() {
        return DEFAULT_EXPIRATION;
    }

    @Override
    public JwtToken createToken(Params params) {
        Member.Role role = Member.Role.valueOf(params.getString(TokenClaim.ROLE));
        final int memberId = params.getInteger(TokenClaim.MEMBER_ID);
        final Date expirationDate = new Date((System.currentTimeMillis() + DEFAULT_EXPIRATION.getTime()));
        return new JwtTokenImpl(expirationDate, new TokenClaim(role, memberId));
    }

    @Override
    public JwtToken renewToken(String token) {
        JwtToken jwt = parse(token);
        return createToken(jwt.getClaim());
    }

    @Override
    public JwtToken parse(String token) throws JwtException {
        Jwt jwt = Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(token);

        Claims claims = (Claims) jwt.getBody();

        int memberId = Integer.parseInt((String) claims.get(TokenClaim.MEMBER_ID));
        Member.Role role = Member.Role.valueOf(String.valueOf(claims.get(TokenClaim.ROLE)));
        return new JwtTokenImpl(claims.getExpiration(),
                new TokenClaim(role, memberId));
    }

    private String compact(Date expirationDate, TokenClaim tokenClaim) {
        return Jwts.builder()
                .setClaims(tokenClaim.getClaimMap())
                .setExpiration(expirationDate)
                .signWith(key).compact();
    }

    @EqualsAndHashCode
    private class JwtTokenImpl implements JwtToken {
        private final Date expirationDate;
        private TokenClaim tokenClaim;
        private String compact;

        public JwtTokenImpl(Date expirationDate, TokenClaim tokenClaim) {
            this.expirationDate = expirationDate;
            this.tokenClaim = tokenClaim;
            this.compact = compact(expirationDate, tokenClaim);
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
            return compact;
        }
    }
}
