package com.example.bai_tap_09_bai_tap.repository;

import com.example.bai_tap_09_bai_tap.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameIgnoreCase(String name);
}
