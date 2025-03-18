package com.ht.HabitTracker.Service;

import com.ht.HabitTracker.Model.Habit;
import com.ht.HabitTracker.Model.HabitStreak;
import com.ht.HabitTracker.Model.User;
import com.ht.HabitTracker.Repository.HabitRepository;
import com.ht.HabitTracker.Repository.HabitStreakRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HabitStreakService {

    @Autowired
    private HabitStreakRepository repo;
    @Autowired
    private HabitRepository habitRepo;

    public void updateStreak(User user, Habit habit){
        HabitStreak streak = repo.findByUserAndHabit(user,habit).orElse(new HabitStreak());

        LocalDate currentDate = LocalDate.now();
        if(streak.getLastCompletedDate()!=null){
        if(streak.getLastCompletedDate().plusDays(1).isEqual(currentDate)){
            streak.setStreak(streak.getStreak()+1);
        }else if(!streak.getLastCompletedDate().isEqual(currentDate)){
            streak.setStreak(1);
        }
        }else{
            streak.setStreak(1);
        }

        streak.setLastCompletedDate(currentDate);
        streak.setHabit(habit);
        streak.setUser(user);
        repo.save(streak);
    }

    public int getStreak(User user, Habit habit){
        return repo.findByUserAndHabit(user, habit).map(HabitStreak::getStreak).orElse(0);
    }
}
