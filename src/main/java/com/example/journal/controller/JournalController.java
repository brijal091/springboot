package com.example.journal.controller;

import com.example.journal.entity.JournalEntity;
import com.example.journal.entity.User;
import com.example.journal.service.JournalEntryService;
import com.example.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntity>> getAllJournalOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = Optional.ofNullable(userService.getByUsername(username));
        if (user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get().getJournalEntities(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntity> getJournalById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntity> collect = user.getJournalEntities().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
        Optional<JournalEntity> journalEntry = journalEntryService.getByID(id);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntity> createJournal(@RequestBody JournalEntity newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = Optional.ofNullable(userService.getByUsername(username));
        if (user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            newEntry.setDate(LocalDateTime.now());
            JournalEntity saved = journalEntryService.saveEntryForUser(newEntry, user.get());
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}")
    public JournalEntity updateJournal(@PathVariable ObjectId id, @RequestBody JournalEntity newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
       journalEntryService.updateEntry(id,username, newEntry);
       return newEntry;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<JournalEntity> deleteJournal(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
       return new ResponseEntity<>(journalEntryService.deleteEntry(id,username), HttpStatus.NO_CONTENT) ;
    }

}
