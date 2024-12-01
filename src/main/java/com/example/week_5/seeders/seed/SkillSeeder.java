package com.example.week_5.seeders.seed;

import com.example.week_5.models.JobSkill;
import com.example.week_5.models.Skill;
import com.example.week_5.models.enums.SkillType;
import com.example.week_5.repositories.SkillRepository;
import com.example.week_5.seeders.seed.until.Generate;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class SkillSeeder {
    @Autowired private SkillRepository skillRepository;
    @Autowired private Generate generate;


    public void seed(int number) {
        Faker faker = new Faker();

        for (int i = 0; i < number; i++) {
            Skill skill = new Skill();
            skill.setSkillName(faker.job().keySkills());
            skill.setSkillDescription(faker.lorem().sentence());
            skill.setSkillType(generate.getRandomSkillType());

            skillRepository.save(skill);
        }
    }
}
