package com.example.week_5.repositories;

import com.example.week_5.models.CandidateSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Long>, JpaSpecificationExecutor<CandidateSkill> {
}