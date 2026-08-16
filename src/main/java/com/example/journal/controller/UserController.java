package com.example.journal.controller;

import com.example.journal.entity.User;
import com.example.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


//    @PostMapping
//    public void createUser(@RequestBody User user){
//        userService.saveNewUser(user);
//    }

    @PutMapping
    public User updateUser(@RequestBody User updatedUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.updateEntry(username, updatedUser);
    }

    @DeleteMapping
    public User deleteUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.deleteEntry(username);
    }

}
