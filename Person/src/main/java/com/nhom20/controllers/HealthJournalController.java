package com.nhom20.controllers;

import com.nhom20.pojo.HealthJournal;
import com.nhom20.services.HealthJournalService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/health-journal")
public class HealthJournalController {
    
    @Autowired
    private HealthJournalService healthJournalService;
    
    // Hiển thị trang nhật ký
    @GetMapping
    public ModelAndView showJournalPage(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("health-journal");
    }
    
    // Lấy danh sách nhật ký của người dùng
    @GetMapping("/list")
    public ResponseEntity<?> getUserJournals(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
        }
        
        List<HealthJournal> journals = healthJournalService.getUserJournals(userId);
        return ResponseEntity.ok(journals);
    }
    
    // Lấy nhật ký theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable int id, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
        }
        
        HealthJournal journal = healthJournalService.getJournalById(id);
        if (journal == null || journal.getUserId() != userId) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(journal);
    }
    
    // Tạo nhật ký mới
    @PostMapping
    public ResponseEntity<?> createJournal(@RequestBody HealthJournal journal, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
        }
        
        journal.setUserId(userId);
        journal.setDate(LocalDateTime.now());
        
        HealthJournal savedJournal = healthJournalService.saveJournal(journal);
        return ResponseEntity.ok(savedJournal);
    }
    
    // Cập nhật nhật ký
    @PutMapping("/{id}")
    public ResponseEntity<?> updateJournal(@PathVariable int id, 
                                         @RequestBody HealthJournal journal,
                                         HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
        }
        
        HealthJournal existingJournal = healthJournalService.getJournalById(id);
        if (existingJournal == null || existingJournal.getUserId() != userId) {
            return ResponseEntity.notFound().build();
        }
        
        journal.setId(id);
        journal.setUserId(userId);
        journal.setDate(existingJournal.getDate());
        
        HealthJournal updatedJournal = healthJournalService.updateJournal(journal);
        return ResponseEntity.ok(updatedJournal);
    }
    
    // Xóa nhật ký
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable int id, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
        }
        
        HealthJournal journal = healthJournalService.getJournalById(id);
        if (journal == null || journal.getUserId() != userId) {
            return ResponseEntity.notFound().build();
        }
        
        boolean deleted = healthJournalService.deleteJournal(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Không thể xóa nhật ký");
        }
    }
    
    // Lấy thống kê tâm trạng và năng lượng
    @GetMapping("/stats")
    public ResponseEntity<?> getMoodAndEnergyStats(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
        }
        
        List<Object> stats = healthJournalService.getMoodAndEnergyStats(userId);
        return ResponseEntity.ok(stats);
    }
} 