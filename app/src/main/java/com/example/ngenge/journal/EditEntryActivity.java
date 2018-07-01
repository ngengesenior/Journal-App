package com.example.ngenge.journal;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ngenge.journal.room.JournalEntry;
import com.example.ngenge.journal.room.JournalViewModel;

import java.util.Date;

import butterknife.BindView;

public class EditEntryActivity extends AppCompatActivity {

    EditText titleEdit;
    TextInputEditText descEdit;
    EditText tagsEdit;
    Button buttonSave;
    private JournalViewModel journalViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        findViews();
        Intent intent = getIntent();
        journalViewModel = ViewModelProviders.of(this).get(JournalViewModel.class);

        String desc = intent.getStringExtra("DESC");
        String title = intent.getStringExtra("TITLE");
        Long longDate = intent.getLongExtra("LONG_DATE",-1);
        String tags = intent.getStringExtra("TAGS");
        final int id = intent.getIntExtra("ID",-1);

        titleEdit.setText(title);
        tagsEdit.setText(tags);
        descEdit.setText(desc);
        final Date date = new Date();
        date.setTime(longDate);


        buttonSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JournalEntry entry = new JournalEntry();
                        entry.setId(id);
                        entry.setDate(date);
                        entry.setTitle(titleEdit.getText().toString());
                        entry.setDescription(descEdit.getText().toString());
                        entry.setTags(tagsEdit.getText().toString());

                        journalViewModel.insert(entry);
                        startActivity(new Intent(EditEntryActivity.this,MainActivity.class));
                        finish();
                    }
                }
        );



    }


    private void findViews()
    {
        titleEdit = findViewById(R.id.editTextEntryTitle);
        tagsEdit = findViewById(R.id.editTextTags);
        descEdit = findViewById(R.id.editTextEntry);
        buttonSave = findViewById(R.id.buttonSave);
    }

}
