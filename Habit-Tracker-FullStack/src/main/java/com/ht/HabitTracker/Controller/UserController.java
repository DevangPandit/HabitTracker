package com.ht.HabitTracker.Controller;

import com.ht.HabitTracker.Model.User;
import com.ht.HabitTracker.Repository.UserRepository;
import com.ht.HabitTracker.Service.UserService;
import jdk.javadoc.doclet.Reporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repo;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user){
        Optional<User> existingUser = repo.findByEmail(user.getEmail());
        Optional<User> existingUserName = repo.findByUsername(user.getUsername());
        if(existingUser.isPresent() || existingUserName.isPresent()){
            Map<String, String> res = new HashMap<>();
            res.put("message","User already exists with this email or username!!");
            return ResponseEntity.ok(res);
        }
        service.registerUser(user);
        Map<String, String>  successRes = new HashMap<>();
        successRes.put("message", "User Registered Successfully!");
        return ResponseEntity.ok(successRes);
    }

    @GetMapping("/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) throws Exception{
        Optional<User> existingUser = repo.findByEmail(email);
        if(existingUser.isPresent()){
            return existingUser;
        }
         throw new Exception("User does not exist with this email!");
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        List<User> users = repo.findAll();
        return users;
    }

    @GetMapping("/userData")
    public ResponseEntity<String> getUserData() {
        return ResponseEntity.ok("This is data for users only!");
    }
}
