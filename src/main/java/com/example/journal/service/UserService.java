package com.example.journal.service;

import com.example.journal.entity.User;
import com.example.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User userEntry){
        userRepository.save(userEntry);
    }

    public User updateEntry(String username, User newEntry){
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null){
            return null;
        }
        existingUser.setUsername(newEntry.getUsername() != null && !newEntry.getUsername().isBlank()? newEntry.getUsername():existingUser.getUsername());
        existingUser.setPassword(newEntry.getPassword() != null && !newEntry.getPassword().isBlank()? newEntry.getPassword(): existingUser.getPassword());
        this.saveEntry(existingUser);
        return existingUser;
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getByID(ObjectId id){
        return userRepository.findById(id);
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User deleteEntry(ObjectId id){
        User currentEntry = userRepository.findById(id).orElse(null);
         if (currentEntry != null) {
             userRepository.delete(currentEntry);
         }
         return currentEntry;
    }
}
