package com.example.ngenge.journal.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "journal")
public class JournalEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private Date date;
    private String tags;

    public JournalEntry() {
    }


    public JournalEntry(String title, String description, Date date, String tags) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.tags = tags;


    }

    @Ignore
    public JournalEntry(int id, String title, String description, Date date, String tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.tags = tags;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
