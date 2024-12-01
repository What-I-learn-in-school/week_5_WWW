package com.example.week_5.seeders;

import com.example.week_5.seeders.seed.CandidateSeeder;
import com.example.week_5.seeders.seed.CompanySeeder;
import com.example.week_5.seeders.seed.JobSeeder;
import com.example.week_5.seeders.seed.SkillSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigSeeder {

    @Bean
    public CommandLineRunner run(CompanySeeder companySeeder, SkillSeeder skillSeeder, JobSeeder jobSeeder, CandidateSeeder candidateSeeder) {
        return args -> {
            companySeeder.seed(100); // Tạo 100 người dùng giả lập
            skillSeeder.seed(100); // Tạo 100 kỹ năng giả
            jobSeeder.seed();
            candidateSeeder.seed(100);
        };
    }
}