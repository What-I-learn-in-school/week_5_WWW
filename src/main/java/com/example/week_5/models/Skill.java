package com.example.week_5.models;

import com.example.week_5.models.enums.SkillType;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "skill_description", nullable = false)
    private String skillDescription;

    @Column(name = "skill_name", nullable = false)
    private String skillName;

    @ToString.Exclude
    @OneToMany(mappedBy = "skill")
    private Set<CandidateSkill> candidateSkills = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "skill")
    private Set<JobSkill> jobSkills = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_type", nullable = false)
    private SkillType skillType;

}