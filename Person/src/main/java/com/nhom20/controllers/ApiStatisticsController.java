package com.nhom20.controllers;

import com.nhom20.pojo.HealthProfile;
import com.nhom20.pojo.UserAccount;
import com.nhom20.services.HealthProfileService;
import com.nhom20.services.StatisticsService;
import com.nhom20.services.UserService;
import com.nhom20.services.UserTrainerService;
import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nguyenho
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiStatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTrainerService userTrainerService;
    
    @Autowired
    private HealthProfileService healthProfileService;

    @GetMapping("/secure/statistics")
    public ResponseEntity<?> getStatistics(
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Principal principal) {

        String username = principal.getName();
        UserAccount user = userService.getUserByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        int userId = user.getId();

        List<Object[]> weeklyStats = statisticsService.statsByWeek(userId, startDate, endDate);
        List<Object[]> monthlyStats = statisticsService.statsByMonth(userId, startDate, endDate);

        Map<String, Object> response = new HashMap<>();
        response.put("weeklyStats", weeklyStats);
        response.put("monthlyStats", monthlyStats);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/secure/trainer-statistics")
    public ResponseEntity<?> getStatistics(
            @RequestParam(value = "userId", required = false) int userId,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Principal principal) {

        String username = principal.getName();
        UserAccount currentUser = userService.getUserByUsername(username);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean connected = userTrainerService.existsAcceptedConnection(userId, currentUser.getId());
        if (!connected) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền xem thống kê của user này.");
        }

        List<Object[]> weeklyStats = statisticsService.statsByWeek(userId, startDate, endDate);
        List<Object[]> monthlyStats = statisticsService.statsByMonth(userId, startDate, endDate);

        Map<String, Object> result = new HashMap<>();
        result.put("weeklyStats", weeklyStats);
        result.put("monthlyStats", monthlyStats);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/secure/accepted-user")
    public ResponseEntity<?> getConnectedUsers(Principal principal) {
        String username = principal.getName();
        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Chưa đăng nhập");
        }

        UserAccount trainer = userService.getUserByUsername(username);
        if (trainer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Trainer không tồn tại");
        }

        List<UserAccount> connectedUsers = userTrainerService.getUsersAcceptedByTrainer(trainer.getId());

        return ResponseEntity.ok(connectedUsers);
    }

    @GetMapping("/secure/trainer-health-profile")
    public ResponseEntity<?> getHealthProfile(
            @RequestParam("userId") int userId,
            Principal principal) {

        String username = principal.getName();
        UserAccount currentUser = userService.getUserByUsername(username);

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean canView = userId == currentUser.getId()
                || userTrainerService.existsAcceptedConnection(userId, currentUser.getId());

        if (!canView) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền xem hồ sơ sức khỏe này.");
        }

        HealthProfile profile = healthProfileService.getHealthProfileByUserId(userId);

        if (profile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy hồ sơ sức khỏe.");
        }

        return ResponseEntity.ok(profile);
    }
}
