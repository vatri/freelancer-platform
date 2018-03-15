package net.vatri.freelanceplatform.controllers;


import net.vatri.freelanceplatform.models.User;
import net.vatri.freelanceplatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("userValidator")
    Validator userValidator;

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login/login";
    }

    @GetMapping("/register")
    public String register(){
        return "login/register";
    }

    @PostMapping("/register")
    public ModelAndView doRegister(@Valid User user, BindingResult bindingResult, Model model){

        userValidator.validate(user, bindingResult);

        if( bindingResult.hasErrors() ){

            StringBuilder sb = new StringBuilder();

            bindingResult.getAllErrors().forEach(e -> {


                sb.append(StringUtils.capitalize(e.getDefaultMessage()));
                sb.append("<br>");
            });

            model.addAttribute("error", sb);

        } else {
            userService.save(user);
            return new ModelAndView("redirect:/login");
        }
        model.addAttribute("user", user);
        return new ModelAndView("login/register", model.asMap());
    }
}
