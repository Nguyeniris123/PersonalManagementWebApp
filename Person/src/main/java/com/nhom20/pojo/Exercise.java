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
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author nguyenho
 */
@Entity
@Table(name = "exercise")
@NamedQueries({
    @NamedQuery(name = "Exercise.findAll", query = "SELECT e FROM Exercise e"),
    @NamedQuery(name = "Exercise.findById", query = "SELECT e FROM Exercise e WHERE e.id = :id"),
    @NamedQuery(name = "Exercise.findByName", query = "SELECT e FROM Exercise e WHERE e.name = :name"),
    @NamedQuery(name = "Exercise.findByMuscleGroup", query = "SELECT e FROM Exercise e WHERE e.muscleGroup = :muscleGroup"),
    @NamedQuery(name = "Exercise.findByLevel", query = "SELECT e FROM Exercise e WHERE e.level = :level"),
    @NamedQuery(name = "Exercise.findByCaloriesBurned", query = "SELECT e FROM Exercise e WHERE e.caloriesBurned = :caloriesBurned")})
public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Size(max = 50)
    @Column(name = "muscle_group")
    private String muscleGroup;
    @Size(max = 20)
    @Column(name = "level")
    private String level;
    @Column(name = "calories_burned")
    private Integer caloriesBurned;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exerciseId")
    private Set<WorkoutPlanExercise> workoutPlanExerciseSet;

    public Exercise() {
    }

    public Exercise(Integer id) {
        this.id = id;
    }

    public Exercise(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(Integer caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public Set<WorkoutPlanExercise> getWorkoutPlanExerciseSet() {
        return workoutPlanExerciseSet;
    }

    public void setWorkoutPlanExerciseSet(Set<WorkoutPlanExercise> workoutPlanExerciseSet) {
        this.workoutPlanExerciseSet = workoutPlanExerciseSet;
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
        if (!(object instanceof Exercise)) {
            return false;
        }
        Exercise other = (Exercise) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom20.pojo.Exercise[ id=" + id + " ]";
    }
    
}
