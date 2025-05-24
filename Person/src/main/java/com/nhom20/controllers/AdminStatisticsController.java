package com.nhom20.controllers;

import com.nhom20.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminStatisticsController {
    private final UserService userService;

    public AdminStatisticsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/statistics")
    public String showStatisticsPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "statistics";
    }
} 