package com.example.week_5.seeders.seed;

import com.example.week_5.models.Account;
import com.example.week_5.models.Address;
import com.example.week_5.models.Company;
import com.example.week_5.models.enums.Role;
import com.example.week_5.repositories.AccountRepository;
import com.example.week_5.repositories.AddressRepository;
import com.example.week_5.repositories.CompanyRepository;

import net.datafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CompanySeeder {
    @Autowired private CompanyRepository companyRepository;
    @Autowired private AddressRepository addressRepository;
    @Autowired private AccountRepository accountRepository;

    public void seed(int number) {
        Faker faker = new Faker();

        for (int i = 0; i < number; i++) {
            Account  account  = new Account();
            account.setUsername(faker.name().username());
            account.setPassword(new BCryptPasswordEncoder().encode("123"));
            account.setRole(Role.COMPANY);

            Account accountSave = accountRepository.save(account);

            Address address = new Address();
            address.setCity(faker.address().city());
            address.setCountry(faker.random().nextInt(1, 100).shortValue());
            address.setNumber(faker.address().buildingNumber());
            address.setStreet(faker.address().streetName());
            address.setZipcode(faker.address().zipCode());

            Address addressSave = addressRepository.save(address);

            Company company = new Company();
            company.setAbout(faker.lorem().sentence());
            company.setEmail(faker.internet().emailAddress());
            company.setCompanyName(faker.company().name());
            company.setPhone(faker.phoneNumber().cellPhone());
            company.setWebUrl(faker.internet().url());

            company.setAddress(addressSave);
            company.setAccount(accountSave);

            companyRepository.save(company);
        }
    }
}
