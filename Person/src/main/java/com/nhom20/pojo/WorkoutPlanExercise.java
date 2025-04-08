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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author nguyenho
 */
@Entity
@Table(name = "workout_plan_exercise")
@NamedQueries({
    @NamedQuery(name = "WorkoutPlanExercise.findAll", query = "SELECT w FROM WorkoutPlanExercise w"),
    @NamedQuery(name = "WorkoutPlanExercise.findById", query = "SELECT w FROM WorkoutPlanExercise w WHERE w.id = :id"),
    @NamedQuery(name = "WorkoutPlanExercise.findBySets", query = "SELECT w FROM WorkoutPlanExercise w WHERE w.sets = :sets"),
    @NamedQuery(name = "WorkoutPlanExercise.findByReps", query = "SELECT w FROM WorkoutPlanExercise w WHERE w.reps = :reps"),
    @NamedQuery(name = "WorkoutPlanExercise.findByDurationMinutes", query = "SELECT w FROM WorkoutPlanExercise w WHERE w.durationMinutes = :durationMinutes")})
public class WorkoutPlanExercise implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "sets")
    private Integer sets;
    @Column(name = "reps")
    private Integer reps;
    @Column(name = "duration_minutes")
    private Integer durationMinutes;
    @JoinColumn(name = "exercise_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Exercise exerciseId;
    @JoinColumn(name = "workout_plan_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private WorkoutPlan workoutPlanId;

    public WorkoutPlanExercise() {
    }

    public WorkoutPlanExercise(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Exercise getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Exercise exerciseId) {
        this.exerciseId = exerciseId;
    }

    public WorkoutPlan getWorkoutPlanId() {
        return workoutPlanId;
    }

    public void setWorkoutPlanId(WorkoutPlan workoutPlanId) {
        this.workoutPlanId = workoutPlanId;
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
        if (!(object instanceof WorkoutPlanExercise)) {
            return false;
        }
        WorkoutPlanExercise other = (WorkoutPlanExercise) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom20.pojo.WorkoutPlanExercise[ id=" + id + " ]";
    }
    
}
