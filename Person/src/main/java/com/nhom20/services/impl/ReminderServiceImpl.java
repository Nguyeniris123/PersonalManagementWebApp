/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.services.impl;

import com.nhom20.pojo.Reminder;
import com.nhom20.repositories.ReminderRepository;
import com.nhom20.services.ReminderService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguyenho
 */
@Service
public class ReminderServiceImpl implements ReminderService{

    @Autowired
    private ReminderRepository reminderRepository;
    
    @Override
    public List<Reminder> getReminder(Map<String, String> params) {
        return reminderRepository.getReminder(params);
    }

    @Override
    public Reminder getReminderById(int id) {
        return reminderRepository.getReminderById(id);
    }

    @Override
    public Reminder addOrUpdateReminder(Reminder reminder) {
        return reminderRepository.addOrUpdateReminder(reminder);
    }

    @Override
    public boolean deleteReminder(int id) {
        try {
            this.reminderRepository.deleteReminder(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Reminder> getReminderByUserId(int userId, Map<String, String> params) {
        return this.reminderRepository.getReminderByUserId(userId, params);
    }
}
