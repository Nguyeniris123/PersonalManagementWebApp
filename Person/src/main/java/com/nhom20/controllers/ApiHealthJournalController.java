/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.HealthJournal;
import com.nhom20.pojo.UserAccount;
import com.nhom20.services.HealthJournalService;
import com.nhom20.services.UserService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nguyenho
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiHealthJournalController {

    @Autowired
    private HealthJournalService healthJournalService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/health-journal/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("id") int id) {
        this.healthJournalService.deleteHealthJournal(id);
    }

    @GetMapping("/secure/health-journals")
    public ResponseEntity<?> getMyHealthJournals(@RequestParam Map<String, String> params, Principal principal) {
        try {
            // Lấy username từ token
            String username = principal.getName();
            UserAccount user = userService.getUserByUsername(username);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Người dùng không tồn tại");
            }

            // Gọi service: truyền userId + params (gồm kw, page, orderBy...)
            List<HealthJournal> journals = healthJournalService.getHealthJournalByUserId(user.getId(), params);

            return ResponseEntity.ok(journals);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi lấy nhật ký sức khỏe");
        }
    }

    @GetMapping("/secure/health-journals/{id}")
    public ResponseEntity<?> getHealthJournalById(@PathVariable("id") int id, Principal principal) {
        try {
            // Lấy username từ token đăng nhập
            String username = principal.getName();
            UserAccount user = userService.getUserByUsername(username);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Người dùng không tồn tại");
            }

            // Tìm nhật ký theo ID
            HealthJournal journal = healthJournalService.getHealthJournalById(id);

            if (journal == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy nhật ký");
            }

            // Kiểm tra quyền sở hữu
            if (!journal.getUserId().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền xem nhật ký này");
            }

            return ResponseEntity.ok(journal);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi lấy nhật ký");
        }
    }
    
    @PostMapping("/secure/health-journal/add")
    public ResponseEntity<?> createHealthJournal(@RequestBody HealthJournal hj, Principal principal) {
        try {
            String username = principal.getName(); // Lấy username từ người dùng đang đăng nhập
            UserAccount user = this.userService.getUserByUsername(username); // Tìm UserAccount tương ứng

            if (user == null) {
                return new ResponseEntity<>("Không tìm thấy người dùng!", HttpStatus.BAD_REQUEST);
            }

            hj.setUserId(user); // Gán user vào

            return new ResponseEntity<>(this.healthJournalService.addOrUpdateHealthJournal(hj), HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace(); // Log lỗi
            return ResponseEntity.badRequest().body("Có lỗi xảy ra khi thêm kế hoạch!");
        }
    }

}
