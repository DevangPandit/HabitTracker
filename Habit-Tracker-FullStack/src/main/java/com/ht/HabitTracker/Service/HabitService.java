package com.ht.HabitTracker.Service;

import com.ht.HabitTracker.Model.Habit;
import com.ht.HabitTracker.Model.User;
import com.ht.HabitTracker.Repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HabitService {
    @Autowired
    private HabitRepository repo;


    public String createHabit(Habit habit, User user){
        habit.setCreatedAt(LocalDate.now());
        habit.setUser(user);
        repo.save(habit);
        return "Habit created successfully!";
    }

    public List<Habit> getAllHabits(){
        return repo.findAll();
    }

    public Optional<Habit> getHabitById(Long habitId){
        return repo.findById(habitId);
    }

    public String updateHabit(Habit habit,Long habitId){
        Optional<Habit> existingHabit = getHabitById(habitId);

        if(existingHabit.isEmpty()){
            return "Habit not found!";
        }
        habit.setId(habitId);
        repo.save(habit);
        return "Habit saved successfully!";
    }

    public String deleteHabit(Long habitId){
        repo.deleteById(habitId);
        return "Habit deleted successfully!";
    }

    public List<Habit> getHabitsByUsername(String username) {
        return repo.findByUserUsername(username);
    }
}
