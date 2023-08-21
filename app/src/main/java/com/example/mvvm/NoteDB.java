package com.example.mvvm;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class},version = 1)
public abstract class NoteDB extends RoomDatabase {

    private static NoteDB instance;

    public abstract NoteDAO noteDAO();

    public static synchronized NoteDB getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDB.class,"note_database")
                    .fallbackToDestructiveMigration()
                    //.addCallback(room)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback room = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDB(instance).execute();
        }
    };

    private static class populateDB extends AsyncTask<Void,Void,Void> {

        private NoteDAO noteDAO;

        private populateDB(NoteDB db) {
            noteDAO = db.noteDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.Insert(new Note("Title 1","Description 1",1));
            noteDAO.Insert(new Note("Title 2","Description 2",2));
            noteDAO.Insert(new Note("Title 3","Description 3",3));
            return null;
        }
    }
}
