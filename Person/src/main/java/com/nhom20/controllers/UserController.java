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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

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
    @PostMapping(path = "/users/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    public String addUser(@RequestParam Map<String, String> params,
            @RequestParam("avatar") MultipartFile avatar,
            Model model) {

        try {
            userService.addUser(params, avatar);
            return "redirect:/users";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage()); // gắn thông báo lỗi
            return "adduser"; // giữ lại form
        }
    }

    // Hiển thị trang sửa thông tin người dùng
    @GetMapping("/users/{id}")
    public String updateView(@PathVariable("id") int id, Model model) {
        UserAccount user = userService.getUserById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "updateuser"; // Trang cập nhật thông tin người dùng
        }
        return "redirect:/users"; // Nếu không tìm thấy người dùng, quay lại trang danh sách
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") int id,
            @RequestParam Map<String, String> params,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar, Model model) {
        try {
            userService.updateUser(params, avatar, id);
            return "redirect:/users"; // Chuyển hướng về danh sách người dùng sau khi cập nhật thành công
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage()); // gắn thông báo lỗi
            return "adduser"; // giữ lại form
        }
    }

    // Endpoint for trainer to get their clients
    @GetMapping("/secure/clients-for-trainer")
    public ResponseEntity<?> getClientsForTrainer(Principal principal) {
        String trainerUsername = principal.getName();
        return ResponseEntity.ok(userService.getClientsForTrainer(trainerUsername));
    }
}
