package com.ht.HabitTracker.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Value;
import org.hibernate.annotations.ValueGenerationType;

import java.time.LocalDate;


@Entity
@Data
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String status;
    private String description;
    private String frequency;
    private LocalDate createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setId(Long id){
        this.id=id;
    }

    public Long getId(){
        return id;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setStatus(String status){
        this.status=status;
    }

    public String getStatus(){
        return status;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public String getDescription(){
        return description;
    }

    public void setFrequency(String frequency){
        this.frequency=frequency;
    }

    public String getFrequency(){
        return frequency;
    }

    public void setCreatedAt(LocalDate createdAt){
        this.createdAt=createdAt;
    }

    public LocalDate getCreatedAt(){
        return createdAt;
    }

    public void setUser(User user){
        this.user=user;
    }

    public User getUser(){
        return user;
    }
}
