/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom20.services;

import com.nhom20.pojo.Reminder;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguyenho
 */
public interface ReminderService {
    List<Reminder> getReminder(Map<String, String> params);
    Reminder getReminderById(int id);
    Reminder addOrUpdateReminder(Reminder reminder);
    boolean deleteReminder(int id);
    List<Reminder> getReminderByUserId(int userId, Map<String, String> params);
}
