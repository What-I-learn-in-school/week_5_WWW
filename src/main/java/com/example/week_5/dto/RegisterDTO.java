package com.example.week_5.dto;

import com.example.week_5.models.Address;
import com.example.week_5.models.Candidate;
import com.example.week_5.models.Company;

import java.time.LocalDate;

public class RegisterDTO {
    private String username;
    private String password;
    private String registerFor;
    private Address address;

    private Candidate candidate;

    private Company company;

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", registerFor='" + registerFor + '\'' +
                ", address=" + address +
                ", candidate=" + candidate +
                ", company=" + company +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegisterFor() {
        return registerFor;
    }

    public void setRegisterFor(String registerFor) {
        this.registerFor = registerFor;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
