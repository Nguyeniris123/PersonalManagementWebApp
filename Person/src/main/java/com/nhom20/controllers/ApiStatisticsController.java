package com.nhom20.controllers;

import com.nhom20.pojo.UserAccount;
import com.nhom20.services.StatisticsService;
import com.nhom20.services.UserService;
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
}
