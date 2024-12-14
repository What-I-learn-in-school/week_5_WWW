package com.example.week_5.resource;

import com.example.week_5.dto.company.JobDto;
import com.example.week_5.models.Job;
import com.example.week_5.models.Skill;
import com.example.week_5.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/company")
public class CompanyResource {
    @Autowired
    private CompanyService companyService;


    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User account = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Job> jobs = companyService.getJobsForCompany(account.getUsername());
        List<Map<String, Object>> companyCanLear = companyService.getJobsForFitCandidate(account.getUsername());


        model.addAttribute("jobs", jobs);
        model.addAttribute("companyCanLear", companyCanLear);

        return "company/dashboard";
    }

    @GetMapping("/create-job")
    public String createJob(Model model) {
        List<Skill> skills = companyService.getSkill();
        JobDto jobDto = new JobDto();

        model.addAttribute("skills", skills);
        model.addAttribute("jobDto", jobDto);

        return "company/createJob";
    }

    @PostMapping("/create-job/create")
    public String createJob(@ModelAttribute JobDto jobDto) {
        User account = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    companyService.createJob(account.getUsername() ,jobDto);
        return "redirect:/company/dashboard";
    }

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam String email) {
        companyService.sendEmail(email);
        return "redirect:/company/dashboard";
    }
}
