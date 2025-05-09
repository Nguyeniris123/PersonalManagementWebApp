/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.services.impl;

import com.nhom20.pojo.HealthJournal;
import com.nhom20.repositories.HealthJournalRepository;
import com.nhom20.services.HealthJournalService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguyenho
 */
@Service
public class HealthJournalServiceImpl implements HealthJournalService {

    @Autowired
    private HealthJournalRepository healthJournalRepository;

    @Override
    public List<HealthJournal> getHealthJournal(Map<String, String> params) {
        return healthJournalRepository.getHealthJournal(params);
    }

    @Override
    public HealthJournal getHealthJournalById(int id) {
        return healthJournalRepository.getHealthJournalById(id);
    }

    @Override
    public HealthJournal addOrUpdateHealthJournal(HealthJournal healthJournal) {
        return healthJournalRepository.addOrUpdateHealthJournal(healthJournal);

    }

    @Override
    public boolean deleteHealthJournal(int id) {
        try {
            this.healthJournalRepository.deleteHealthJournal(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<HealthJournal> getHealthJournalByUserId(int userId) {
        return this.healthJournalRepository.getHealthJournalByUserId(userId);
    }
}
