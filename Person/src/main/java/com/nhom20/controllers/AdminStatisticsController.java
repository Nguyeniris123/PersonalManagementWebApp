package com.nhom20.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminStatisticsController {
    
    @GetMapping("/statistics")
    public String showStatisticsPage() {
        return "admin/statistics";
    }
} 