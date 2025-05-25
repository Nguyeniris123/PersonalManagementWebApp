/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.services.impl;

import com.nhom20.pojo.UserAccount;
import com.nhom20.pojo.UserTrainer;
import com.nhom20.repositories.UserTrainerRepository;
import com.nhom20.services.UserTrainerService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguyenho
 */

@Service
public class UserTrainerServiceImpl implements UserTrainerService {
    
    @Autowired
    private UserTrainerRepository userTrainerRepository;

    @Override
    public List<UserTrainer> getUserTrainer(Map<String, String> params) {
        return userTrainerRepository.getUserTrainer(params);
    }

    @Override
    public UserTrainer getUserTrainerById(int id) {
        return userTrainerRepository.getUserTrainerById(id);
    }

    @Override
    public UserTrainer addOrUpdateUserTrainer(UserTrainer userTrainer) {
        return userTrainerRepository.addOrUpdateUserTrainer(userTrainer);
    }

    @Override
    public boolean deleteUserTrainer(int id) {
        try {
            this.userTrainerRepository.deleteUserTrainer(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public List<UserTrainer> getUserTrainerByUserId(int userId) {
        return userTrainerRepository.getUserTrainerByUserId(userId);
    }
    
    @Override
    public List<UserTrainer> getUserTrainerByTrainerId(int trainerId) {
        return userTrainerRepository.getUserTrainerByTrainerId(trainerId);
    }

    @Override
    public boolean existsAcceptedConnection(int userId, int trainerId) {
        return userTrainerRepository.existsAcceptedConnection(userId, trainerId);
    }

    @Override
    public List<UserAccount> getUsersAcceptedByTrainer(int trainerId) {
        return userTrainerRepository.getUsersAcceptedByTrainer(trainerId);
    }  
}
