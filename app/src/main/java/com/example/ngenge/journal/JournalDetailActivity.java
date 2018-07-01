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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ngenge.journal.room.JournalEntry;
import com.example.ngenge.journal.room.JournalViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    String tags;
    String desc;
    String title;
    String date;
    Long long_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        tags = intent.getStringExtra("TAGS");
         desc = intent.getStringExtra("DESC");

        title = intent.getStringExtra("TITLE");
        date = intent.getStringExtra("DATE");
        long_date = intent.getLongExtra("LONG_DATE",-1);





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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_entry,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_edit)
        {
            Intent intent = new Intent(this,EditEntryActivity.class);
            intent.putExtra("TAGS",tags);
            intent.putExtra("DESC",desc);
            intent.putExtra("LONG_DATE",long_date);
            intent.putExtra("TITLE",title);

            startActivity(intent);
            finish();

            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
