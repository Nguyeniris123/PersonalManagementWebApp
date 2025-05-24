package com.nhom20.controllers;

import com.nhom20.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Added import
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin
public class StatisticsController {

    @Autowired
    private StatisticService statisticService;

    // Endpoint for user's own exercise statistics
    @GetMapping("/user/exercise")
    public ResponseEntity<Map<String, Object>> getUserExerciseStats(
            Principal principal,
            @RequestParam String period, // "week", "month", "year"
            @RequestParam int year,
            @RequestParam(required = false) Integer month, // Required for "week" and "month"
            @RequestParam(required = false) Integer week) { // Required for "week"
        String username = principal.getName();
        Map<String, Object> stats = statisticService.getUserExerciseStats(username, period, year, month, week);
        return ResponseEntity.ok(stats);
    }

    // Endpoint for user's own health progress
    @GetMapping("/user/health-progress")
    public ResponseEntity<Map<String, Object>> getUserHealthProgress(
            Principal principal,
            @RequestParam String period, // "week", "month", "year"
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer week) {
        String username = principal.getName();
        Map<String, Object> progress = statisticService.getUserHealthProgress(username, period, year, month, week);
        return ResponseEntity.ok(progress);
    }

    // Endpoint for trainer to view client's exercise statistics
    @GetMapping("/trainer/client/{clientId}/exercise")
    public ResponseEntity<Map<String, Object>> getClientExerciseStats(
            Principal principal, // Trainer
            @PathVariable int clientId,
            @RequestParam String period,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer week) {
        String trainerUsername = principal.getName();
        if (!statisticService.isTrainerOfClient(trainerUsername, clientId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Map<String, Object> stats = statisticService.getClientExerciseStats(clientId, period, year, month, week);
        return ResponseEntity.ok(stats);
    }

    // Endpoint for trainer to view client's health progress
    @GetMapping("/trainer/client/{clientId}/health-progress")
    public ResponseEntity<Map<String, Object>> getClientHealthProgress(
            Principal principal, // Trainer
            @PathVariable int clientId,
            @RequestParam String period,
            @RequestParam int year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer week) {
        String trainerUsername = principal.getName();
        if (!statisticService.isTrainerOfClient(trainerUsername, clientId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Map<String, Object> progress = statisticService.getClientHealthProgress(clientId, period, year, month, week);
        return ResponseEntity.ok(progress);
    }
}
