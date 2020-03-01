package tw.waterball.vocabnotes.services.token;

import org.junit.jupiter.api.Test;
import tw.waterball.vocabnotes.models.entities.Member;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenServiceTest {
    private final static String SECRET = "1234567890abcdefghijklmn";
    private JwtTokenService jwtTokenService = new JwtTokenService(SECRET);

    @Test
    public void testCreateToken() {
        JwtToken token = jwtTokenService.createToken(new TokenClaim(Member.Role.MEMBER, 1));
        assertEquals(1, token.getClaim().getMemberId());
        assertEquals(Member.Role.MEMBER, token.getClaim().getRole());

        JwtToken parsed = jwtTokenService.parse(token.toString());
        assertEquals(token, parsed);
    }
}