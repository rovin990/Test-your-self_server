package com.kick.it.kickit.repository;

import com.kick.it.kickit.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
}
