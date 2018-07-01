package com.example.ngenge.journal.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface JournalDao {

    @Query("SELECT * FROM journal")
    LiveData<List<JournalEntry>> getJournals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJournal(JournalEntry journalEntry);

    @Insert
    void insertJournals(JournalEntry... journalEntries);

    @Delete
    void deleteJournal(JournalEntry journalEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateJournal(JournalEntry journalEntry);

    @Query("SELECT * FROM journal WHERE id = :entryId LIMIT 1")
    JournalEntry getEntryById(int entryId);

    @Query("DELETE FROM journal")
    void deleteAll();


}
