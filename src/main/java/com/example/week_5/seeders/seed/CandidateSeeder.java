package com.example.week_5.seeders.seed;

import com.example.week_5.models.*;
import com.example.week_5.models.enums.Role;
import com.example.week_5.repositories.*;
import com.example.week_5.seeders.seed.until.Generate;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class CandidateSeeder {
    @Autowired private CandidateRepository candidateRepository;
    @Autowired private AddressRepository addressRepository;
    @Autowired private SkillRepository skillRepository;
    @Autowired private SkillSeeder skillSeeder;
    @Autowired private ExperienceRepository experienceRepository;
    @Autowired private CandidateSkillRepository candidateSkillRepository;
    @Autowired private Generate generate;
    @Autowired private AccountRepository accountRepository;

    public void seed(int number) {
        Faker faker = new Faker();

        List<Candidate> candidates = candidateRepository.findAll();
        if (!candidates.isEmpty() ){
            return;
        }

        for (int i = 0; i < number; i++) {
            LocalDate date = generate.getRandomLocalDate(2010, 2024);

            Account account  = new Account();
            account.setUsername(faker.name().username());
            account.setPassword(new BCryptPasswordEncoder().encode("123"));
            account.setRole(Role.CANDIDATE);

            Account accountSave = accountRepository.save(account);


            Address address = new Address();
            address.setCity(faker.address().city());
            address.setCountry(faker.random().nextInt(1, 100).shortValue());
            address.setNumber(faker.address().buildingNumber());
            address.setStreet(faker.address().streetName());
            address.setZipcode(faker.address().zipCode());

            Address addressSave = addressRepository.save(address);

            Candidate candidate = new Candidate();
            candidate.setFullName(faker.name().fullName());
            candidate.setPhone(faker.phoneNumber().cellPhone());
            candidate.setEmail(faker.internet().emailAddress());
            candidate.setDob(date);

            candidate.setAddress(addressSave);
            candidate.setAccount(accountSave);

            Candidate candidateSave = candidateRepository.save(candidate);

            for (int j = 0; j < faker.random().nextInt(1, 5); j++) {
                Experience experience = new Experience();
                experience.setCompanyName(faker.company().name());
                experience.setFromDate(date);
                experience.setToDate(generate.generateFutureDateFromRandom(date));
                experience.setWorkDescription(faker.lorem().sentence());
                experience.setRole(faker.job().position());

                if (faker.bool().bool()) {
                    experience.setCandidate(generate.generateRandomCandidate());
                }

                experience.setCandidate(candidateSave);

                experienceRepository.save(experience);
            }

            for (int j = 0; j < faker.random().nextInt(1, 5); j++) {
                Experience experience = new Experience();
                experience.setCompanyName(faker.company().name());
                experience.setFromDate(date);
                experience.setToDate(generate.generateFutureDateFromRandom(date));
                experience.setWorkDescription(faker.lorem().sentence());
                experience.setRole(faker.job().position());

                if (faker.bool().bool()) {
                    experience.setCandidate(generate.generateRandomCandidate());
                }

                experience.setCandidate(candidateSave);

                experienceRepository.save(experience);
            }

            for (int j = 0; j < faker.random().nextInt(1, 5); j++) {
                CandidateSkill candidateSkill = new CandidateSkill();
                candidateSkill.setCandidate(candidateSave);
                candidateSkill.setSkill(generate.getRandomSkill());
                candidateSkill.setSkillLevel(generate.getRandomSkillLevel());
                candidateSkill.setMoreInfos(faker.lorem().sentence());

                candidateSkillRepository.save(candidateSkill);
            }
        }
    }
}
