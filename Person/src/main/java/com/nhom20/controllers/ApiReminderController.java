/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.Reminder;
import com.nhom20.pojo.UserAccount;
import com.nhom20.services.ReminderService;
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
import org.springframework.web.bind.annotation.PutMapping;
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
public class ApiReminderController {

    @Autowired
    private ReminderService reminderService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/reminder/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("id") int id) {
        this.reminderService.deleteReminder(id);
    }

    @GetMapping("/secure/reminders")
    public ResponseEntity<?> getMyReminders(@RequestParam Map<String, String> params, Principal principal) {
        try {
            String username = principal.getName();
            UserAccount user = userService.getUserByUsername(username);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Người dùng không tồn tại");
            }

            List<Reminder> reminders = reminderService.getReminderByUserId(user.getId(), params);

            return ResponseEntity.ok(reminders);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi lấy danh sách nhắc nhở");
        }
    }

    @PostMapping("/secure/reminder/add")
    public ResponseEntity<?> createReminder(@RequestBody Reminder r, Principal principal) {
        try {
            String username = principal.getName();
            UserAccount user = this.userService.getUserByUsername(username);

            if (user == null) {
                return new ResponseEntity<>("Không tìm thấy người dùng!", HttpStatus.BAD_REQUEST);
            }

            r.setUserId(user);

            return new ResponseEntity<>(this.reminderService.addOrUpdateReminder(r), HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("Có lỗi xảy ra khi thêm nhắc nhở!");
        }
    }

    @GetMapping("/secure/reminders/{id}")
    public ResponseEntity<?> getReminderById(@PathVariable("id") int id, Principal principal) {
        try {
            String username = principal.getName();
            UserAccount user = userService.getUserByUsername(username);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Người dùng không tồn tại");
            }

            Reminder reminder = reminderService.getReminderById(id);

            if (reminder == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy nhắc nhở");
            }

            if (!reminder.getUserId().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền xem nhắc nhở này");
            }

            return ResponseEntity.ok(reminder);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi lấy nhắc nhở");
        }
    }

    @PutMapping("/secure/update-reminder/{id}")
    public ResponseEntity<?> updateReminder(@PathVariable("id") int id,
            @RequestBody Reminder updatedReminder,
            Principal principal) {
        try {
            String username = principal.getName();
            UserAccount user = userService.getUserByUsername(username);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không xác thực được người dùng!");
            }

            Reminder existing = reminderService.getReminderById(id);
            if (existing == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy nhắc nhở!");
            }

            if (!existing.getUserId().getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Không có quyền cập nhật nhắc nhở của người khác!");
            }

            existing.setTitle(updatedReminder.getTitle());
            existing.setReminderType(updatedReminder.getReminderType());
            existing.setTime(updatedReminder.getTime());
            existing.setIsActive(updatedReminder.getIsActive());

            Reminder updated = reminderService.addOrUpdateReminder(existing);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi: " + ex.getMessage());
        }
    }
}
