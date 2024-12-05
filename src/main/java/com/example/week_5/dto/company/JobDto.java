package com.example.week_5.dto.company;

import com.example.week_5.models.enums.SkillLevel;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.example.week_5.models.Job}
 */
@Data
public class JobDto implements Serializable {
    String jobDescription;
    String jobName;
    String moreInfos;
    String skillId;
    String skillLevel;
}