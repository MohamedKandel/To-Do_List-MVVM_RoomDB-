package com.example.mvvm;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepo {
    private NoteDAO noteDAO;
    private LiveData<List<Note>> AllNotes;

    public NoteRepo(Application application) {
        NoteDB db = NoteDB.getInstance(application);
        noteDAO = db.noteDAO();
        AllNotes = noteDAO.Display();
    }

    public void insert(Note note) {
        new InsertIntoAsyncTask(noteDAO).execute(note);
    }

    public void update(Note note) {
        new UpdateIntoAsyncTask(noteDAO).execute(note);
    }

    public void delete(Note note) {
        new DeleteFromAsyncTask(noteDAO).execute(note);
    }

    public void DeleteAllNotes(){
        new DeleteAllAsyncTask(noteDAO).execute();
    }

    public LiveData<List<Note>> GetAllNotes(){
        return AllNotes;
    }

    private static class InsertIntoAsyncTask extends AsyncTask<Note,Void,Void> {

        private NoteDAO noteDAO;

        private InsertIntoAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.Insert(notes[0]);
            return null;
        }
    }

    private static class UpdateIntoAsyncTask extends AsyncTask<Note,Void,Void> {

        private NoteDAO noteDAO;

        private UpdateIntoAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.Updata(notes[0]);
            return null;
        }
    }

    private static class DeleteFromAsyncTask extends AsyncTask<Note,Void,Void> {

        private NoteDAO noteDAO;

        private DeleteFromAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.Delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void> {

        private NoteDAO noteDAO;

        private DeleteAllAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.Delete_All();
            return null;
        }
    }
}
