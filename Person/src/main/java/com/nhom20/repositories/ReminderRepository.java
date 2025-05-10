/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom20.repositories;

import com.nhom20.pojo.Reminder;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguyenho
 */
public interface ReminderRepository {
    List<Reminder> getReminder(Map<String, String> params);
    Reminder getReminderById(int id);
    Reminder addOrUpdateReminder(Reminder reminder);
    void deleteReminder(int id);
    List<Reminder> getReminderByUserId(int userId);
}
