/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom20.services;

import com.nhom20.pojo.HealthProfile;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguyenho
 */
public interface HealthProfileService {
    List<HealthProfile> getHealthProfiles(Map<String, String> params);
    HealthProfile getHealthProfileById(int id);
    boolean saveHealthProfile(HealthProfile healthProfile);
    boolean deleteHealthProfile(int id);
}
