package com.example.bai_tap_09_bai_tap.service;
import com.example.bai_tap_09_bai_tap.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
public interface ProductService {
    Page<ProductDTO> findAll(String keyword, int page, int size);
    ProductDTO findById(Long id);
    ProductDTO create(ProductDTO dto, MultipartFile image);
    ProductDTO update(Long id, ProductDTO dto, MultipartFile image);
    void delete(Long id);
    long countProducts();
    long countByUser(Long userId);

    // Actor-aware operations keep product ownership checks in the web flow.
    Page<ProductDTO> search(String q, int page, int size, Long ownerId);
    ProductDTO find(Long id, Long actorId, boolean admin);
    ProductDTO save(ProductDTO dto, Long actorId, boolean admin, MultipartFile image);
    void delete(Long id, Long actorId, boolean admin);
}
