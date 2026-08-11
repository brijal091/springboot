package com.example.journal.service;

import com.example.journal.entity.JournalEntity;
import com.example.journal.entity.User;
import com.example.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;


    @Autowired
    private UserService userService;

    public JournalEntity saveEntry(JournalEntity journalEntry){
        return journalEntryRepository.save(journalEntry);
    }

    public JournalEntity saveEntryForUser(JournalEntity journalEntry, User user){
        JournalEntity savedJournal = journalEntryRepository.save(journalEntry);
        user.getJournalEntities().add(savedJournal);
        userService.saveEntry(user);
        return savedJournal;
    }

    public JournalEntity updateEntry(ObjectId id, JournalEntity newEntry){
        JournalEntity existingJournal = journalEntryRepository.findById(id).orElse(null);
        if (existingJournal == null){
            return null;
        }
        existingJournal.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isBlank()? newEntry.getTitle():existingJournal.getTitle());
        existingJournal.setContent(newEntry.getContent() != null && !newEntry.getContent().isBlank()? newEntry.getContent(): existingJournal.getContent());
        return this.saveEntry(existingJournal);
    }
    public List<JournalEntity> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> getByID(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public JournalEntity deleteEntry(ObjectId id, String username){
        User user = userService.getByUsername(username);
        user.getJournalEntities().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        JournalEntity currentEntry = journalEntryRepository.findById(id).orElse(null);
         if (currentEntry != null) {
             journalEntryRepository.delete(currentEntry);
         }
         return currentEntry;
    }
}
