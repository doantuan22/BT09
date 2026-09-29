package com.example.bai_tap_09_bai_tap.mapper;

import com.example.bai_tap_09_bai_tap.dto.UserDTO;
import com.example.bai_tap_09_bai_tap.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "role.name", target = "roleName")
    UserDTO toDto(User user);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(UserDTO userDTO);
}
