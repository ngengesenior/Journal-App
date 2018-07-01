package com.example.ngenge.journal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.Explode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ngenge.journal.adapters.JournalRecyclerAdapter;
import com.example.ngenge.journal.room.JournalEntry;
import com.example.ngenge.journal.room.JournalViewModel;
import com.example.ngenge.journal.service.SyncService;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements JournalRecyclerAdapter.ItemClickListener {

    private final int RC_SIGN = 123;
    private FirebaseAuth auth;
    JournalRecyclerAdapter adapter;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;
    ItemTouchHelper helper;

    @BindView(R.id.jornalList)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private JournalViewModel journalViewModel;

    private FirebaseJobDispatcher jobDispatcher;
    private final String job_tag = "job_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));


        journalViewModel = ViewModelProviders.of(this).get(JournalViewModel.class);


         adapter = new JournalRecyclerAdapter(this);
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

        helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int pos = viewHolder.getAdapterPosition();
                JournalEntry entry = adapter.getEntryAtPosition(pos);

                journalViewModel.remove(entry);
                Toast.makeText(MainActivity.this,"Item removed at"+ pos,Toast.LENGTH_SHORT)
                        .show();

            }
        });

        helper.attachToRecyclerView(recyclerView);


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

        if(id == R.id.action_sync)
        {
            Job job = jobDispatcher.newJobBuilder()
                    .setTag(job_tag)
                    .setService(SyncService.class)
                    .setLifetime(Lifetime.FOREVER)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .setReplaceCurrent(false)
                    .build();
            jobDispatcher.mustSchedule(job);

            return true;
        }

        if(id == R.id.clear_all)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(R.string.clear_all_warning)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            journalViewModel.deleteAll();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    void goToAddJournal() {
        startActivity(new Intent(this, CreateEntryActivity.class));
    }

    @Override
    public void onItemClicked(int position) {

        Intent intent = new Intent(this,JournalDetailActivity.class);
        JournalEntry entry = adapter.getEntryAtPosition(position);

        intent.putExtra("DATE",entry.getDate().toString());
        intent.putExtra("DESC",entry.getDescription());
        intent.putExtra("TAGS",entry.getTags());
        intent.putExtra("TITLE",entry.getTitle());
        startActivity(intent);

    }
}
