package com.london.reboot.controllers;

import com.london.reboot.config.MethodSecurityConfig;
import com.london.reboot.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(AuthController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class, RestTemplate.class})
@DirtiesContext
class AuthControllerTest {

@Autowired
private MockMvc mvc;

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testRoles() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/auth").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("works"));
    }

}