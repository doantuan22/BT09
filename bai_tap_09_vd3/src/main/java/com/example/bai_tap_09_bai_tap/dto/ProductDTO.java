package com.example.bai_tap_09_bai_tap.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public class ProductDTO {
 private Long id;
 @NotBlank @Size(max=150) private String name;
 @Size(max=2000) private String description;
 @NotNull @DecimalMin("0.00") @Digits(integer=10,fraction=2) private BigDecimal price;
 private String imageUrl; private Long userId; private String username; private LocalDateTime createdAt;
 public Long getId(){return id;} public void setId(Long v){id=v;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;} public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
 public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;} public Long getUserId(){return userId;} public void setUserId(Long v){userId=v;}
 public String getUsername(){return username;} public void setUsername(String v){username=v;} public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
