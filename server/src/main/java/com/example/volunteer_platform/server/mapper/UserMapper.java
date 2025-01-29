package com.example.volunteer_platform.server.mapper;

import com.example.volunteer_platform.server.model.Customer;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @SubclassMapping(source = Volunteer.class, target = UserResponseDTO.class)
    @SubclassMapping(source = Customer.class, target = UserResponseDTO.class)
    UserResponseDTO toUserResponseDTO(User user);
}