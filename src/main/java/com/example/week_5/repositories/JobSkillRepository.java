package com.example.week_5.repositories;

import com.example.week_5.models.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobSkillRepository extends JpaRepository<JobSkill, Long>, JpaSpecificationExecutor<JobSkill> {
}