/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.Reminder;
import com.nhom20.services.ReminderService;
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
public class ReminderController {
    @Autowired
    private ReminderService reminderService;

    @Autowired
    private UserService userService;

    @GetMapping("/reminder-view")
    public String addView(Model model) {
        model.addAttribute("reminder", new Reminder());
        model.addAttribute("users", userService.getAllUsers());
        return "addupdatereminder";
    }

    @PostMapping("/reminder/add")
    public String addReminder(@ModelAttribute("reminder") Reminder r, Model model) {
        try {
            reminderService.addOrUpdateReminder(r);
            return "redirect:/reminders";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage()); // gắn thông báo lỗi
            model.addAttribute("users", userService.getAllUsers());
            return "addupdatereminder"; // giữ lại form
        }
    }
    
    @GetMapping("/reminder/{id}")
    public String updateView(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("reminder", this.reminderService.getReminderById(id));
        model.addAttribute("users", userService.getAllUsers());
        return "addupdatereminder";
    }
}
