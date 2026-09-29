package com.example.bai_tap_09_bai_tap.service;
import com.example.bai_tap_09_bai_tap.dto.UserDTO;
import org.springframework.data.domain.Page;
public interface UserService { Page<UserDTO> search(String q,int page,int size); UserDTO findById(Long id); UserDTO save(UserDTO dto,String password); void delete(Long id); long count(); long countProducts(Long userId); }
