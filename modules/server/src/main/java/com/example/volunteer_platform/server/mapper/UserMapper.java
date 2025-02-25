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
    @Mapping(target = "id", expression = "java(user.getId())")
    @Mapping(target = "username", expression = "java(user.getUsername())")
    @Mapping(target = "password", expression = "java(user.getPassword())")
    @Mapping(target = "email", expression = "java(user.getEmail())")
    @Mapping(target = "role", expression = "java(user.getRole().name())")
    UserResponseDTO toUserResponseDTO(User user);

    @Mapping(target = "id", expression = "java(volunteer.getId())")
    @Mapping(target = "username", expression = "java(volunteer.getUsername())")
    @Mapping(target = "password", expression = "java(volunteer.getPassword())")
    @Mapping(target = "email", expression = "java(volunteer.getEmail())")
    @Mapping(target = "role", expression = "java(volunteer.getRole().name())")
    UserResponseDTO toUserResponseDTO(Volunteer volunteer);

    @Mapping(target = "id", expression = "java(customer.getId())")
    @Mapping(target = "username", expression = "java(customer.getUsername())")
    @Mapping(target = "password", expression = "java(customer.getPassword())")
    @Mapping(target = "email", expression = "java(customer.getEmail())")
    @Mapping(target = "role", expression = "java(customer.getRole().name())")
    UserResponseDTO toUserResponseDTO(Customer customer);
}
