package com.example.volunteer_platform.repository;

import com.example.volunteer_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository<U extends User> extends JpaRepository<U, Long> {
    U findUserByEmail(String email);
    U findUserByUsername(String username);
}
