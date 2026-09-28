package com.example.bai_tap_09;

import com.example.bai_tap_09.entity.Role;
import com.example.bai_tap_09.entity.User;
import com.example.bai_tap_09.repository.UserRepository;
import com.example.bai_tap_09.security.CustomUserDetails;
import com.example.bai_tap_09.service.CustomUserDetailsService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BaiTap09ApplicationTests {

    @Test
    void acceptsUsernameOrEmailAndProvidesPrincipalFields() {
        UserRepository repository = mock(UserRepository.class);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = sampleUser(encoder.encode("123456"), true);
        when(repository.findByUsernameOrEmail(eq("user01"), eq("user01"))).thenReturn(Optional.of(user));
        when(repository.findByUsernameOrEmail(eq("user01@gmail.com"), eq("user01@gmail.com")))
                .thenReturn(Optional.of(user));

        CustomUserDetailsService service = new CustomUserDetailsService(repository);
        CustomUserDetails byUsername = (CustomUserDetails) service.loadUserByUsername("user01");
        CustomUserDetails byEmail = (CustomUserDetails) service.loadUserByUsername("USER01@GMAIL.COM");

        assertEquals("user01@gmail.com", byUsername.getEmail());
        assertEquals("user01@gmail.com", byEmail.getEmail());
        assertEquals("Nguy?n H?u Trung", byUsername.getFullName());
        assertEquals("/images/user.png", byUsername.getImages());
        assertEquals("ROLE_USER", byUsername.getRole());
        assertTrue(byUsername.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        assertTrue(encoder.matches("123456", byUsername.getPassword()));
    }

    @Test
    void customPrincipalPreservesDisabledState() {
        UserRepository repository = mock(UserRepository.class);
        User user = sampleUser("{bcrypt}hash", false);
        when(repository.findByUsernameOrEmail(eq("user01"), eq("user01"))).thenReturn(Optional.of(user));

        CustomUserDetails details = (CustomUserDetails)
                new CustomUserDetailsService(repository).loadUserByUsername("user01");
        assertFalse(details.isEnabled());
    }

    private User sampleUser(String password, boolean enabled) {
        Role role = new Role("ROLE_USER");
        User user = new User();
        user.setUsername("user01");
        user.setEmail("user01@gmail.com");
        user.setPassword(password);
        user.setFullName("Nguy?n H?u Trung");
        user.setImages("/images/user.png");
        user.setEnabled(enabled);
        user.setRole(role);
        return user;
    }
}
