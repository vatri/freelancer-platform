package net.vatri.freelanceplatform.frontend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.vatri.freelanceplatform.models.Profile;
import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserService userService;

                    @Autowired
                    ObjectMapper objectMapper;

    @RequestMapping(value = { "", "/{id}" })
    public String viewProfile(@PathVariable("id") Optional<Long> profileIdParam , Model model){

        // If profile not provided in URL, fetch current logged user
        Long profileId = profileIdParam.isPresent() ? profileIdParam.get() : 0L;

        Profile profile;
        User user;

        if(profileId < 1) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails == false) {
                return "redirect:/";
            }
            String username = ((UserDetails) principal).getUsername();

            user = userService.getByEmail(username);
        } else {
            user = userService.get(profileId);
        }

        if(user == null){
            return "redirect:/";
        }

        model.addAttribute("user", user);
        model.addAttribute("profile", user.getProfile());

        return "frontend/profile/view";
    }
}
