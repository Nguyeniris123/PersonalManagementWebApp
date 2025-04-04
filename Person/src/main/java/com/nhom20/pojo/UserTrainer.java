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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author nguyenho
 */
@Entity
@Table(name = "user_trainer")
@NamedQueries({
    @NamedQuery(name = "UserTrainer.findAll", query = "SELECT u FROM UserTrainer u"),
    @NamedQuery(name = "UserTrainer.findById", query = "SELECT u FROM UserTrainer u WHERE u.id = :id"),
    @NamedQuery(name = "UserTrainer.findByStatus", query = "SELECT u FROM UserTrainer u WHERE u.status = :status"),
    @NamedQuery(name = "UserTrainer.findByCreatedAt", query = "SELECT u FROM UserTrainer u WHERE u.createdAt = :createdAt")})
public class UserTrainer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 8)
    @Column(name = "status")
    private String status;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserAccount userId;
    @JoinColumn(name = "trainer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserAccount trainerId;

    public UserTrainer() {
    }

    public UserTrainer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public UserAccount getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(UserAccount trainerId) {
        this.trainerId = trainerId;
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
        if (!(object instanceof UserTrainer)) {
            return false;
        }
        UserTrainer other = (UserTrainer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom20.pojo.UserTrainer[ id=" + id + " ]";
    }
    
}
