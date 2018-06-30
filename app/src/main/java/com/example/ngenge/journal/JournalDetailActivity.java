package com.example.ngenge.journal;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.transition.Explode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.example.ngenge.journal.room.JournalEntry;
import com.example.ngenge.journal.room.JournalViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JournalDetailActivity extends AppCompatActivity {


    @BindView(R.id.textViewTitle)
    TextView textViewTitle;
    @BindView(R.id.textViewTags)
    TextView textViewTags;

    @BindView(R.id.textViewDescription)
    TextView textViewDesc;
    @BindView(R.id.textViewDate)
    TextView dateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String tags = intent.getStringExtra("TAGS");
        String desc = intent.getStringExtra("DESC");

        String title = intent.getStringExtra("TITLE");
        String date = intent.getStringExtra("DATE");


        setData(tags,date,title,desc);

    }

    private void setData(String tags,String date, String title,String desc)
    {
        String[] tagList = tags.split("\\W");

        for (String tag:tagList)
        {
            textViewTags.setText("#"+tag+" ");
        }

        textViewDesc.setText(desc);
        dateTextView.setText(date);
        textViewTitle.setText(title);

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState !=null)
        {

        }
    }
}
