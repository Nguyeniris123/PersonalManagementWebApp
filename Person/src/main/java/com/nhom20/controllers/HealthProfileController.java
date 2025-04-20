/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.HealthProfile;
import com.nhom20.services.HealthProfileService;
import com.nhom20.services.UserService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author nguyenho
 */
@Controller
public class HealthProfileController {

    @Autowired
    private HealthProfileService healthProfileService;

    @Autowired
    private UserService userService;

    @GetMapping("/health-profiles")
    public String addView(Model model) {
        model.addAttribute("healthProfile", new HealthProfile());
        model.addAttribute("users", userService.getAllUsers());
        return "healthprofiles";
    }
    
//    // Sử dụng InitBinder để chuyển đổi String thành UserAccount
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(UserAccount.class, new PropertyEditorSupport() {
//            @Override
//            public void setAsText(String text) throws IllegalArgumentException {
//                UserAccount user = userService.getUserById(Integer.parseInt(text));
//                setValue(user);
//            }
//        });
//    }
    
    @PostMapping("/health-profile/add")
    public String addHealthProfile(@ModelAttribute("healthProfile") HealthProfile hp, Model model) {
        try {
            hp.setUpdatedAt(new Date()); // Đặt thời gian hiện tại
            healthProfileService.saveHealthProfile(hp);
            return "redirect:/";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage()); // gắn thông báo lỗi
            model.addAttribute("users", userService.getAllUsers());
            return "healthprofiles"; // giữ lại form
        }
    }

    
    @GetMapping("/health-profiles/{id}")
    public String updateView(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("healthProfile", this.healthProfileService.getHealthProfileById(id));
        model.addAttribute("users", userService.getAllUsers());
        return "healthprofiles";
    }
}


