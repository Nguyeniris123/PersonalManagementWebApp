/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom20.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author nguyenho
 */
@Entity
@Table(name = "workout_plan")
@NamedQueries({
    @NamedQuery(name = "WorkoutPlan.findAll", query = "SELECT w FROM WorkoutPlan w"),
    @NamedQuery(name = "WorkoutPlan.findById", query = "SELECT w FROM WorkoutPlan w WHERE w.id = :id"),
    @NamedQuery(name = "WorkoutPlan.findByName", query = "SELECT w FROM WorkoutPlan w WHERE w.name = :name"),
    @NamedQuery(name = "WorkoutPlan.findByStartDate", query = "SELECT w FROM WorkoutPlan w WHERE w.startDate = :startDate"),
    @NamedQuery(name = "WorkoutPlan.findByEndDate", query = "SELECT w FROM WorkoutPlan w WHERE w.endDate = :endDate"),
    @NamedQuery(name = "WorkoutPlan.findByCreatedAt", query = "SELECT w FROM WorkoutPlan w WHERE w.createdAt = :createdAt")})
public class WorkoutPlan implements Serializable {

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
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserAccount userId;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workoutPlanId")
    private Set<WorkoutPlanExercise> workoutPlanExerciseSet;

    public WorkoutPlan() {
    }

    public WorkoutPlan(Integer id) {
        this.id = id;
    }

    public WorkoutPlan(Integer id, String name) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UserAccount getUserId() {
        return userId;
    }

    public void setUserId(UserAccount userId) {
        this.userId = userId;
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
