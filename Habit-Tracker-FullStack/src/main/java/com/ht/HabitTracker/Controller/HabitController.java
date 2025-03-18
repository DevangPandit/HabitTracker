package com.ht.HabitTracker.Controller;

import com.ht.HabitTracker.Model.Habit;
import com.ht.HabitTracker.Model.User;
import com.ht.HabitTracker.Repository.HabitRepository;
import com.ht.HabitTracker.Repository.UserRepository;
import com.ht.HabitTracker.Service.HabitService;
import com.ht.HabitTracker.Service.HabitStreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class HabitController {
    @Autowired
    private HabitService service;
    @Autowired
    private HabitStreakService habitStreakService;
    @Autowired
    private HabitRepository habitRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{username}/habit")
    public ResponseEntity<Habit> createHabit(@RequestBody Habit habit, @PathVariable String username) throws Exception {
        service.createHabit(habit, userRepository.findByUsername(username).orElseThrow(()->new Exception("No user found!")));
        return new ResponseEntity<>(habit, HttpStatus.OK);

    }

    @GetMapping("/{username}/habit")
    public ResponseEntity<List<Habit>> getHabitsByUsername(@PathVariable String username) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("User not found"));
        List<Habit> habits = service.getHabitsByUsername(username);
        return ResponseEntity.ok(habits);
    }

    @GetMapping("/habit")
    public List<Habit> getHabits(){
        return service.getAllHabits();
    }

    @GetMapping("/habit/{id}")
    public Optional<Habit> getHabitById(@PathVariable Long id){
        return service.getHabitById(id);
    }

    @PutMapping("/habit/{id}")
    public ResponseEntity<Habit> updateHabit(@PathVariable Long id,
                                             @RequestBody Habit habit){
        service.updateHabit(habit,id);
        return new ResponseEntity<>(habit,HttpStatus.OK);
    }

    @DeleteMapping("/habit/{id}")
    public ResponseEntity<String> deleteHabit(@PathVariable Long id) {
        Optional<Habit> habit = habitRepository.findById(id);
        if (habit.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habit not found!");
        }
        habitRepository.deleteById(id);
        return ResponseEntity.ok("Habit deleted successfully!");
    }

    @GetMapping("/{userId}/{habitId}/streak")
    public ResponseEntity<?> getStreak(@PathVariable Long habitId, @PathVariable Long userId) throws Exception{
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new Exception("Habit not found"));

        int streak = habitStreakService.getStreak(userRepository.findById(userId).orElseThrow(()->new Exception("User not found!")), habit);
        Map<String, Integer> res = new HashMap<>();
        res.put("streak",streak);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/{userId}/{habitId}/complete")
    public ResponseEntity<?> completeHabit(@PathVariable Long habitId, @PathVariable Long userId) throws Exception{
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new Exception("Habit not found"));

        habitStreakService.updateStreak(userRepository.findById(userId).orElseThrow(()-> new Exception("No User found for habit!")), habit);
        Map<String, String> res = new HashMap<>();
        res.put("message","Habit Completed! Proud of you!");
        return ResponseEntity.ok(res);
    }
}