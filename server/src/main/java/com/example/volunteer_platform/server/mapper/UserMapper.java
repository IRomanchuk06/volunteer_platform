package com.example.volunteer_platform.server.mapper;

import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "password", source = "user.password")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "role", source = "user.role")
    @Mapping(target = "notifications", source = "user.notifications")
    UserResponseDTO toUserResponseDTO(User user);

    @Mapping(target = "id", source = "volunteer.id")
    @Mapping(target = "username", source = "volunteer.username")
    @Mapping(target = "password", source = "volunteer.password")
    @Mapping(target = "email", source = "volunteer.email")
    @Mapping(target = "role", source = "volunteer.role")
    @Mapping(target = "notifications", source = "volunteer.notifications")
    UserResponseDTO toUserResponseDTO(Volunteer volunteer);

    @Mapping(target = "id", source = "customer.id")
    @Mapping(target = "username", source = "customer.username")
    @Mapping(target = "password", source = "customer.password")
    @Mapping(target = "email", source = "customer.email")
    @Mapping(target = "role", source = "customer.role")
    @Mapping(target = "notifications", source = "customer.notifications")
    UserResponseDTO toUserResponseDTO(Customer customer);
}
