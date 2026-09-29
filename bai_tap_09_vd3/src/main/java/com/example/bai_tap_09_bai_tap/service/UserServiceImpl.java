package com.example.bai_tap_09_bai_tap.service;

import com.example.bai_tap_09_bai_tap.dto.UserDTO;
import com.example.bai_tap_09_bai_tap.entity.Product;
import com.example.bai_tap_09_bai_tap.entity.Role;
import com.example.bai_tap_09_bai_tap.entity.User;
import com.example.bai_tap_09_bai_tap.mapper.UserMapper;
import com.example.bai_tap_09_bai_tap.repository.ProductRepository;
import com.example.bai_tap_09_bai_tap.repository.RoleRepository;
import com.example.bai_tap_09_bai_tap.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository users;
    private final RoleRepository roles;
    private final ProductRepository products;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;

    public UserServiceImpl(UserRepository users, RoleRepository roles,
                           ProductRepository products, UserMapper mapper,
                           PasswordEncoder passwordEncoder, CloudinaryService cloudinaryService) {
        this.users = users;
        this.roles = roles;
        this.products = products;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1),
                Sort.by(Sort.Direction.DESC, "id"));
        return users.search(keyword == null ? "" : keyword.trim(), pageable)
                .map(user -> {
                    UserDTO dto = mapper.toDto(user);
                    dto.setProductCount(users.countProductsByUserId(user.getId()));
                    return dto;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = users.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User không tồn tại."));
        UserDTO dto = mapper.toDto(user);
        dto.setProductCount(users.countProductsByUserId(id));
        return dto;
    }

    @Override
    public UserDTO create(UserDTO dto) {
        String username = dto.getUsername().trim();
        String email = dto.getEmail().trim().toLowerCase(Locale.ROOT);
        if (users.existsByUsernameIgnoreCase(username)) {
            throw new IllegalArgumentException("Username đã tồn tại.");
        }
        if (users.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("Email đã tồn tại.");
        }

        User user = mapper.toEntity(dto);
        user.setUsername(username);
        user.setEmail(email);
        user.setRole(findRole(dto.getRoleName()));
        user.setPassword(passwordEncoder.encode("123456"));
        user.setEnabled(dto.isEnabled());
        return mapper.toDto(users.save(user));
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) {
        User user = users.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User không tồn tại."));
        String username = dto.getUsername().trim();
        String email = dto.getEmail().trim().toLowerCase(Locale.ROOT);
        if (users.existsByUsernameIgnoreCaseAndIdNot(username, id)) {
            throw new IllegalArgumentException("Username đã tồn tại.");
        }
        if (users.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new IllegalArgumentException("Email đã tồn tại.");
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setFullName(dto.getFullName().trim());
        user.setEnabled(dto.isEnabled());
        if (dto.getRoleName() != null && !dto.getRoleName().isBlank()) {
            user.setRole(findRole(dto.getRoleName()));
        }
        return mapper.toDto(users.save(user));
    }

    @Override
    public void delete(Long id) {
        User user = users.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User không tồn tại."));
        List<Product> ownedProducts = products.findAllByUserId(id);
        for (Product product : ownedProducts) {
            String imageUrl = product.getImageUrl();
            if (imageUrl != null && imageUrl.contains("|")) {
                cloudinaryService.delete(imageUrl.substring(imageUrl.indexOf('|') + 1));
            }
        }
        products.deleteAll(ownedProducts);
        users.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public long countUsers() {
        return users.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countProducts(Long userId) {
        return users.countProductsByUserId(userId);
    }

    private Role findRole(String roleName) {
        String name = roleName == null || roleName.isBlank() ? "ROLE_USER" : roleName;
        return roles.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Role không tồn tại."));
    }
}
