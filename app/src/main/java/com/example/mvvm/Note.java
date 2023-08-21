package com.example.mvvm;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")   //if we don't add tableName the default table name will be as class name (Note)
public class Note {
    @PrimaryKey(autoGenerate = true)    // = AUTOINCREMENT in MySQL
    private int id;

    //@ColumnInfo(name = "Note")      Change the column name and set it as Note
    private String title;

    private String description;

    private int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
