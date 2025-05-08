package com.nhom20.services;

import com.nhom20.pojo.NotificationSettings;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificationService {
    private static NotificationService instance;
    private final Map<Integer, NotificationSettings> userSettings;
    private final ScheduledExecutorService scheduler;

    private NotificationService() {
        this.userSettings = new HashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(3);
    }

    public static synchronized NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }

    public void updateUserSettings(int userId, NotificationSettings settings) {
        userSettings.put(userId, settings);
        scheduleReminders(userId, settings);
    }

    public NotificationSettings getUserSettings(int userId) {
        return userSettings.getOrDefault(userId, new NotificationSettings());
    }

    private void scheduleReminders(int userId, NotificationSettings settings) {
        // Cancel existing reminders
        scheduler.shutdownNow();
        
        // Schedule new reminders
        if (settings.isWaterReminderEnabled()) {
            scheduler.scheduleAtFixedRate(
                () -> sendWaterReminder(userId),
                0,
                settings.getWaterReminderInterval(),
                TimeUnit.MINUTES
            );
        }

        if (settings.isExerciseReminderEnabled()) {
            scheduler.scheduleAtFixedRate(
                () -> sendExerciseReminder(userId),
                0,
                settings.getExerciseReminderInterval(),
                TimeUnit.MINUTES
            );
        }

        if (settings.isRestReminderEnabled()) {
            scheduler.scheduleAtFixedRate(
                () -> sendRestReminder(userId),
                0,
                settings.getRestReminderInterval(),
                TimeUnit.MINUTES
            );
        }
    }

    private void sendWaterReminder(int userId) {
        // TODO: Implement actual notification sending logic
        System.out.println("Sending water reminder to user " + userId);
    }

    private void sendExerciseReminder(int userId) {
        // TODO: Implement actual notification sending logic
        System.out.println("Sending exercise reminder to user " + userId);
    }

    private void sendRestReminder(int userId) {
        // TODO: Implement actual notification sending logic
        System.out.println("Sending rest reminder to user " + userId);
    }
} 