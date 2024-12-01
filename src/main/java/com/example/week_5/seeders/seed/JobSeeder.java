package com.example.week_5.seeders.seed;


import com.example.week_5.models.Company;
import com.example.week_5.models.Job;
import com.example.week_5.models.JobSkill;
import com.example.week_5.models.Skill;
import com.example.week_5.models.enums.SkillLevel;
import com.example.week_5.repositories.CompanyRepository;
import com.example.week_5.repositories.JobRepository;
import com.example.week_5.repositories.JobSkillRepository;
import com.example.week_5.repositories.SkillRepository;
import com.example.week_5.seeders.seed.until.Generate;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class JobSeeder {
    @Autowired private CompanyRepository companyRepository;
    @Autowired private JobRepository jobRepository;
    @Autowired private SkillRepository skillRepository;
    @Autowired private JobSkillRepository jobSkillRepository;
    @Autowired private Generate generate;



    public void seed() {
        Faker faker = new Faker();
        List<Company> companies = companyRepository.findAll();

        for (Company company : companies){
            int random = faker.random().nextInt(5, 10);

            for (int i = 0; i < random; i++) {
                Job job = new Job();
                job.setJobName(faker.job().position());
                job.setJobDescription(faker.lorem().sentence());
                job.setCompany(company);

                Job jobSave = jobRepository.save(job);

                for (int j = 0; j < faker.random().nextInt(1, 5); j++) {
                    Skill skill = generate.getRandomSkill();

                    JobSkill jobSkill = new JobSkill();
                    jobSkill.setJob(jobSave);
                    jobSkill.setSkill(skill);
                    jobSkill.setMoreInfos(faker.lorem().sentence());
                    jobSkill.setSkillLevel(generate.getRandomSkillLevel());

                    jobSkillRepository.save(jobSkill);
                }
            }
        }
    }
}
