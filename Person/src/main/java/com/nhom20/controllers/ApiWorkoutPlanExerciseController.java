/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.Exercise;
import com.nhom20.pojo.UserAccount;
import com.nhom20.pojo.WorkoutPlan;
import com.nhom20.pojo.WorkoutPlanExercise;
import com.nhom20.services.ExerciseService;
import com.nhom20.services.UserService;
import com.nhom20.services.WorkoutPlanExerciseService;
import com.nhom20.services.WorkoutPlanService;
import java.security.Principal;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nguyenho
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiWorkoutPlanExerciseController {

    @Autowired
    private WorkoutPlanExerciseService workoutPlanExerciseService;

    @Autowired
    private WorkoutPlanService workoutPlanService;
    
    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/workout-plans-exercise/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("id") int id) {
        this.workoutPlanExerciseService.deleteWorkoutPlanExercise(id);
    }

    @GetMapping("/secure/workout-plan-exercise/{workoutPlanId}")
    public ResponseEntity<?> getByWorkoutPlanId(
            @PathVariable("workoutPlanId") int workoutPlanId,
            Principal user) {

        // Lấy userId từ token (nếu bạn lưu username trong token)
        String username = user.getName();  // username từ JWT
        UserAccount currentUser = userService.getUserByUsername(username);

        // Lấy kế hoạch tập
        WorkoutPlan plan = workoutPlanService.getWorkOutPlanById(workoutPlanId);
        if (plan == null) {
            return ResponseEntity.notFound().build();
        }

        // So sánh userId
        if (plan.getUserId().getId() != currentUser.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Bạn không có quyền truy cập kế hoạch này!"));
        }

        // Trả danh sách bài tập của kế hoạch
        return ResponseEntity.ok(workoutPlanExerciseService.getWorkoutPlanExercisesByWorkoutPlanId(workoutPlanId));
    }

    @PostMapping("/secure/workout-plan-exercise/add/{workoutPlanId}")
    public ResponseEntity<?> addWorkoutPlanExercise(
            @PathVariable("workoutPlanId") int workoutPlanId,
            @RequestBody WorkoutPlanExercise workoutPlanExercise,
            Principal user) {

        String username = user.getName();
        UserAccount currentUser = userService.getUserByUsername(username);

        WorkoutPlan plan = workoutPlanService.getWorkOutPlanById(workoutPlanId);
        if (plan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy kế hoạch tập luyện!");
        }

        if (plan.getUserId() == null || !plan.getUserId().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền truy cập kế hoạch này!");
        }

        // Lấy exercise từ DB
        Exercise exercise = exerciseService.getExerciseById(workoutPlanExercise.getExerciseId().getId());
        if (exercise == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bài tập!");
        }

        // Gán lại các thông tin
        workoutPlanExercise.setWorkoutPlanId(plan);
        workoutPlanExercise.setExerciseId(exercise); // Gán đối tượng đầy đủ

        WorkoutPlanExercise added = workoutPlanExerciseService.addOrUpdateWorkOutPlanExercise(workoutPlanExercise);

        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }

}
