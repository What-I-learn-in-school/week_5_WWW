package com.example.week_5.repositories;

import com.example.week_5.models.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExperienceRepository extends JpaRepository<Experience, Long>, JpaSpecificationExecutor<Experience> {
}