package tw.waterball.vocabnotes.services.token;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tw.waterball.vocabnotes.VocabNotesApplication;
import tw.waterball.vocabnotes.api.MemberController;
import tw.waterball.vocabnotes.models.entities.Member;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenServiceTest {
    private final static String SECRET = "1234567890abcdefghijklmnopqrstuvwxyz1234567890";
    private JwtTokenService jwtTokenService = new JwtTokenService(SECRET);

    @Test
    public void testCreateToken() {
        JwtToken token = jwtTokenService.createToken(new TokenClaim(Member.Role.MEMBER, 1));
        assertEquals(1, token.getClaim().getMemberId());
        assertEquals(Member.Role.MEMBER, token.getClaim().getRole());

        JwtToken parsed = jwtTokenService.parse(token.toString());
        assertEquals(token.getExpirationTime().getTime() / 1000,
                parsed.getExpirationTime().getTime() / 1000);
        assertEquals(token.getClaim(), parsed.getClaim());
    }
}