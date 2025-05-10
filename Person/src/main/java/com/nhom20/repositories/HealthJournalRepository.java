/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom20.repositories;

import com.nhom20.pojo.HealthJournal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguyenho
 */

public interface HealthJournalRepository {
    List<HealthJournal> getHealthJournal(Map<String, String> params);
    HealthJournal getHealthJournalById(int id);
    HealthJournal addOrUpdateHealthJournal(HealthJournal healthJournal);
    void deleteHealthJournal(int id);
    List<HealthJournal> getHealthJournalByUserId(int userId, Map<String, String> params);
}
