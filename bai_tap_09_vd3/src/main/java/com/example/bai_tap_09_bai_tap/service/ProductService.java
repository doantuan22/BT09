package com.example.bai_tap_09_bai_tap.service;
import com.example.bai_tap_09_bai_tap.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
public interface ProductService { Page<ProductDTO> search(String q,int page,int size,Long ownerId); ProductDTO find(Long id,Long actorId,boolean admin); ProductDTO save(ProductDTO dto,Long actorId,boolean admin,MultipartFile image); void delete(Long id,Long actorId,boolean admin); long countProducts(); long countByUser(Long userId); }
