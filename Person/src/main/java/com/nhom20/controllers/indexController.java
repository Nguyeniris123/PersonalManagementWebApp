/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.controllers;

import com.nhom20.pojo.UserAccount;
import com.nhom20.services.EmailService;
import com.nhom20.services.ExerciseService;
import com.nhom20.services.HealthJournalService;
import com.nhom20.services.HealthProfileService;
import com.nhom20.services.ReminderService;
import com.nhom20.services.UserService;
import com.nhom20.services.UserTrainerService;
import com.nhom20.services.WorkoutPlanExerciseService;
import com.nhom20.services.WorkoutPlanService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author admin
 */
@Controller
public class indexController {

    @Autowired
    private HealthProfileService healthProfileService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkoutPlanService workoutPlanService;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private WorkoutPlanExerciseService workoutPlanExerciseService;

    @Autowired
    private UserTrainerService userTrainerService;

    @Autowired
    private HealthJournalService healthJournalService;

    @Autowired
    private ReminderService reminderService;

    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("healthProfiles", this.healthProfileService.getHealthProfiles(params));
        return "index";
    }

    @GetMapping("/users")
    public String userList(@RequestParam(name = "username", required = false) String username, Model model) {
        List<UserAccount> users;
        if (username != null && !username.isEmpty()) {
            users = userService.searchUsersByUsername(username);
        } else {
            users = userService.getAllUsers();
        }
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/workout-plans")
    public String workoutPlanList(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("workoutPlans", this.workoutPlanService.getWorkOutPlan(params));
        return "workoutplans";
    }

    @GetMapping("/exercises")
    public String exerciseList(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("exercises", this.exerciseService.getExercise(params));
        return "exercises";
    }

    @GetMapping("/workout-plans-exercise")
    public String workoutPlanExerciseList(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("workoutPlanExercises", this.workoutPlanExerciseService.getWorkOutPlanExercise(params));
        return "workoutplanexercise";
    }

    @GetMapping("/user-trainer")
    public String userTrainerList(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("userTrainers", this.userTrainerService.getUserTrainer(params));
        return "usertrainer";
    }

    @GetMapping("/health-journal")
    public String healthJournalList(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("healthJournals", this.healthJournalService.getHealthJournal(params));
        return "healthjournals";
    }

    @GetMapping("/reminders")
    public String reminderList(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("reminders", this.reminderService.getReminder(params));
        return "reminder";
    }

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String testSendEmail() {
        emailService.sendSimpleMessage("2251050048nguyen@ou.edu.vn", "Test Subject", "Hello from Spring MVC!");
        return "redirect:/"; // hoặc redirect
    }
}
