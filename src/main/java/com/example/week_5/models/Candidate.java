package com.example.week_5.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @ToString.Exclude
    @OneToOne(optional = false)
    private Address address;

    @ToString.Exclude
    @OneToMany(mappedBy = "candidate", orphanRemoval = true)
    private Set<CandidateSkill> candidateSkills = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "candidate", orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();

    @ManyToOne
    private Account account;
}