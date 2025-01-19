package com.example.volunteer_platform.server.repository;

import com.example.volunteer_platform.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    User findUserByUsername(String username);

    boolean existsUserByEmail(String email);
}
