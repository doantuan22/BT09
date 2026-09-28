package com.example.bai_tap_09.config;

import com.example.bai_tap_09.entity.Role;
import com.example.bai_tap_09.entity.User;
import com.example.bai_tap_09.repository.RoleRepository;
import com.example.bai_tap_09.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${APP_ADMIN_EMAIL:}")
    private String adminEmail;

    @Value("${APP_ADMIN_USERNAME:admin}")
    private String adminUsername;

    @Value("${APP_ADMIN_PASSWORD:}")
    private String adminPassword;

    @Value("${APP_SAMPLE_USER_PASSWORD:}")
    private String sampleUserPassword;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        findOrCreateRole("USER");
        Role adminRole = findOrCreateRole("ADMIN");
        Role sampleUserRole = findOrCreateRole("ROLE_USER");

        if (sampleUserPassword != null && !sampleUserPassword.isBlank()
                && userRepository.findByUsername("user01").isEmpty()
                && !userRepository.existsByEmailIgnoreCase("user01@gmail.com")) {
            User sampleUser = new User();
            sampleUser.setUsername("user01");
            sampleUser.setEmail("user01@gmail.com");
            sampleUser.setPassword(passwordEncoder.encode(sampleUserPassword));
            sampleUser.setFullName("Nguyễn Hữu Trung");
            sampleUser.setImages("/images/user.png");
            sampleUser.setEnabled(true);
            sampleUser.setRole(sampleUserRole);
            userRepository.save(sampleUser);
        }

        if (adminEmail == null || adminEmail.isBlank()
                || adminPassword == null || adminPassword.isBlank()
                || userRepository.existsByEmailIgnoreCase(adminEmail)
                || userRepository.existsByUsernameIgnoreCase(adminUsername)) {
            return;
        }

        User admin = new User();
        admin.setUsername(adminUsername == null || adminUsername.isBlank() ? "admin" : adminUsername);
        admin.setEmail(adminEmail.trim());
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setFullName("Administrator");
        admin.setEnabled(true);
        admin.setRole(adminRole);
        userRepository.save(admin);
    }

    private Role findOrCreateRole(String name) {
        return roleRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> roleRepository.save(new Role(name)));
    }
}
