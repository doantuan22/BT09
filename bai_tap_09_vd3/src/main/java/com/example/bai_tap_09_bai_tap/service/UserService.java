package com.example.bai_tap_09_bai_tap.service;

import com.example.bai_tap_09_bai_tap.dto.UserDTO;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserDTO> findAll(String keyword, int page, int size);
    UserDTO findById(Long id);
    UserDTO create(UserDTO dto);
    UserDTO update(Long id, UserDTO dto);
    void delete(Long id);
    long countUsers();
    long countProducts(Long userId);
}
