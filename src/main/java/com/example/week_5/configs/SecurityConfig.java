package com.example.week_5.configs;

import com.example.week_5.models.Account;
import com.example.week_5.models.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {
    private static final String COMPANY = "COMPANY";
    private static final String CANDIDATE = "CANDIDATE";

    @Autowired
    private DataSource dataSource;

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT username, password FROM Account WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username, role FROM Account WHERE username = ?").
                passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        // Phân quyền endpoint theo vai trò
                        .requestMatchers("/company/**").hasRole("COMPANY")
                        .requestMatchers("/candidate/**").hasRole("CANDIDATE")
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                ).formLogin( form -> form.loginPage("/login").defaultSuccessUrl("/login")
                        .permitAll()).
                logout(LogoutConfigurer::permitAll).
                httpBasic(Customizer.withDefaults());


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123"));
    }
}