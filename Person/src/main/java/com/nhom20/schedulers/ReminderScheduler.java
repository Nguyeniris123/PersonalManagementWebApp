/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.schedulers;

import com.nhom20.pojo.Reminder;
import com.nhom20.pojo.UserAccount;
import com.nhom20.repositories.ReminderRepository;
import com.nhom20.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ReminderScheduler {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private EmailService emailService;

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    
    private static boolean hasRun = false;

    @Scheduled(cron = "0 * * * * *")
    public void sendReminders() {
        if (hasRun) {
            return;
        }
        hasRun = true;

        String nowStr = TIME_FORMAT.format(new Date());
        System.out.println("Looking for reminders at: " + nowStr);

        List<Reminder> reminders = reminderRepository.getAllActiveReminders();

        for (Reminder r : reminders) {
            String reminderTime = TIME_FORMAT.format(r.getTime());

            if (nowStr.equals(reminderTime)) {
                UserAccount u = r.getUserId();
                try {
                    emailService.sendSimpleMessage(
                            u.getEmail(),
                            "Reminder: " + r.getTitle(),
                            "Hi " + u.getUsername() + ", this is your reminder From Person WebApp: " + r.getReminderType()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        hasRun = false;
    }
}
