package com.nhom20.services;

import java.util.Map;

public interface StatisticService {
    // Methods for user's own statistics
    Map<String, Object> getUserExerciseStats(String username, String period, int year, Integer month, Integer week);

    Map<String, Object> getUserHealthProgress(String username, String period, int year, Integer month, Integer week);

    // Methods for trainer to view client's statistics
    Map<String, Object> getClientExerciseStats(int clientId, String period, int year, Integer month, Integer week);

    Map<String, Object> getClientHealthProgress(int clientId, String period, int year, Integer month, Integer week);

    boolean isTrainerOfClient(String trainerUsername, int clientId);

}
