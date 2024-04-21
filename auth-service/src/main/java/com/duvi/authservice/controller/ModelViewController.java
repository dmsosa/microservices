package com.duvi.authservice.controller;

import com.duvi.authservice.model.User;
import com.duvi.authservice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
