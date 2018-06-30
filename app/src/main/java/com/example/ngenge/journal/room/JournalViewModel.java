package com.example.ngenge.journal.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class JournalViewModel extends AndroidViewModel {
    private JournalRepository journalRepository;
    private LiveData<List<JournalEntry>> allJournalEntries;

    public JournalViewModel(Application application) {
        super(application);
        journalRepository = new JournalRepository(application);
        allJournalEntries = journalRepository.getAllJournalEntries();
    }

    public void insert(JournalEntry journalEntry) {
        journalRepository.insert(journalEntry);
    }

    public LiveData<List<JournalEntry>> getAllJournalEntries() {
        return allJournalEntries;
    }


}
