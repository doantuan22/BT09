package com.example.bai_tap_09_bai_tap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.bai_tap_09_bai_tap.entity.Role;
import com.example.bai_tap_09_bai_tap.entity.User;
import com.example.bai_tap_09_bai_tap.repository.RoleRepository;
import com.example.bai_tap_09_bai_tap.repository.UserRepository;
import com.example.bai_tap_09_bai_tap.repository.ProductRepository;
import com.example.bai_tap_09_bai_tap.service.EmailService;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationSmokeTests {
    @Autowired MockMvc mvc;
    @Autowired UserRepository users;
    @Autowired ProductRepository products;
    @Autowired RoleRepository roles;
    @Autowired PasswordEncoder passwordEncoder;
    @MockitoBean EmailService emailService;

    @Test void publicAuthenticationPagesRender() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk());
        mvc.perform(get("/login")).andExpect(status().isOk());
        mvc.perform(get("/register")).andExpect(status().isOk());
        mvc.perform(get("/verify-otp?email=verify@example.test")).andExpect(status().isOk());
        mvc.perform(get("/forgot-password")).andExpect(status().isOk());
        mvc.perform(get("/reset-password")).andExpect(status().isOk());
    }

    @Test void protectedManagementPagesRequireSession() throws Exception {
        mvc.perform(get("/users")).andExpect(status().is3xxRedirection());
        mvc.perform(get("/products")).andExpect(status().is3xxRedirection());
    }

    @Test void registrationOtpLoginAndPasswordResetWork() throws Exception {
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/register")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("username", "flow-user").param("email", "flow@example.test")
                .param("fullName", "Flow User").param("password", "StartPass123!")
                .param("confirmPassword", "StartPass123!"))
                .andExpect(status().is3xxRedirection());
        String registrationOtp = captureOtp("flow@example.test");
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/verify-otp")
                .with(SecurityMockMvcRequestPostProcessors.csrf()).param("email", "flow@example.test")
                .param("otp", registrationOtp)).andExpect(status().is3xxRedirection());
        assertTrue(users.findByEmailIgnoreCase("flow@example.test").orElseThrow().isEnabled());

        MvcResult login = mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/login")
                .with(SecurityMockMvcRequestPostProcessors.csrf()).param("username", "flow-user")
                .param("password", "StartPass123!")).andExpect(status().is3xxRedirection()).andReturn();
        MockHttpSession session = (MockHttpSession) login.getRequest().getSession(false);
        mvc.perform(get("/products").session(session)).andExpect(status().isOk());

        clearInvocations(emailService);
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/forgot-password")
                .with(SecurityMockMvcRequestPostProcessors.csrf()).param("email", "flow@example.test"))
                .andExpect(status().is3xxRedirection());
        String resetOtp = captureOtp("flow@example.test");
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/reset-password")
                .with(SecurityMockMvcRequestPostProcessors.csrf()).param("email", "flow@example.test")
                .param("otp", resetOtp).param("password", "UpdatedPass123!")
                .param("confirmPassword", "UpdatedPass123!"))
                .andExpect(status().is3xxRedirection());
        assertTrue(passwordEncoder.matches("UpdatedPass123!", users.findByEmailIgnoreCase("flow@example.test").orElseThrow().getPassword()));
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/logout")
                .with(SecurityMockMvcRequestPostProcessors.csrf()).session(session))
                .andExpect(status().is3xxRedirection());
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/login")
                .with(SecurityMockMvcRequestPostProcessors.csrf()).param("username", "flow-user")
                .param("password", "UpdatedPass123!")).andExpect(status().is3xxRedirection());
    }

    @Test void adminRoleCanOpenUserManagement() throws Exception {
        User admin = new User();
        admin.setUsername("smoke-admin"); admin.setEmail("smoke-admin@example.test");
        admin.setFullName("Smoke Admin"); admin.setPassword(passwordEncoder.encode("AdminPass123!"));
        admin.setEnabled(true); admin.setRole(roles.findByNameIgnoreCase("ROLE_ADMIN").orElseThrow());
        users.save(admin);
        MvcResult login = mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/login")
                .with(SecurityMockMvcRequestPostProcessors.csrf()).param("username", "smoke-admin")
                .param("password", "AdminPass123!")).andExpect(status().is3xxRedirection()).andReturn();
        MockHttpSession session = (MockHttpSession) login.getRequest().getSession(false);
        mvc.perform(get("/users").session(session)).andExpect(status().isOk());
        mvc.perform(get("/users/create").session(session)).andExpect(status().isOk());
        mvc.perform(get("/products/create").session(session)).andExpect(status().isOk());

        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/users/create")
                .with(SecurityMockMvcRequestPostProcessors.csrf()).session(session)
                .param("username", "managed-user").param("email", "managed@example.test")
                .param("fullName", "Managed User").param("roleName", "ROLE_USER").param("enabled", "true"))
                .andExpect(status().is3xxRedirection());
        User managed = users.findByEmailIgnoreCase("managed@example.test").orElseThrow();
        assertTrue(passwordEncoder.matches("123456", managed.getPassword()));
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/users/edit/{id}", managed.getId())
                .with(SecurityMockMvcRequestPostProcessors.csrf()).session(session)
                .param("username", "managed-user").param("email", "managed@example.test")
                .param("fullName", "Updated User").param("roleName", "ROLE_USER").param("enabled", "true"))
                .andExpect(status().is3xxRedirection());
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/users/delete/{id}", managed.getId())
                .with(SecurityMockMvcRequestPostProcessors.csrf()).session(session))
                .andExpect(status().is3xxRedirection());

        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/products/create")
                .with(SecurityMockMvcRequestPostProcessors.csrf()).session(session)
                .param("name", "Smoke Product").param("description", "CRUD test")
                .param("price", "12.50")).andExpect(status().is3xxRedirection());
        var product = products.findAll().stream().filter(p -> p.getName().equals("Smoke Product")).findFirst().orElseThrow();
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/products/edit/{id}", product.getId())
                .with(SecurityMockMvcRequestPostProcessors.csrf()).session(session)
                .param("name", "Updated Product").param("description", "CRUD test")
                .param("price", "15.00")).andExpect(status().is3xxRedirection());
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/products/delete/{id}", product.getId())
                .with(SecurityMockMvcRequestPostProcessors.csrf()).session(session))
                .andExpect(status().is3xxRedirection());
    }

    private String captureOtp(String email) {
        ArgumentCaptor<String> otp = ArgumentCaptor.forClass(String.class);
        verify(emailService).sendOtp(eq(email), otp.capture(), anyString());
        return otp.getValue();
    }
}
