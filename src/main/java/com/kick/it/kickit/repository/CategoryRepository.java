package com.kick.it.kickit.repository;

import com.kick.it.kickit.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Long> {
}
