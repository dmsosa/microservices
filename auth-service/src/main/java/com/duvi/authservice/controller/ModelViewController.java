package com.duvi.authservice.controller;

import com.duvi.authservice.model.User;
import com.duvi.authservice.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/src/main/templates")
public class ModelViewController {

    private UserService userService;
    public ModelViewController(UserService userService) {
        this.userService = userService;
    }
    @ModelAttribute("allUsers")
    public List<User> populateUsers() {
        return userService.findAllUsers();
    }
    @RequestMapping({"/", "/inde"})
    public String showPage() {

        return "inde";
    }
    @RequestMapping(value = "/inde", params = {"save"})
    public String saveUsers(final List<User> users, final BindingResult bindingResult, final ModelMap modelMap) {
        return "inde";
    }
}
