package com.example.volunteer_platform.server.unit.mapper;

import com.example.volunteer_platform.server.mapper.UserMapper;
import com.example.volunteer_platform.server.model.*;
import com.example.volunteer_platform.server.unit.BaseUnitTests;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTests extends BaseUnitTests {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapVolunteerToDTO() {
        Volunteer volunteer = Volunteer.builder()
                .id(2L)
                .username("volunteer1")
                .password("password2")
                .email("volunteer1@test.com")
                .role(User.UserRole.VOLUNTEER)
                .build();

        UserResponseDTO dto = userMapper.toUserResponseDTO(volunteer);

        assertAll(
                () -> assertEquals(2L, dto.getId()),
                () -> assertEquals("volunteer1", dto.getUsername()),
                () -> assertEquals("password2", dto.getPassword()),
                () -> assertEquals("volunteer1@test.com", dto.getEmail()),
                () -> assertEquals("VOLUNTEER", dto.getRole())
        );
    }

    @Test
    void shouldMapCustomerToDTO() {
        Customer customer = Customer.builder()
                .id(3L)
                .username("customer1")
                .password("password3")
                .email("customer1@test.com")
                .role(User.UserRole.CUSTOMER)
                .build();

        UserResponseDTO dto = userMapper.toUserResponseDTO(customer);

        assertAll(
                () -> assertEquals(3L, dto.getId()),
                () -> assertEquals("customer1", dto.getUsername()),
                () -> assertEquals("password3", dto.getPassword()),
                () -> assertEquals("customer1@test.com", dto.getEmail()),
                () -> assertEquals("CUSTOMER", dto.getRole())
        );
    }

    @Test
    public void testToUserResponseDTO_Customer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setUsername("testUser");
        customer.setPassword("testPassword");
        customer.setEmail("test@example.com");
        customer.setRole(User.UserRole.CUSTOMER);

        UserResponseDTO userResponseDTO = userMapper.toUserResponseDTO(customer);

        assertEquals(customer.getId(), userResponseDTO.getId());
        assertEquals(customer.getUsername(), userResponseDTO.getUsername());
        assertEquals(customer.getPassword(), userResponseDTO.getPassword());
        assertEquals(customer.getEmail(), userResponseDTO.getEmail());
        assertEquals(customer.getRole().name(), userResponseDTO.getRole());
    }

    @Test
    public void testToUserResponseDTO_User() {
        User user = new User() {};
        user.setId(2L);
        user.setUsername("testUser2");
        user.setPassword("testPassword2");
        user.setEmail("test2@example.com");
        user.setRole(User.UserRole.VOLUNTEER);

        UserResponseDTO userResponseDTO = userMapper.toUserResponseDTO(user);

        assertEquals(user.getId(), userResponseDTO.getId());
        assertEquals(user.getUsername(), userResponseDTO.getUsername());
        assertEquals(user.getPassword(), userResponseDTO.getPassword());
        assertEquals(user.getEmail(), userResponseDTO.getEmail());
        assertEquals(user.getRole().name(), userResponseDTO.getRole());
    }
}