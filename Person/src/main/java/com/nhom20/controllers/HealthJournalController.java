/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.HealthJournal;
import com.nhom20.pojo.WorkoutPlan;
import com.nhom20.services.HealthJournalService;
import com.nhom20.services.UserService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author nguyenho
 */

@Controller
public class HealthJournalController {
    @Autowired
    private HealthJournalService healthJournalService;

    @Autowired
    private UserService userService;
    
    @GetMapping("/health-journal-view")
    public String addView(Model model) {
        model.addAttribute("healthJournal", new HealthJournal());
        model.addAttribute("users", userService.getAllUsers());
        return "addupdatehealthjournal";
    }
    
    // Định nghĩa cách chuyển đổi chuỗi ngày thành đối tượng Date
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
    
    @PostMapping("/health-journal/add")
    public String addHealthJournal(@ModelAttribute("healthJournal") HealthJournal hj, Model model) {
        try {
            healthJournalService.addOrUpdateHealthJournal(hj);
            return "redirect:/health-journal";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage()); // gắn thông báo lỗi
            model.addAttribute("users", userService.getAllUsers());
            return "addupdatehealthjournal"; // giữ lại form
        }
    }
    
    @GetMapping("/health-journal/{id}")
    public String updateView(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("healthJournal", this.healthJournalService.getHealthJournalById(id));
        model.addAttribute("users", userService.getAllUsers());
        return "addupdatehealthjournal";
    }
}
