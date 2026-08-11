package com.example.journal.service;

import com.example.journal.entity.JournalEntity;
import com.example.journal.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntity journalEntry){
        journalEntryRepository.save(journalEntry);
    }
}
