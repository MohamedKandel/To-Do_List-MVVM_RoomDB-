package com.example.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    NoteRepo repo;
    LiveData<List<Note>> AllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repo = new NoteRepo(application);
        AllNotes = repo.GetAllNotes();
    }

    public void Insert(Note note) {
        repo.insert(note);
    }

    public void Update(Note note) {
        repo.update(note);
    }

    public void Delete(Note note) {
        repo.delete(note);
    }

    public void DeleteAll() {
        repo.DeleteAllNotes();
    }

    public LiveData<List<Note>> GetAll(){
        return repo.GetAllNotes();
    }
}
