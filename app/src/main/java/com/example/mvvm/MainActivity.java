package com.example.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private NoteViewModel viewModel;
    public static final int Add_REQUEST_CODE = 1;
    public static final int Edit_REQUEST_CODE = 2;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //getSupportActionBar().hide();
        setTitle("Notes");
        
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        NoteAdapter adapter = new NoteAdapter();
        rv.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        viewModel.GetAll().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.SetNotes(notes);
                //Toast.makeText(MainActivity.this, "On Changed", Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AddEdit_Note.class);
                startActivityForResult(intent, Add_REQUEST_CODE);
                //startActivity(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            //for Drag and Drop we will not used in this Application
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            //for Swipe and we let it apply left and right swipe
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.Delete(adapter.getNoteAt(viewHolder.getBindingAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted",
                        Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv);

        adapter.setOnItemClickListener(new NoteAdapter.onItemClickListener() {
            @Override
            public void OnItemClick(Note note) {
                intent = new Intent(MainActivity.this, AddEdit_Note.class);
                intent.putExtra(AddEdit_Note.ID, note.getId());
                intent.putExtra(AddEdit_Note.TITLE, note.getTitle());
                intent.putExtra(AddEdit_Note.DESCRIPTION, note.getDescription());
                intent.putExtra(AddEdit_Note.PRIORITY, note.getPriority());

                startActivityForResult(intent, Edit_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Add_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEdit_Note.TITLE);
            String desc = data.getStringExtra(AddEdit_Note.DESCRIPTION);
            int prio = data.getIntExtra(AddEdit_Note.PRIORITY, 1);
            Note note = new Note(title, desc, prio);
            viewModel.Insert(note);

            //Log.d("inserted id",note.getId()+"");

            Toast.makeText(MainActivity.this, "Note saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == Edit_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEdit_Note.ID, -1);
            Log.d("id from main",id+"");
            if (id == -1) {
                Toast.makeText(MainActivity.this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            } else {
                String title = data.getStringExtra(AddEdit_Note.TITLE);
                String desc = data.getStringExtra(AddEdit_Note.DESCRIPTION);
                int prio = data.getIntExtra(AddEdit_Note.PRIORITY, 1);
                Note note = new Note(title, desc, prio);
                note.setId(id);
                viewModel.Update(note);
                Toast.makeText(MainActivity.this, "Note updated", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.delete:
                viewModel.DeleteAll();
                Toast.makeText(MainActivity.this, "All Notes Deleted", Toast.LENGTH_SHORT)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}