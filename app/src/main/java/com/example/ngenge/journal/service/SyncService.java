package com.example.ngenge.journal.service;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.ngenge.journal.room.JournalEntry;
import com.example.ngenge.journal.room.JournalRepository;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;

public class SyncService extends JobService {
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private JournalRepository repository;
    @Override
    public boolean onStartJob(JobParameters job) {
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        new WriteTask().execute();

        jobFinished(job,false);
        return false;
    }

    private void writeFromSqliteToFirestore() {

        String userId = auth.getCurrentUser().getUid();
        Context context = getApplicationContext();
        repository = new JournalRepository(getApplication());

        List<JournalEntry> entryList =repository.getAllJournalEntries()
                .getValue();
        Toast.makeText(getApplicationContext(),String.valueOf(entryList.size()),
                Toast.LENGTH_SHORT)
                .show();
        if(entryList.size()>0)
        {
            Toast.makeText(getApplicationContext(),"Entry list is not empty",Toast.LENGTH_SHORT)
                    .show();
            WriteBatch batch = firebaseFirestore.batch();


            CollectionReference reference = firebaseFirestore
                    .collection("journals/"+userId);
            for (JournalEntry entry:entryList)
            {
                reference.add(entry);

            }

            Toast.makeText(getApplicationContext(),
                    "Syncing finished",Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    private  class WriteTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            writeFromSqliteToFirestore();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(),"Syncing started",Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
