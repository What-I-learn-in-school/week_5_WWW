package com.example.week_5.resource;

import com.example.week_5.dto.RegisterDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class AuthResource {

    @PostMapping
    public String register(@ModelAttribute("RegisterDTO") RegisterDTO register) {
        System.out.println(register);
    return "home";
    }


}
