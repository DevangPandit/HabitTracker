package com.ht.HabitTracker.Repository;

import com.ht.HabitTracker.Model.Habit;
import com.ht.HabitTracker.Model.HabitStreak;
import com.ht.HabitTracker.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabitStreakRepository extends JpaRepository<HabitStreak, Long> {
    Optional<HabitStreak> findByUserAndHabit(User user, Habit habit);
}
