package net.vatri.freelanceplatform.frontend.controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@EnableAutoConfiguration
@Controller
@RequestMapping("/frontend")
public class HomeController {
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("name","Boris");
        return "frontend/home";
    }
}
