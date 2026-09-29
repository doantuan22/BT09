package com.example.bai_tap_09_bai_tap.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "products", indexes = @Index(name = "idx_products_name", columnList = "name"))
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 500, columnDefinition = "nvarchar(500)") private String name;
    @Column(length = 5000, columnDefinition = "nvarchar(500)") private String description;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal price;
    @Column(length = 1000) private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id", nullable = false) private User user;
    @Column(nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
    public Product() {}
    public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
    public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;}
    public User getUser(){return user;} public void setUser(User v){user=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
