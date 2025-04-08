/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.UserAccount;
import com.nhom20.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nguyenho
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/user-add-view")
    public String addView(Model model) {
        model.addAttribute("users", new UserAccount());
        return "adduser";
    }

    // Xử lý submit form
    @PostMapping(path = "/users/add", 
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
                 produces = MediaType.TEXT_HTML_VALUE)
    public String addUser(@RequestParam Map<String, String> params,
                          @RequestParam("avatar") MultipartFile avatar,
                          Model model) {

        try {
            userService.addUser(params, avatar);
            model.addAttribute("message", "Thêm người dùng thành công!");
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi thêm người dùng.");
            e.printStackTrace();
        }

        return "redirect:/users"; // hoặc return "add-user" nếu muốn hiển thị lại form
    }

}
