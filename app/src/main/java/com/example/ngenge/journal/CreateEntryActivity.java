package com.example.ngenge.journal;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.ngenge.journal.room.AppDatabase;
import com.example.ngenge.journal.room.JournalEntry;
import com.example.ngenge.journal.room.JournalViewModel;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateEntryActivity extends AppCompatActivity {
    private AppDatabase database;
    @BindView(R.id.editTextEntry)
    EditText editTextEntry;

    @BindView(R.id.editTextEntryTitle)
    EditText editTextEntryTitle;


    @BindView(R.id.editTextTags)
    EditText editTextTags;

    @BindView(R.id.root)
    ConstraintLayout root;
    private JournalViewModel journalViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);
        ButterKnife.bind(this);
        //database = AppDatabase.getInstance(getApplicationContext());
        journalViewModel = ViewModelProviders.of(this)
                .get(JournalViewModel.class);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_done:
                submitEntry();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }






    private void submitEntry() {

        Date date = new Date();

        JournalEntry journalEntry = new JournalEntry(editTextEntryTitle.getText().toString(),
                editTextEntry.getText().toString(), date, editTextTags.getText().toString());
        //database.getJournalDao().insertJournal(journalEntry);
        journalViewModel.insert(journalEntry);
        showSnackbar("Item inserted");
        finish();

    }




    private void showSnackbar(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT)
                .show();
    }


}
