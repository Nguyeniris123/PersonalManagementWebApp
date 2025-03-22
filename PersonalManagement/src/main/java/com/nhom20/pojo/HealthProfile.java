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
@Table(name = "health_profile")
@NamedQueries({
    @NamedQuery(name = "HealthProfile.findAll", query = "SELECT h FROM HealthProfile h"),
    @NamedQuery(name = "HealthProfile.findById", query = "SELECT h FROM HealthProfile h WHERE h.id = :id"),
    @NamedQuery(name = "HealthProfile.findByHeight", query = "SELECT h FROM HealthProfile h WHERE h.height = :height"),
    @NamedQuery(name = "HealthProfile.findByWeight", query = "SELECT h FROM HealthProfile h WHERE h.weight = :weight"),
    @NamedQuery(name = "HealthProfile.findByBmi", query = "SELECT h FROM HealthProfile h WHERE h.bmi = :bmi"),
    @NamedQuery(name = "HealthProfile.findByHeartRate", query = "SELECT h FROM HealthProfile h WHERE h.heartRate = :heartRate"),
    @NamedQuery(name = "HealthProfile.findByStepsPerDay", query = "SELECT h FROM HealthProfile h WHERE h.stepsPerDay = :stepsPerDay"),
    @NamedQuery(name = "HealthProfile.findByWaterIntake", query = "SELECT h FROM HealthProfile h WHERE h.waterIntake = :waterIntake"),
    @NamedQuery(name = "HealthProfile.findByUpdatedAt", query = "SELECT h FROM HealthProfile h WHERE h.updatedAt = :updatedAt")})
public class HealthProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "height")
    private Float height;
    @Column(name = "weight")
    private Float weight;
    @Column(name = "bmi")
    private Float bmi;
    @Column(name = "heart_rate")
    private Integer heartRate;
    @Lob
    @Column(name = "target")
    private String target;
    @Column(name = "steps_per_day")
    private Integer stepsPerDay;
    @Column(name = "water_intake")
    private Float waterIntake;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserAccount userId;

    public HealthProfile() {
    }

    public HealthProfile(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getBmi() {
        return bmi;
    }

    public void setBmi(Float bmi) {
        this.bmi = bmi;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getStepsPerDay() {
        return stepsPerDay;
    }

    public void setStepsPerDay(Integer stepsPerDay) {
        this.stepsPerDay = stepsPerDay;
    }

    public Float getWaterIntake() {
        return waterIntake;
    }

    public void setWaterIntake(Float waterIntake) {
        this.waterIntake = waterIntake;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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
        if (!(object instanceof HealthProfile)) {
            return false;
        }
        HealthProfile other = (HealthProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom20.pojo.HealthProfile[ id=" + id + " ]";
    }
    
}
