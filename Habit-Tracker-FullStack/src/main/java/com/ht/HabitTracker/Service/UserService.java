package com.ht.HabitTracker.Service;

import com.ht.HabitTracker.Model.User;
import com.ht.HabitTracker.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDate.now());
        return repo.save(user);
    }

    private Optional<User> getUserByEmail(String email){
        return repo.findByEmail(email);

    }
}
