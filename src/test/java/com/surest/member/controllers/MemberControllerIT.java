package com.surest.member.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surest.member.dto.MemberDTO;
import com.surest.member.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemberControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestUtil testUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String adminToken;
    private String userToken;

    @BeforeEach
    void setup() {
        adminToken = "Bearer " + testUtil.createTestUser("admin1", "ADMIN");
        userToken  = "Bearer " + testUtil.createTestUser("user1", "USER");
    }

    @Test
    public void shouldReturn403WhenNoTokenProvided() throws Exception {
        mockMvc.perform(get("/api/v1/members"))
                .andExpect(status().isForbidden()); // ✅
    }


    @Test
    public void userCannotViewMembers() throws Exception {
        mockMvc.perform(get("/api/v1/members")
                        .header("Authorization", userToken))
                .andExpect(status().isForbidden()); // ✅
    }


    @Test
    void userCannotCreateMember() throws Exception {
        MemberDTO dto = new MemberDTO(
                null, "John", "Doe",
                LocalDate.of(1990, 1, 1),
                "jd@example.com",
                null, null
        );

        mockMvc.perform(post("/api/v1/members")
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanCreateMember() throws Exception {
        MemberDTO dto = new MemberDTO(
                null, "John", "Doe",
                LocalDate.of(1990, 1, 1),
                "jd2@example.com",
                null, null
        );

        mockMvc.perform(post("/api/v1/members")
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    // ⭐ STEP 3: Pagination Integration Test (ASSIGNMENT GOLD)
    @Test
    void shouldReturnPagedMembers() throws Exception {
        mockMvc.perform(get("/api/v1/members?page=0&size=5")
                        .header("Authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.size").value(5));
    }
}
