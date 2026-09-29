package com.example.bai_tap_09_bai_tap.config;

import com.example.bai_tap_09_bai_tap.entity.Product;
import com.example.bai_tap_09_bai_tap.entity.Role;
import com.example.bai_tap_09_bai_tap.entity.User;
import com.example.bai_tap_09_bai_tap.repository.ProductRepository;
import com.example.bai_tap_09_bai_tap.repository.RoleRepository;
import com.example.bai_tap_09_bai_tap.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roles;
    private final UserRepository users;
    private final ProductRepository products;
    private final PasswordEncoder encoder;

    @Value("${APP_ADMIN_EMAIL:}") private String adminEmail;
    @Value("${APP_ADMIN_USERNAME:admin}") private String adminUsername;
    @Value("${APP_ADMIN_PASSWORD:}") private String adminPassword;
    @Value("${APP_SAMPLE_USER_EMAIL:user01@bai_tap_09_bai_tap.test}") private String sampleEmail;
    @Value("${APP_SAMPLE_USER_USERNAME:user01}") private String sampleUsername;
    @Value("${APP_SAMPLE_USER_PASSWORD:}") private String samplePassword;

    public DataInitializer(RoleRepository roles, UserRepository users,
                           ProductRepository products, PasswordEncoder encoder) {
        this.roles = roles;
        this.users = users;
        this.products = products;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Role userRole = roles.findByNameIgnoreCase("ROLE_USER")
                .orElseGet(() -> roles.save(new Role("ROLE_USER")));
        Role adminRole = roles.findByNameIgnoreCase("ROLE_ADMIN")
                .orElseGet(() -> roles.save(new Role("ROLE_ADMIN")));

        createUserIfConfigured(adminUsername, adminEmail, adminPassword,
                "Administrator", adminRole);
        User sampleUser = createUserIfConfigured(sampleUsername, sampleEmail,
                samplePassword, "Người dùng thử nghiệm", userRole);
        if (sampleUser != null) {
            seedProducts(sampleUser);
        }
    }

    private User createUserIfConfigured(String username, String email, String password,
                                        String fullName, Role role) {
        if (username == null || username.isBlank() || email == null || email.isBlank()
                || password == null || password.isBlank()) {
            return users.findByUsername(username == null ? "" : username).orElse(null);
        }
        User existing = users.findByEmailIgnoreCase(email.trim()).orElse(null);
        if (existing == null) {
            existing = users.findByUsername(username.trim()).orElse(null);
        }
        if (existing != null) {
            return existing;
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setEmail(email.trim().toLowerCase(java.util.Locale.ROOT));
        user.setPassword(encoder.encode(password));
        user.setFullName(fullName);
        user.setEnabled(true);
        user.setRole(role);
        return users.save(user);
    }

    private void seedProducts(User owner) {
        List<ProductSeed> seeds = List.of(
                new ProductSeed("Tai nghe Bluetooth", "Tai nghe không dây, pin lâu.", "890000.00"),
                new ProductSeed("Bàn phím cơ", "Bàn phím cơ layout gọn, switch tactile.", "1250000.00"),
                new ProductSeed("Chuột không dây", "Chuột quang không dây dùng cho văn phòng.", "450000.00"),
                new ProductSeed("Màn hình 24 inch", "Màn hình Full HD phù hợp học tập và làm việc.", "3290000.00"),
                new ProductSeed("USB-C Hub", "Bộ chuyển đổi USB-C nhiều cổng.", "690000.00")
        );
        List<String> existingNames = products.findAllByUserId(owner.getId()).stream()
                .map(Product::getName)
                .toList();
        for (ProductSeed seed : seeds) {
            if (existingNames.contains(seed.name())) {
                continue;
            }
            Product product = new Product();
            product.setName(seed.name());
            product.setDescription(seed.description());
            product.setPrice(new BigDecimal(seed.price()));
            product.setUser(owner);
            products.save(product);
        }
    }

    private record ProductSeed(String name, String description, String price) {}
}
