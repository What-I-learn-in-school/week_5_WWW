package com.example.week_5.controllers;

import com.example.week_5.dto.RegisterDTO;
import com.example.week_5.models.Account;
import com.example.week_5.models.enums.Role;
import com.example.week_5.repositories.AccountRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(Principal principal) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            // Lấy Credentials
            Object credentials = authentication.getCredentials();
            System.out.println("Credentials: " + credentials);

            // Lấy Authorities
            authentication.getAuthorities().forEach(authority -> {
                System.out.println("Granted Authority: " + authority.getAuthority());
            });
        } else {
            System.out.println("No authentication available");
        }

    if (principal != null) {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
        if (role.equals("ROLE_CANDIDATE")) {
            System.out.println("redirecting to candidate dashboard");
            return "redirect:/candidate/dashboard";
        } else if (role.equals("ROLE_COMPANY")) {
            System.out.println("redirecting to company dashboard");
            return "redirect:/company/dashboard";
        }
    }
        return "/login";
    }

    @GetMapping("/logout")
    public String logout(Principal principal) {
        if (principal != null) {
        SecurityContextHolder.setContext(null);
        }
        return "login";
    }

    @GetMapping("/register")
    public String register(Principal principal, Model model) {
        if (principal != null) {
            System.out.println(principal.toString());
            SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(System.out::println);
            String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();


            if (role.equals("ROLE_CANDIDATE")) {
                return "/candidate/dashboard";
            } else if (role.equals("ROLE_COMPANY")) {
                return "/company/dashboard";
            }
        }

        model.addAttribute("register", new RegisterDTO());

        return "register";
    }
}
