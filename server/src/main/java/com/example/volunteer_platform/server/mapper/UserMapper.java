package com.example.volunteer_platform.server.mapper;

import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toUserResponseDTO(User user);
}