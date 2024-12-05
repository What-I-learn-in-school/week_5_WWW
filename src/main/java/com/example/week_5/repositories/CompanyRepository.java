package com.example.week_5.repositories;

import com.example.week_5.models.Company;
import com.example.week_5.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
    List<Company> findByJobs_JobSkills_Skill(Skill skill);

    Optional<Company> findByAccount_Id(Long id);

    Optional<Company> findByAccount_Username(String username);
}