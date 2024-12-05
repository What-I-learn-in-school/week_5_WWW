package com.example.week_5.services;

import com.example.week_5.models.*;
import com.example.week_5.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CandidateService {
    @Autowired
    private JobSkillRepository jobSkillRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Company> getJobsForFitCandidate(String username) {

        Candidate candidate = candidateRepository.findByAccount_Username(username).orElse(null);
        if (candidate == null) {
            return null;
        }

        String sql = """
                 SELECT candidate_skill.skill_id, COUNT(candidate_skill.skill_id) AS SkillCount, job.job_id, company.comp_id
                 FROM candidate_skill
                 INNER JOIN job_skill
                 ON candidate_skill.skill_id = job_skill.skill_id
                 INNER JOIN job
                 ON job_skill.job_id = job.job_id
                 INNER JOIN company
                 ON company.comp_id = job.company_id
                WHERE candidate_skill.candidate_id = :candidateId
                 GROUP BY candidate_skill.skill_id, job_skill.skill_id, candidate_skill.candidate_id,job.job_id
                 ORDER BY SkillCount desc
                LIMIT 10
                """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("candidateId", candidate.getId());

        List<Object[]> result = query.getResultList();

        List<Company> companies = new ArrayList<>();

        for (Object[] objects : result) {
            Long job = Long.parseLong(objects[2].toString());
            Company company = new Company();
            Job jobEntity = jobRepository.findById(job).orElse(null);

            if (jobEntity != null) {
                boolean flag = false;
                for (Company company1 : companies) {
                    if (company1.getId().equals(jobEntity.getCompany().getId())) {
                        Set<Job> jobs = company1.getJobs();
                        jobs.add(jobEntity);
                        company1.setJobs(jobs);

                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    company.setId(jobEntity.getCompany().getId());
                    company.setCompanyName(jobEntity.getCompany().getCompanyName());
                    company.setAbout(jobEntity.getCompany().getAbout());
                    company.setAddress(jobEntity.getCompany().getAddress());
                    company.setPhone(jobEntity.getCompany().getPhone());
                    company.setWebUrl(jobEntity.getCompany().getWebUrl());
                    company.setEmail(jobEntity.getCompany().getEmail());


                    Set<Job> jobs = new LinkedHashSet<>();
                    jobs.add(jobEntity);

                    company.setJobs(jobs);

                    companies.add(company);

                }
            }
        }
        return companies;
    }

    public List<Company> getJobsCanLearn(String username) {

        Candidate candidate = candidateRepository.findByAccount_Username(username).orElse(null);
        if (candidate == null) {
            return null;
        }

        String sql = """
                 SELECT candidate_skill.skill_id, COUNT(candidate_skill.skill_id) AS SkillCount, job.job_id, company.comp_id
                 FROM candidate_skill
                 INNER JOIN job_skill
                 ON candidate_skill.skill_id = job_skill.skill_id
                 INNER JOIN job
                 ON job_skill.job_id = job.job_id
                 INNER JOIN company
                 ON company.comp_id = job.company_id
                WHERE candidate_skill.candidate_id = :candidateId
                 GROUP BY candidate_skill.skill_id, job_skill.skill_id, candidate_skill.candidate_id,job.job_id
                 ORDER BY SkillCount asc
                LIMIT 10
                """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("candidateId", candidate.getId());

        List<Object[]> result = query.getResultList();

        List<Company> companies = new ArrayList<>();

        for (Object[] objects : result) {
            Long job = Long.parseLong(objects[2].toString());
            Company company = new Company();
            Job jobEntity = jobRepository.findById(job).orElse(null);

            if (jobEntity != null) {
                boolean flag = false;
                for (Company company1 : companies) {
                    if (company1.getId().equals(jobEntity.getCompany().getId())) {
                        Set<Job> jobs = company1.getJobs();
                        jobs.add(jobEntity);
                        company1.setJobs(jobs);

                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    company.setId(jobEntity.getCompany().getId());
                    company.setCompanyName(jobEntity.getCompany().getCompanyName());
                    company.setAbout(jobEntity.getCompany().getAbout());
                    company.setAddress(jobEntity.getCompany().getAddress());
                    company.setPhone(jobEntity.getCompany().getPhone());
                    company.setWebUrl(jobEntity.getCompany().getWebUrl());
                    company.setEmail(jobEntity.getCompany().getEmail());


                    Set<Job> jobs = new LinkedHashSet<>();
                    jobs.add(jobEntity);

                    company.setJobs(jobs);

                    companies.add(company);

                }
            }
        }
        return companies;
    }
}


