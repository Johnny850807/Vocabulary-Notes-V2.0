package tw.waterball.vocabnotes.it;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.*;
import com.fasterxml.jackson.core.type.TypeReference;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestBody;
import tw.waterball.vocabnotes.VocabNotesApplication;
import tw.waterball.vocabnotes.api.ApiPrefix;
import tw.waterball.vocabnotes.api.MemberController;
import tw.waterball.vocabnotes.api.Requests;
import tw.waterball.vocabnotes.api.Requests.CreateMember;
import tw.waterball.vocabnotes.api.Requests.RegisterMember;
import tw.waterball.vocabnotes.models.dto.Credentials;
import tw.waterball.vocabnotes.models.entities.Member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tw.waterball.vocabnotes.api.ApiPrefix.withApiPrefix;

/**
 * @author johnny850807 (johnny850807@gmail.com)
 */
public class MemberControllerIT extends BaseMvcTest {
    @Autowired
    private MemberController memberController;

    @Override
    protected Object[] controllers() {
        return new Object[]{memberController};
    }

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(jsonRequest(
                post(apiPrefix("/tokens")),
                new Credentials("johnny850807@gmail.com", "12345678")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("token").isString())
                .andExpect(jsonPath("expired").isNumber())
                .andExpect(jsonPath("memberId").value(1));
    }

    @Test
    void testCreateMember() throws Exception {
        mockMvc.perform(jsonRequest(
                post(apiPrefix()),
                new RegisterMember(
                        new CreateMember("Peter", "Pan", 20),
                        new Credentials("apple@example.com", "12345678"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("token").isString())
                .andExpect(jsonPath("expired").isNumber())
                .andExpect(jsonPath("memberId").isNumber());
    }

    @Test
    void testGetMember() throws Exception {
        mockMvc.perform(get(apiPrefix("/{id}"), 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("firstName").value("Johnny"))
                .andExpect(jsonPath("lastName").value("Pan"))
                .andExpect(jsonPath("email").value("johnny850807@gmail.com"))
                .andExpect(jsonPath("password").doesNotExist())
                .andExpect(jsonPath("role").value("MEMBER"));
    }

    @Test
    void testUpdateMember() throws Exception {
        mockMvc.perform(jsonRequest(put(apiPrefix("/{id}"), 1),
                new Requests.UpdateMember("updated", "updated", 5)))
                .andExpect(status().isOk());

        Member member = em.find(Member.class, 1);
        assertEquals("updated", member.getFirstName());
        assertEquals("updated", member.getLastName());
        assertEquals(5, member.getAge());
    }

    @Test
    void testGetOwnDictionaries() throws Exception {
        mockMvc.perform(get(apiPrefix("/{memberId}/own/dictionaries"), 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());

        //TODO how to deserialize json into object then test
    }

    private String apiPrefix() {
        return apiPrefix("");
    }

    private String apiPrefix(String url) {
        return withApiPrefix("/members" + url);
    }
}
