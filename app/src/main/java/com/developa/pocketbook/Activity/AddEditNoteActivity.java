package com.developa.pocketbook.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.developa.pocketbook.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.developa.pocketbook.EXTRA_TITLE";
    public static final String EXTRA_BODY = "com.developa.pocketbook.EXTRA_BODY";
    public static final String EXTRA_TIME_STAMP = "com.developa.pocketbook.EXTRA_TIME_STAMP";

    private View customWarningToast;

    private EditText mNoteTitle;
    private EditText mNoteBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        Toolbar toolbar = findViewById(R.id.add_edit_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Add Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        mNoteTitle = findViewById(R.id.et_note_title);
        mNoteBody = findViewById(R.id.et_note_body);

        customWarningToast = getLayoutInflater().inflate(R.layout.custom_warning_toast,(ViewGroup) findViewById(R.id.warning_toast));
    }

    private void saveNote() {
        String title = mNoteTitle.getText().toString();
        String body = mNoteBody.getText().toString();

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        String timeStamp = dateFormat.format(date);

        if (title.trim().isEmpty() || body.trim().isEmpty()) {
            Toast toast = Toast.makeText(this,null,Toast.LENGTH_LONG);
            toast.setView(customWarningToast);
            toast.show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_BODY, body);
        data.putExtra(EXTRA_TIME_STAMP, timeStamp);

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_edit_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.note_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
