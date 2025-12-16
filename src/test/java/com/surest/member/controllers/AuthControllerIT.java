package com.surest.member.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surest.member.dto.LoginRequest;
import com.surest.member.dto.RegisterRequest;
import com.surest.member.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUtil testUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterUser() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("newUser");
        req.setPassword("secret");
        req.setRole("USER");

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginUser() throws Exception {
        // Create test user in H2 DB
        testUtil.createTestUser("loginUser", "USER");

        LoginRequest login = new LoginRequest("loginUser", "password");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
    @Test
    void shouldReturn403WhenTokenMissing() throws Exception {
        mockMvc.perform(get("/api/v1/members"))
                .andExpect(status().isForbidden()); // ✅
    }


    @Test
    void shouldFailForInvalidLogin() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "username": "wrong",
                  "password": "wrong"
                }
            """))
                .andExpect(status().is4xxClientError()); // ✅
    }


}
