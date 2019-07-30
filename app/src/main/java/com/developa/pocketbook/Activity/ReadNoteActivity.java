package com.developa.pocketbook.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.developa.pocketbook.Fragment.NoteFragment;
import com.developa.pocketbook.R;

public class ReadNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_note);


        Toolbar toolbar = findViewById(R.id.read_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Read Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView title = findViewById(R.id.read_title);
        TextView body = findViewById(R.id.read_body);
        TextView timeStamp = findViewById(R.id.read_time_stamp);
        FloatingActionButton editNoteButton = findViewById(R.id.edit_note_button);

        String intentTitle = getIntent().getStringExtra(NoteFragment.EXTRA_TITLE);
        String intentBody = getIntent().getStringExtra(NoteFragment.EXTRA_BODY);
        String intentTimeStamp = "Created " + getIntent().getStringExtra(NoteFragment.EXTRA_TIME_STAMP);

        title.setText(intentTitle);
        body.setText(intentBody);
        timeStamp.setText(intentTimeStamp);

        View view = findViewById(R.id.view);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int randomColor = generator.getRandomColor();

        view.setBackgroundColor(randomColor);
        toolbar.setBackgroundColor(randomColor);
        editNoteButton.getBackground().setTint(randomColor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mail_note:
                Intent sendMailIntent = new Intent(Intent.ACTION_SEND);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
