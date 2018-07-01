package com.example.ngenge.journal.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
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


    /**
     *
     * @param entry The entry to remove from database
     */
    public void remove(JournalEntry entry)
    {
        new removeTask(journalDao)
                .execute(entry);

    }

    public void deleteAll()

    {
        new deleteAllTask(journalDao)
                .execute();
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


    private static class removeTask extends AsyncTask<JournalEntry,Void,Void>
    {
        private JournalDao removeTaskDao;

        removeTask(JournalDao dao)
        {
            removeTaskDao = dao;
        }
        @Override
        protected Void doInBackground(JournalEntry... journalEntries) {
            removeTaskDao.deleteJournal(journalEntries[0]);
            return null;
        }
    }


    private static class deleteAllTask extends AsyncTask<Void,Void,Void>{

        private JournalDao deleteAllAsyncDao;

        deleteAllTask(JournalDao dao)
        {
            deleteAllAsyncDao = dao;

        }
        @Override
        protected Void doInBackground(Void... voids) {
            deleteAllAsyncDao.deleteAll();
            return null;
        }
    }



}
