package com.example.week_5.services;

import com.example.week_5.models.Account;
import com.example.week_5.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

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

    public Account register (Account account) {
        return accountRepository.save(account);
    }

    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username).orElse(null);
    }
}
