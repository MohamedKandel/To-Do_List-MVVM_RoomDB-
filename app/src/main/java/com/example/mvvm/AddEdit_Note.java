package com.example.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEdit_Note extends AppCompatActivity {

    @BindView(R.id.txt_title)
    EditText txt_title;
    @BindView(R.id.txt_desc)
    EditText txt_desc;
    @BindView(R.id.num_pick)
    NumberPicker num_pick;

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PRIORITY = "priority";
    public static final String ID = "ID";

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__note);
        ButterKnife.bind(this);

        num_pick.setMinValue(1);
        num_pick.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        intent = getIntent();
        if (intent.hasExtra(ID)) {
            setTitle("Edit Note");
            txt_title.setText(intent.getStringExtra(TITLE));
            txt_desc.setText(intent.getStringExtra(DESCRIPTION));
            num_pick.setValue(intent.getIntExtra(PRIORITY, 1));
            Toast.makeText(getApplicationContext(),intent.getIntExtra(ID,-1)+"",Toast.LENGTH_SHORT).show();
        } else {
            setTitle("Add Note");
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.save_item:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = txt_title.getText().toString().trim();
        String desc = txt_desc.getText().toString().trim();
        int per = num_pick.getValue();
        if (isEmpty(title, desc, per)) {
            Toast.makeText(getApplicationContext(), "Error in Saving data", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent data = new Intent();
            data.putExtra(TITLE, title);
            data.putExtra(DESCRIPTION, desc);
            data.putExtra(PRIORITY, per);

            int id = getIntent().getIntExtra(ID, -1);
            Log.d("id from add_Edit",id+"");
            if (id >= 0) {
                data.putExtra(ID, id);
            }
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    private boolean isEmpty(String title, String desc, int per) {
        if (title.equals("") || desc.equals("") || per < 1 || per > 10) {
            return true;
        } else {
            return false;
        }
    }
}