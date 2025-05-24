/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhom20.repositories;

import com.nhom20.pojo.UserAccount;
import java.util.List;

/**
 *
 * @author nguyenhos
 */
public interface UserRepository {
    UserAccount getUserById(int id);

    UserAccount getUserByUsername(String username);

    UserAccount getUserByEmail(String email);

    List<UserAccount> getAllUsers();

    List<UserAccount> searchUsersByUsername(String username);

    UserAccount addUser(UserAccount user);

    UserAccount updateUser(UserAccount user);

    boolean deleteUser(int id);

    boolean authenticate(String username, String password);

    List<UserAccount> findByRole(String role);

    List<UserAccount> getClientsByTrainerId(int trainerId); // Method to get clients of a trainer
}
