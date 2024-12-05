package com.example.week_5.services;

import com.example.week_5.models.Account;
import com.example.week_5.models.Address;
import com.example.week_5.models.Candidate;
import com.example.week_5.models.Company;
import com.example.week_5.models.enums.Role;
import com.example.week_5.repositories.AccountRepository;
import com.example.week_5.repositories.AddressRepository;
import com.example.week_5.repositories.CandidateRepository;
import com.example.week_5.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Gắn vai trò vào Spring Security
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + account.getRole()));

        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                authorities
        );
    }

    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username).orElse(null);
    }

    public Account register (String username, String password, Role role) {
        Account newAccount = new Account();
        newAccount.setUsername(username);
        newAccount.setPassword(passwordEncoder.encode(password));
        newAccount.setRole(role);
        return accountRepository.save(newAccount);
    }

    public Address registerAddress(Address address){
        return addressRepository.save(address);
    }

    public Company registerCompany(Company company){
        return companyRepository.save(company);
    }

    public Candidate registerCandidate(Candidate candidate){
        return candidateRepository.save(candidate);
    }



}
