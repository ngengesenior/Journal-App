package com.example.ngenge.journal.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class JournalRepository {
    private JournalDao journalDao;
    private LiveData<List<JournalEntry>> allJournalEntries;



    public JournalRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        journalDao = db.getJournalDao();
        allJournalEntries = journalDao.getJournals();

    }

    public LiveData<List<JournalEntry>> getAllJournalEntries() {
        return allJournalEntries;
    }

    public void insert(JournalEntry journalEntry) {
        new insertTask(journalDao).execute(journalEntry);
    }

    private static class insertTask extends AsyncTask<JournalEntry, Void, Void> {

        private JournalDao asyncTaskDao;

        insertTask(JournalDao journalDao) {
            asyncTaskDao = journalDao;
        }

        @Override
        protected Void doInBackground(JournalEntry... journalEntries) {
            asyncTaskDao.insertJournal(journalEntries[0]);
            return null;
        }
    }





}
