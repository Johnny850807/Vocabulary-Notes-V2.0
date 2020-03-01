package tw.waterball.vocabnotes.it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tw.waterball.vocabnotes.VocabNotesApplication;
import tw.waterball.vocabnotes.api.MemberController;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestEntityManager
@ContextConfiguration(classes = VocabNotesApplication.class)
@Transactional
public abstract class BaseMvcTest {
    @Autowired
    protected TestEntityManager em;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controllers()).build();
    }

    protected abstract Object[] controllers();


    protected MockHttpServletRequestBuilder jsonRequest(MockHttpServletRequestBuilder requestBuilder, Object body) throws JsonProcessingException {
        return requestBuilder.content(toJson(body))
                .contentType(MediaType.APPLICATION_JSON);
    }

    protected String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T fromJson(String json, Class<T> type) throws JsonProcessingException {
        return objectMapper.readValue(json, type);
    }

    protected <T> T fromJson(String json, TypeReference<T> type) throws JsonProcessingException {
        return objectMapper.readValue(json, type);
    }

}
