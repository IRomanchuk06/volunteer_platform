package com.example.volunteer_platform.server.repository;

import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE TYPE(u) IN (Volunteer, Customer) AND u.email = :email")
    User findUserByEmail(@Param("email") String email);

    User findUserByUsername(String username);

    boolean existsUserByEmail(String email);

    @Query("SELECT COUNT(v) > 0 FROM Event e JOIN e.volunteers v WHERE e.id = :eventId AND v.id = :volunteerId")
    boolean existsByEventIdAndVolunteerId(@Param("eventId") Long eventId, @Param("volunteerId") Long volunteerId);
}
