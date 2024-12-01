package com.example.week_5.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id", nullable = false)
    private Long id;

    @Column(name = "job_description", nullable = false, length = 2000)
    private String jobDescription;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "job")
    private Set<JobSkill> jobSkills = new LinkedHashSet<>();

}