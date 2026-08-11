package com.example.journal.controller;

import com.example.journal.entity.JournalEntity;
import com.example.journal.entity.User;
import com.example.journal.service.JournalEntryService;
import com.example.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/{username}")
    public ResponseEntity<List<JournalEntity>> getAllJournalOfUser(@PathVariable String username){
        Optional<User> user = Optional.ofNullable(userService.getByUsername(username));
        if (user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get().getJournalEntities(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntity> getJournalById(@PathVariable ObjectId id){
        Optional<JournalEntity> journalEntry = journalEntryService.getByID(id);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/user/{username}")
    public ResponseEntity<JournalEntity> createJournal(@PathVariable String username, @RequestBody JournalEntity newEntry){
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
       journalEntryService.updateEntry(id, newEntry);
       return newEntry;
    }
    @DeleteMapping("/{id}/{username}")
    public ResponseEntity<JournalEntity> deleteJournal(@PathVariable ObjectId id, @PathVariable String username){
       return new ResponseEntity<>(journalEntryService.deleteEntry(id,username), HttpStatus.NO_CONTENT) ;
    }

}
