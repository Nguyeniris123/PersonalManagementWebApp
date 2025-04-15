    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.services.impl;

import com.nhom20.pojo.HealthProfile;
import com.nhom20.repositories.HealthProfileRepository;
import com.nhom20.services.HealthProfileService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguyenho
 */
@Service
public class HealthProfileServiceImpl implements HealthProfileService {

    @Autowired
    private HealthProfileRepository healthProfileRepository;

    @Override
    public List<HealthProfile> getHealthProfiles(Map<String, String> params) {
        return healthProfileRepository.getHealthProfiles(params);
    }

    @Override
    public HealthProfile getHealthProfileById(int id) {
        return healthProfileRepository.getHealthProfileById(id);
    }
    
    @Override
    public HealthProfile getHealthProfileByUserId(int userId) {
        return healthProfileRepository.getHealthProfileByUserId(userId);
    }

    @Override
    public HealthProfile saveHealthProfile(HealthProfile healthProfile) {
        return healthProfileRepository.addOrUpdateHealthProfile(healthProfile);
    }

    @Override
    public boolean deleteHealthProfile(int id) {
        try {
            this.healthProfileRepository.deleteHealthProfile(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
