/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.UserAccount;
import com.nhom20.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author nguyenho
 */
@Controller
public class UserController {

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }
    
    @GetMapping("/user-add-view")
    public String addView(Model model) {
        model.addAttribute("users", new UserAccount());
        return "adduser";
    }

}
