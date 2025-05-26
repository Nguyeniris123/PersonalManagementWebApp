/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.UserAccount;
import com.nhom20.pojo.UserTrainer;
import com.nhom20.services.UserService;
import com.nhom20.services.UserTrainerService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author nguyenho
 */
@Controller
public class UserTrainerController {

    @Autowired
    private UserTrainerService userTrainerService;

    @Autowired
    private UserService userService;

    @GetMapping("/user-trainer-view")
    public String addUserTrainerView(Model model) {
        List<UserAccount> users = userService.findByRole("ROLE_USER");
        List<UserAccount> trainers = userService.findByRole("ROLE_TRAINER");
        model.addAttribute("userTrainer", new UserTrainer());
        model.addAttribute("users", users);
        model.addAttribute("trainers", trainers);
        return "addupdateusertrainer";
    }

    @PostMapping("/user-trainer/add")
    public String addUserTrainer(@ModelAttribute("userTrainer") UserTrainer userTrainer,
            Model model, @RequestParam Map<String, String> params) {
        try {
            userTrainer.setCreatedAt(new Date());
            userTrainerService.addOrUpdateUserTrainer(userTrainer);
            return "redirect:/user-trainer";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "addupdateusertrainer";
        }
    }
    
    @GetMapping("/user-trainer/{id}")
    public String updateView(Model model, @PathVariable(value = "id") int id, @RequestParam Map<String, String> params) {
        model.addAttribute("userTrainer", this.userTrainerService.getUserTrainerById(id));
        List<UserAccount> users = userService.findByRole("ROLE_USER");
        List<UserAccount> trainers = userService.findByRole("ROLE_TRAINER");
        model.addAttribute("users", users);
        model.addAttribute("trainers", trainers);
        return "addupdateusertrainer";
    }
}
