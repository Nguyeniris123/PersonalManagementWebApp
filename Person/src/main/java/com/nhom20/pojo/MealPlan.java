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
@Table(name = "meal_plan")
@NamedQueries({
    @NamedQuery(name = "MealPlan.findAll", query = "SELECT m FROM MealPlan m"),
    @NamedQuery(name = "MealPlan.findById", query = "SELECT m FROM MealPlan m WHERE m.id = :id"),
    @NamedQuery(name = "MealPlan.findByDate", query = "SELECT m FROM MealPlan m WHERE m.date = :date"),
    @NamedQuery(name = "MealPlan.findByMealType", query = "SELECT m FROM MealPlan m WHERE m.mealType = :mealType"),
    @NamedQuery(name = "MealPlan.findByCalories", query = "SELECT m FROM MealPlan m WHERE m.calories = :calories")})
public class MealPlan implements Serializable {

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
    @Column(name = "meal_type")
    private String mealType;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "calories")
    private Integer calories;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserAccount userId;

    public MealPlan() {
    }

    public MealPlan(Integer id) {
        this.id = id;
    }

    public MealPlan(Integer id, Date date) {
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

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
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
        if (!(object instanceof MealPlan)) {
            return false;
        }
        MealPlan other = (MealPlan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom20.pojo.MealPlan[ id=" + id + " ]";
    }
    
}
