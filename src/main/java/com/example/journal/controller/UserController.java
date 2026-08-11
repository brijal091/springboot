package com.example.journal.controller;

import com.example.journal.entity.User;
import com.example.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
    return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }

    @PutMapping("/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User updatedUser){
        return userService.updateEntry(username, updatedUser);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable ObjectId id){
        return userService.deleteEntry(id);
    }

}
