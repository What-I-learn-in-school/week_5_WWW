package com.example.week_5.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString

@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comp_id", nullable = false)
    private Long id;

    @Column(name = "about", length = 2000)
    private String about;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "web_url")
    private String webUrl;

    @ToString.Exclude
    @OneToOne(optional = false)
    @JoinColumn(name = "address", nullable = false)
    private Address address;

    @ToString.Exclude
    @OneToMany(mappedBy = "company")
    private Set<Job> jobs = new LinkedHashSet<>();

    @ManyToOne
    private Account account;

}