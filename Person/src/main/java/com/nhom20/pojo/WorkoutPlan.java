/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author nguyenho
 */
@Entity
@Table(name = "workout_plan")
@NamedQueries({
    @NamedQuery(name = "WorkoutPlan.findAll", query = "SELECT w FROM WorkoutPlan w"),
    @NamedQuery(name = "WorkoutPlan.findById", query = "SELECT w FROM WorkoutPlan w WHERE w.id = :id"),
    @NamedQuery(name = "WorkoutPlan.findByDate", query = "SELECT w FROM WorkoutPlan w WHERE w.date = :date"),
    @NamedQuery(name = "WorkoutPlan.findByExerciseName", query = "SELECT w FROM WorkoutPlan w WHERE w.exerciseName = :exerciseName"),
    @NamedQuery(name = "WorkoutPlan.findByDurationMinutes", query = "SELECT w FROM WorkoutPlan w WHERE w.durationMinutes = :durationMinutes"),
    @NamedQuery(name = "WorkoutPlan.findByCaloriesBurned", query = "SELECT w FROM WorkoutPlan w WHERE w.caloriesBurned = :caloriesBurned")})
public class WorkoutPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "exercise_name")
    private String exerciseName;
    @Column(name = "duration_minutes")
    private Integer durationMinutes;
    @Column(name = "calories_burned")
    private Integer caloriesBurned;
    @Lob
    @Column(name = "notes")
    private String notes;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserAccount userId;

    public WorkoutPlan() {
    }

    public WorkoutPlan(Integer id) {
        this.id = id;
    }

    public WorkoutPlan(Integer id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(Integer caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public UserAccount getUserId() {
        return userId;
    }

    public void setUserId(UserAccount userId) {
        this.userId = userId;
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
        if (!(object instanceof WorkoutPlan)) {
            return false;
        }
        WorkoutPlan other = (WorkoutPlan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom20.pojo.WorkoutPlan[ id=" + id + " ]";
    }
    
}
