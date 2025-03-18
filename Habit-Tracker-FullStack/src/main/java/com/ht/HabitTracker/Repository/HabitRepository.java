package com.ht.HabitTracker.Repository;

import com.ht.HabitTracker.Model.Habit;
import com.ht.HabitTracker.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByUserUsername(String Username);
}
