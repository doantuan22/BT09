package com.example.bai_tap_09.service;

import com.example.bai_tap_09.dto.UserDTO;

public interface UserService {
    UserDTO findById(Long id);
}
