package com.example.journal.controller;

import com.example.journal.entity.JournalEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("journal")
public class JournalController {

    private Map<Long, JournalEntity> journalEntries = new HashMap<>();
    @GetMapping
    public List<JournalEntity> getAllJournal(){
        return new ArrayList<>(journalEntries.values());
    }

    @GetMapping("/{id}")
    public JournalEntity getJournalById(@PathVariable Long id){
        return journalEntries.get(id);
    }

    @PostMapping
    public boolean createJournal(@RequestBody JournalEntity newEntry){
        journalEntries.put(newEntry.getId(), newEntry);
        return true;
    }
    @PutMapping
    public boolean updateJournal(@PathVariable Long id, @RequestBody JournalEntity newEntry){
        journalEntries.put(newEntry.getId(), newEntry);
        return true;
    }
    @DeleteExchange
    public JournalEntity deleteJournal(@PathVariable Long id){
       return journalEntries.remove(id);
    }

}
