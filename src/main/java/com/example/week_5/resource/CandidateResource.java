package com.example.week_5.resource;

import com.example.week_5.models.Company;
import com.example.week_5.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/candidate")
public class CandidateResource {
    @Autowired
    private CandidateService candidateService;


    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User account = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Company> companies =candidateService.getJobsForFitCandidate(account.getUsername());
        List<Company> companyCanLear = candidateService.getJobsCanLearn(account.getUsername());

        model.addAttribute("companies", companies);
        model.addAttribute("companyCanLear", companyCanLear);

        return "candidate/dashboard";
    }
}
