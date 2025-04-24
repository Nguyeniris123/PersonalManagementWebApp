/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom20.services;

import com.nhom20.pojo.UserTrainer;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguyenho
 */
public interface UserTrainerService {
    List<UserTrainer> getUserTrainer(Map<String, String> params);
    UserTrainer getUserTrainerById(int id);
    UserTrainer addOrUpdateUserTrainer(UserTrainer userTrainer);
    boolean deleteUserTrainer(int id);
    List<UserTrainer> getUserTrainerByUserId(int userId);

}
