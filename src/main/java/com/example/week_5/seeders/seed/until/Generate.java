package com.example.week_5.seeders.seed.until;

import com.example.week_5.models.Candidate;
import com.example.week_5.models.Skill;
import com.example.week_5.models.enums.SkillLevel;
import com.example.week_5.models.enums.SkillType;
import com.example.week_5.repositories.CandidateRepository;
import com.example.week_5.repositories.SkillRepository;
import com.example.week_5.seeders.seed.SkillSeeder;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Generate {
    @Autowired private SkillRepository skillRepository;
    @Autowired private CandidateRepository candidateRepository;

    public Candidate generateRandomCandidate() {
        List<Candidate> candidates = candidateRepository.findAll();
        int randomIndex = new Random().nextInt(candidates.size());
        return candidates.get(randomIndex);
    }

    public Skill getRandomSkill() {
        List<Skill> skills = skillRepository.findAll();
        int randomIndex = new Random().nextInt(skills.size());
        return skills.get(randomIndex);
    }

    public SkillLevel getRandomSkillLevel() {
        SkillLevel[] skillLevels = SkillLevel.values();
        int randomIndex = new Random().nextInt(skillLevels.length);
        return skillLevels[randomIndex];
    }

    public  SkillType getRandomSkillType() {
        SkillType[] skillTypes = SkillType.values();
        int randomIndex = new Random().nextInt(skillTypes.length); // Chọn chỉ số ngẫu nhiên
        return skillTypes[randomIndex];
    }

    public  LocalDate getRandomLocalDate(int yearStart, int yearEnd) {
        long minDay = LocalDate.of(yearStart, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(yearEnd, 1, 1).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    public LocalDate generateFutureDateFromRandom(LocalDate randomDate) {
        LocalDate today = LocalDate.now();

        // Nếu ngày random sau ngày hiện tại, trả về chính ngày random
        if (!randomDate.isBefore(today)) {
            return randomDate;
        }

        // Sinh ngày tương lai từ ngày random đến ngày hiện tại
        long minDay = randomDate.toEpochDay();
        long maxDay = today.toEpochDay();
        long randomFutureDay = ThreadLocalRandom.current().nextLong(minDay, maxDay + 1); // Bao gồm cả today
        return LocalDate.ofEpochDay(randomFutureDay);
    }
}
