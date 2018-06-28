package com.example.ngenge.journal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ngenge.journal.adapters.JournalRecyclerAdapter;
import com.example.ngenge.journal.room.JournalEntry;
import com.example.ngenge.journal.room.JournalViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.jornalList)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private JournalViewModel journalViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        journalViewModel = ViewModelProviders.of(this).get(JournalViewModel.class);
        

        final JournalRecyclerAdapter adapter = new JournalRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, manager.getOrientation());
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(decoration);
        journalViewModel.getAllJournalEntries()
                .observe(this, new Observer<List<JournalEntry>>() {
                    @Override
                    public void onChanged(@Nullable List<JournalEntry> journalEntries) {
                        adapter.setJournalEntryList(journalEntries);
                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    void goToAddJournal() {
        startActivity(new Intent(this, CreateEntryActivity.class));
    }
}
