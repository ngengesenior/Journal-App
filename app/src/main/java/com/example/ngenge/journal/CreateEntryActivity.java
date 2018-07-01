package com.example.ngenge.journal;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.ngenge.journal.room.AppDatabase;
import com.example.ngenge.journal.room.JournalEntry;
import com.example.ngenge.journal.room.JournalViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.buttonPickDate)
    Button buttonPickDate;
    int day_,month_,year_;
    boolean dateHasBeenPicked = false;


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


        String title = editTextEntryTitle.getText().toString();
        String desc  = editTextEntry.getText().toString();
        StringBuilder tags = new StringBuilder(editTextTags.getText().toString());


        if(validateField(title) && validateField(desc))
        {
            if(tags.toString().trim().length() > 0 && (tags.length() > 0))
            {
                String[] vals = tags.toString().split("\\W");
                tags = new StringBuilder();
                for(String s:vals)
                {
                    tags.append(s).append(" ");

                }



            }


            /**
             * Ensure that user picks date for this journal
             */
            if(dateHasBeenPicked) {
                Calendar c = Calendar.getInstance();

                Date date = new GregorianCalendar(year_,month_,day_).getTime();
                JournalEntry journalEntry= new JournalEntry(title, desc, date, tags.toString());
                journalViewModel.insert(journalEntry);
                showSnackbar(getString(R.string.str_item_saved));
                finish();

            }


            /**Let them pick a date before proceeding**/
            else{
                showSnackbar(getString(R.string.str_pick_date));

            }

        }else {
            showSnackbar(getString(R.string.empty_warning));
            return;
        }



    }


private boolean validateField(String text) {
    return text.trim().length() > 0;
}

    private void showSnackbar(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT)
                .show();
    }


    @OnClick(R.id.buttonPickDate)
    void pickDate()
    {
        showDialog();

    }

private void showDialog()
{
    Calendar calendar = Calendar.getInstance();
    day_ = calendar.get(Calendar.DAY_OF_MONTH);
    month_ = calendar.get(Calendar.MONTH);
    year_ = calendar.get(Calendar.YEAR);

    DatePickerDialog dialog = new DatePickerDialog(this,
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    buttonPickDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    dateHasBeenPicked = true;
                }
            },day_,month_,year_);
    dialog.show();

}
}
