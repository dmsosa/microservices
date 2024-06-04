package com.duvi.authservice.controller;

import com.duvi.authservice.model.User;
import com.duvi.authservice.model.exception.UserExistsException;
import com.duvi.authservice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ModelViewController {

    private UserService userService;
    public ModelViewController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(method = { RequestMethod.POST }, path = {"/register"}, consumes = "application/x-www-form-urlencoded")
    public String registerUser(User user, BindingResult bindingResult, Model model) throws UserExistsException {
        try {
            userService.saveUser(user);
            model.addAttribute("user", user);
        } catch (UserExistsException uee) {
            model.addAttribute("errorMessage", uee.getMessage());
        }
        return "login";
    }

}
