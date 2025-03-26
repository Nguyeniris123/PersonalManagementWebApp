/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author nguyenho
 */
@Entity
@Table(name = "user_account")
@NamedQueries({
    @NamedQuery(name = "UserAccount.findAll", query = "SELECT u FROM UserAccount u"),
    @NamedQuery(name = "UserAccount.findById", query = "SELECT u FROM UserAccount u WHERE u.id = :id"),
    @NamedQuery(name = "UserAccount.findByUsername", query = "SELECT u FROM UserAccount u WHERE u.username = :username"),
    @NamedQuery(name = "UserAccount.findByPassword", query = "SELECT u FROM UserAccount u WHERE u.password = :password"),
    @NamedQuery(name = "UserAccount.findByEmail", query = "SELECT u FROM UserAccount u WHERE u.email = :email"),
    @NamedQuery(name = "UserAccount.findByRole", query = "SELECT u FROM UserAccount u WHERE u.role = :role"),
    @NamedQuery(name = "UserAccount.findByFullName", query = "SELECT u FROM UserAccount u WHERE u.fullName = :fullName"),
    @NamedQuery(name = "UserAccount.findByGender", query = "SELECT u FROM UserAccount u WHERE u.gender = :gender"),
    @NamedQuery(name = "UserAccount.findByDateOfBirth", query = "SELECT u FROM UserAccount u WHERE u.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "UserAccount.findByPhone", query = "SELECT u FROM UserAccount u WHERE u.phone = :phone"),
    @NamedQuery(name = "UserAccount.findByAvatar", query = "SELECT u FROM UserAccount u WHERE u.avatar = :avatar"),
    @NamedQuery(name = "UserAccount.findByCreatedAt", query = "SELECT u FROM UserAccount u WHERE u.createdAt = :createdAt")})
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    private String role;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "gender")
    private String gender;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Column(name = "phone")
    private String phone;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<UserTrainer> userTrainerSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainerId")
    private Set<UserTrainer> userTrainerSet1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<WorkoutPlan> workoutPlanSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<MealPlan> mealPlanSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Reminder> reminderSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<HealthJournal> healthJournalSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "senderId")
    private Set<ChatMessage> chatMessageSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiverId")
    private Set<ChatMessage> chatMessageSet1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<HealthProfile> healthProfileSet;

    public UserAccount() {
    }

    public UserAccount(Integer id) {
        this.id = id;
    }

    public UserAccount(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<UserTrainer> getUserTrainerSet() {
        return userTrainerSet;
    }

    public void setUserTrainerSet(Set<UserTrainer> userTrainerSet) {
        this.userTrainerSet = userTrainerSet;
    }

    public Set<UserTrainer> getUserTrainerSet1() {
        return userTrainerSet1;
    }

    public void setUserTrainerSet1(Set<UserTrainer> userTrainerSet1) {
        this.userTrainerSet1 = userTrainerSet1;
    }

    public Set<WorkoutPlan> getWorkoutPlanSet() {
        return workoutPlanSet;
    }

    public void setWorkoutPlanSet(Set<WorkoutPlan> workoutPlanSet) {
        this.workoutPlanSet = workoutPlanSet;
    }

    public Set<MealPlan> getMealPlanSet() {
        return mealPlanSet;
    }

    public void setMealPlanSet(Set<MealPlan> mealPlanSet) {
        this.mealPlanSet = mealPlanSet;
    }

    public Set<Reminder> getReminderSet() {
        return reminderSet;
    }

    public void setReminderSet(Set<Reminder> reminderSet) {
        this.reminderSet = reminderSet;
    }

    public Set<HealthJournal> getHealthJournalSet() {
        return healthJournalSet;
    }

    public void setHealthJournalSet(Set<HealthJournal> healthJournalSet) {
        this.healthJournalSet = healthJournalSet;
    }

    public Set<ChatMessage> getChatMessageSet() {
        return chatMessageSet;
    }

    public void setChatMessageSet(Set<ChatMessage> chatMessageSet) {
        this.chatMessageSet = chatMessageSet;
    }

    public Set<ChatMessage> getChatMessageSet1() {
        return chatMessageSet1;
    }

    public void setChatMessageSet1(Set<ChatMessage> chatMessageSet1) {
        this.chatMessageSet1 = chatMessageSet1;
    }

    public Set<HealthProfile> getHealthProfileSet() {
        return healthProfileSet;
    }

    public void setHealthProfileSet(Set<HealthProfile> healthProfileSet) {
        this.healthProfileSet = healthProfileSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAccount)) {
            return false;
        }
        UserAccount other = (UserAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom20.pojo.UserAccount[ id=" + id + " ]";
    }
    
}
