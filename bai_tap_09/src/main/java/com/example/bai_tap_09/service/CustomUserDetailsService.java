package com.example.bai_tap_09.service;

import com.example.bai_tap_09.entity.User;
import com.example.bai_tap_09.repository.UserRepository;
import java.util.Locale;
import com.example.bai_tap_09.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        String normalizedLogin = login.trim().toLowerCase(Locale.ROOT);
        User user = userRepository.findByUsernameOrEmail(normalizedLogin, normalizedLogin)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }
}
