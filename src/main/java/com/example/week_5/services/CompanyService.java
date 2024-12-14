package com.example.week_5.services;

import com.example.week_5.dto.company.JobDto;
import com.example.week_5.models.*;
import com.example.week_5.models.enums.SkillLevel;
import com.example.week_5.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompanyService {
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

    @Autowired
    private SkillRepository skillrepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JavaMailSender mailSender;

    public Set<Job> getJobsForCompany(String username) {
    Company company = companyRepository.findByAccount_Username(username).orElseThrow();
    Set<Job> jobs = company.getJobs();

    return jobs;

    }

    public List<Map<String, Object>> getJobsForFitCandidate(String username) {
        Company company = companyRepository.findByAccount_Username(username).orElseThrow();

        if (company == null) {
            return null;
        }

        String sql = """
SELECT COUNT(candidate_skill.skill_id) AS SkillCount, candidate.id AS candidateId, job.job_id
FROM candidate_skill
INNER JOIN job_skill
ON candidate_skill.skill_id = job_skill.skill_id
INNER JOIN job
ON job_skill.job_id = job.job_id
INNER JOIN candidate
ON candidate.id = candidate_skill.candidate_id
WHERE job.company_id = :companyId
GROUP BY candidate_skill.skill_id, job_skill.id, job_skill.skill_id, candidate_skill.candidate_id,job.job_id, job_skill.skill_id, candidate.id
ORDER BY SkillCount DESC
LIMIT 10\s
                """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("companyId", company.getId());

        List<Object[]> result = query.getResultList();

        List<Map<String, Object>> results = new ArrayList<>();


        for (Object[] objects : result) {

            Long candidateId = Long.parseLong(objects[1].toString());
            Candidate candidate = candidateRepository.findById(candidateId).orElse(null);

            Long jobId = Long.parseLong(objects[2].toString());
            Job job = jobRepository.findById(jobId).orElse(null);


            boolean flag = false;
            for (Map<String, Object> result1 : results) {
                if (result1.get("candidate").equals(candidate)) {
                    List<Job> jobs = (List<Job>) result1.get("jobs");
                    jobs.add(job);
                    result1.put("jobs", jobs);

                    flag = true;
                    break;
                }
            }

            if (!flag) {
                Map<String, Object> jobMap = new HashMap<>();
                jobMap.put("candidate", candidate);
                jobMap.put("jobs", new ArrayList<>(List.of(job)));
                results.add(jobMap);
            }
        }

        System.out.println(results);

        return results;
    }

    public List<Skill> getSkill (){
        List<Skill> skills = new ArrayList<>();
        skills = skillrepo.findAll();
        return skills;
    }

    public boolean createJob (String username, JobDto job){
        Company company = companyRepository.findByAccount_Username(username).orElseThrow();

        Job newJob = new Job();
        newJob.setCompany(company);
        newJob.setJobName(job.getJobName());
        newJob.setJobDescription(job.getJobDescription());

        Job jobSave = jobRepository.save(newJob);

        JobSkill jobSkill = new JobSkill();
        jobSkill.setJob(jobSave);
        jobSkill.setSkill(skillrepo.findById(Long.valueOf(job.getSkillId())).orElseThrow());
        jobSkill.setSkillLevel(SkillLevel.valueOf(job.getSkillLevel()));

        jobSkillRepository.save(jobSkill);
        return true;

    }

    public boolean sendEmail (String email){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Job Opportunity");
            message.setText("Dear Candidate, we have a job opportunity for you. Please contact us for more details.");
            // Gửi email
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


