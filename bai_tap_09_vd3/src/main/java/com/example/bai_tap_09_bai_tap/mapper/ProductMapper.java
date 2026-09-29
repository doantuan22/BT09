package com.example.bai_tap_09_bai_tap.mapper;
import com.example.bai_tap_09_bai_tap.dto.ProductDTO;
import com.example.bai_tap_09_bai_tap.entity.Product;
import org.mapstruct.*;
@Mapper(componentModel="spring",unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface ProductMapper {
 @Mapping(source="user.id",target="userId") @Mapping(source="user.username",target="username") @Mapping(target="image",ignore=true) ProductDTO toDto(Product product);
 @Mapping(target="user",ignore=true) @Mapping(target="createdAt",ignore=true) Product toEntity(ProductDTO dto);
}
