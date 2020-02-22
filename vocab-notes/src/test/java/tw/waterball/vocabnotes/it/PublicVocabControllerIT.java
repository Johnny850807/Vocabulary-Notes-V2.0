package tw.waterball.vocabnotes.it;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import tw.waterball.vocabnotes.VocabNotesApplication;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = VocabNotesApplication.class)
public class PublicVocabControllerIT {

    @Test
    public void testAllEndpoints() {
        RestTemplate restTemplate = new RestTemplate();

        MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();

        mockServer.expect(requestTo(resourceUrl("/dictionaries/1")))
                .andRespond(withSuccess());

        mockServer.verify();
    }


    private static String resourceUrl(String resourceUrlFormat, Object ...params) {
        return "/app/public" + String.format(resourceUrlFormat, params);
    }

}
