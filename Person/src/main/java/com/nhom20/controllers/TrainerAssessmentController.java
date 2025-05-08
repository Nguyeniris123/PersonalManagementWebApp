package com.nhom20.controllers;

import com.nhom20.pojo.TrainerAssessment;
import com.nhom20.services.TrainerAssessmentService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/trainer/assessment")
public class TrainerAssessmentController {
    
    @Autowired
    private TrainerAssessmentService trainerAssessmentService;
    
    // Hiển thị trang đánh giá
    @GetMapping
    public ModelAndView showAssessmentPage(HttpSession session) {
        Integer trainerId = (Integer) session.getAttribute("userId");
        if (trainerId == null) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("trainer-assessment");
    }
    
    // Lấy danh sách khách hàng của huấn luyện viên
    @GetMapping("/clients")
    public ResponseEntity<?> getTrainerClients(HttpSession session) {
        Integer trainerId = (Integer) session.getAttribute("userId");
        if (trainerId == null) {
            return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
        }
        
        List<TrainerAssessment> clients = trainerAssessmentService.getTrainerClients(trainerId);
        return ResponseEntity.ok(clients);
    }
    
    // Lấy đánh giá của một khách hàng
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getClientAssessments(@PathVariable int clientId, HttpSession session) {
        Integer trainerId = (Integer) session.getAttribute("userId");
        if (trainerId == null) {
            return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
        }
        
        List<TrainerAssessment> assessments = trainerAssessmentService.getClientAssessments(clientId);
        return ResponseEntity.ok(assessments);
    }
    
    // Tạo đánh giá mới
    @PostMapping
    public ResponseEntity<?> createAssessment(@RequestBody TrainerAssessment assessment, HttpSession session) {
        Integer trainerId = (Integer) session.getAttribute("userId");
        if (trainerId == null) {
            return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
        }
        
        assessment.setTrainerId(trainerId);
        TrainerAssessment savedAssessment = trainerAssessmentService.saveAssessment(assessment);
        return ResponseEntity.ok(savedAssessment);
    }
    
    // Cập nhật đánh giá
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssessment(@PathVariable int id, 
                                            @RequestBody TrainerAssessment assessment,
                                            HttpSession session) {
        Integer trainerId = (Integer) session.getAttribute("userId");
        if (trainerId == null) {
            return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
        }
        
        TrainerAssessment existingAssessment = trainerAssessmentService.getAssessmentById(id);
        if (existingAssessment == null || existingAssessment.getTrainerId() != trainerId) {
            return ResponseEntity.notFound().build();
        }
        
        assessment.setId(id);
        assessment.setTrainerId(trainerId);
        assessment.setClientId(existingAssessment.getClientId());
        assessment.setAssessmentDate(existingAssessment.getAssessmentDate());
        
        TrainerAssessment updatedAssessment = trainerAssessmentService.updateAssessment(assessment);
        return ResponseEntity.ok(updatedAssessment);
    }
    
    // Xóa đánh giá
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssessment(@PathVariable int id, HttpSession session) {
        Integer trainerId = (Integer) session.getAttribute("userId");
        if (trainerId == null) {
            return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
        }
        
        TrainerAssessment assessment = trainerAssessmentService.getAssessmentById(id);
        if (assessment == null || assessment.getTrainerId() != trainerId) {
            return ResponseEntity.notFound().build();
        }
        
        boolean deleted = trainerAssessmentService.deleteAssessment(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Không thể xóa đánh giá");
        }
    }
    
    // Lấy thống kê tiến độ của khách hàng
    @GetMapping("/stats/{clientId}")
    public ResponseEntity<?> getClientProgressStats(@PathVariable int clientId, HttpSession session) {
        Integer trainerId = (Integer) session.getAttribute("userId");
        if (trainerId == null) {
            return ResponseEntity.badRequest().body("Vui lòng đăng nhập");
        }
        
        Map<String, Object> stats = trainerAssessmentService.getClientProgressStats(clientId);
        return ResponseEntity.ok(stats);
    }
} 