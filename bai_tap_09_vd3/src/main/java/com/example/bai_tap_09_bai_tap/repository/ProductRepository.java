package com.example.bai_tap_09_bai_tap.repository;
import com.example.bai_tap_09_bai_tap.entity.Product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product,Long> {
 Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name,String description,Pageable pageable);
 Page<Product> findByUserId(Long userId,Pageable pageable);
 java.util.List<Product> findAllByUserId(Long userId);
 @Query("select p from Product p where p.user.id = :userId and (lower(p.name) like lower(concat('%', :q, '%')) or lower(coalesce(p.description, '')) like lower(concat('%', :q, '%')))")
 Page<Product> searchByOwner(@Param("userId") Long userId,@Param("q") String q,Pageable pageable);
 long countByUserId(Long userId);
}
