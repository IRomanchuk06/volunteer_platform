package com.example.volunteer_platform.repository;

import com.example.volunteer_platform.model.Volunteer;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends UserRepository<Volunteer>
{
}
