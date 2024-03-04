package com.kick.it.kickit.repository;

import com.kick.it.kickit.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    public Customer findByUsername(String username);
}
