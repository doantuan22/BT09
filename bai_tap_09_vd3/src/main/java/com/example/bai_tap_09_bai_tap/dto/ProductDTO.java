package com.example.bai_tap_09_bai_tap.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;
public class ProductDTO {
 private Long id;
 @NotBlank(message="Tên sản phẩm không được để trống") private String name;
 private String description;
 @NotNull(message="Giá không được để trống") @DecimalMin(value="0.0",message="Giá phải >= 0") private BigDecimal price;
 private String imageUrl; private Long userId; private String username; private LocalDateTime createdAt; private MultipartFile image;
 public Long getId(){return id;} public void setId(Long v){id=v;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;} public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
 public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;} public Long getUserId(){return userId;} public void setUserId(Long v){userId=v;}
 public String getUsername(){return username;} public void setUsername(String v){username=v;} public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
 public MultipartFile getImage(){return image;} public void setImage(MultipartFile v){image=v;}
}
