package com.example.volunteer_platform.repository;

import com.example.volunteer_platform.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface  CustomerRepository extends UserRepository<Customer>
{
}