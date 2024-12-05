package com.example.week_5.resource;

import com.example.week_5.dto.RegisterDTO;
import com.example.week_5.models.Account;
import com.example.week_5.models.Address;
import com.example.week_5.models.Candidate;
import com.example.week_5.models.Company;
import com.example.week_5.models.enums.Role;
import com.example.week_5.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/register")
public class AuthResource {
    @Autowired
    private AuthService authService;

    @PostMapping
    public String register(@ModelAttribute("RegisterDTO") RegisterDTO register) {
        Role role = register.getRegisterFor().equals("Company") ? Role.COMPANY : Role.CANDIDATE;

        Account accountSave = authService.register(register.getUsername(), register.getPassword(), role);
        Address addressSave =  authService.registerAddress(register.getAddress());

        System.out.println("Registering: " + register.toString());

        if (role == Role.COMPANY) {
            Company company = register.getCompany();
            company.setAccount(accountSave);
            company.setAddress(addressSave);
            authService.registerCompany(company);
            return "login";
        } else {
            Candidate candidate = register.getCandidate();
            candidate.setAccount(accountSave);
            candidate.setAddress(addressSave);
            authService.registerCandidate(candidate);
            return "login";
        }
    }


}
