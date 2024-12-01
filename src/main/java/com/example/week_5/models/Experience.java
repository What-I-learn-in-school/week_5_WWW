package com.example.week_5.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "experience")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @Column(nullable = false)
    private String role;

    @Column(name = "company_name", nullable = false, length = 120)
    private String companyName;

    @Column(name = "work_description", nullable = false, length = 400)
    private  String workDescription;

    @ToString.Exclude
    @ManyToOne
    private Candidate candidate;

}