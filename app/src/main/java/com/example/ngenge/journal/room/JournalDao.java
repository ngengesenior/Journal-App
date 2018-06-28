package com.example.ngenge.journal.room;

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
    List<JournalEntry> loadJournals();

    @Insert
    void insertJournal(JournalEntry journalEntry);

    @Insert
    void insertJournals(JournalEntry... journalEntries);

    @Delete
    void deleteJournal(JournalEntry journalEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void upateJournal(JournalEntry journalEntry);


}
