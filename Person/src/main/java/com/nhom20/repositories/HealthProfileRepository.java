/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom20.repositories;

import com.nhom20.pojo.HealthProfile;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguyenho
 */
public interface HealthProfileRepository {
    List<HealthProfile> getHealthProfiles(Map<String, String> params);
    HealthProfile getHealthProfileById(int id);
    HealthProfile getHealthProfileByUserId(int userId);
    HealthProfile addOrUpdateHealthProfile(HealthProfile healthProfile);
    void deleteHealthProfile(int id);
    
}
