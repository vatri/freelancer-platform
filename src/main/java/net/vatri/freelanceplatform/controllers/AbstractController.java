package net.vatri.freelanceplatform.controllers;

import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class AbstractController {

    @Autowired
    UserService userService;
    /**
     * Get logged user
     *
     * @return net.vatri.freelanceplatform.models.User
     **/
    protected User getCurrentUser(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails == false) {
            return null;
        }
        String username = ((UserDetails) principal).getUsername();

        return  userService.getByEmail(username);
    }
}
