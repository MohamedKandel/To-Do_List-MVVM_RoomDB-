package com.example.mvvm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//this class have an access for our Data object (DAO) like "insert,update,delete"
@Dao
public interface NoteDAO {

    @Insert
    void Insert(Note note);

    @Update
    void Updata(Note note);

    @Delete
    void Delete(Note note);

    @Query("Delete from note_table")
    void Delete_All();

    @Query("Select * from note_table Order By priority DESC")
    LiveData<List<Note>> Display();

}
