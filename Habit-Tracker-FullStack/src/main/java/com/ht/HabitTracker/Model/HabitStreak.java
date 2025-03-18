package com.ht.HabitTracker.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class HabitStreak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id")
    private Habit habit;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int streak;
    private LocalDate lastCompletedDate;

    public void setId(Long id){
        this.id=id;
    }

    public Long getId(){
        return id;
    }

    public void setStreak(int streak){
        this.streak=streak;
    }

    public int getStreak(){
        return streak;
    }

    public void setLastCompletedDate(LocalDate lastCompletedDate){
        this.lastCompletedDate=lastCompletedDate;
    }

    public LocalDate getLastCompletedDate(){
        return lastCompletedDate;
    }

    public void setHabit(Habit habit){
        this.habit=habit;
    }

    public Habit getHabit(){
        return habit;
    }

    public void setUser(User user){
        this.user=user;
    }

    public User getUser(){
        return user;
    }
}
