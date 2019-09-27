package com.developa.pocketbook.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.developa.pocketbook.Fragment.NoteFragment;
import com.developa.pocketbook.Model.Note;
import com.developa.pocketbook.R;
import com.developa.pocketbook.ViewModel.NoteViewModel;

public class ReadNoteActivity extends AppCompatActivity {
    private NoteViewModel mViewModel;
    private int mNoteId;
    private String mNoteTitle;
    private String mNoteBody;
    private String mNoteTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_note);

        Toolbar toolbar = findViewById(R.id.read_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Read Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        TextView title = findViewById(R.id.read_title);
        TextView body = findViewById(R.id.read_body);
        TextView timeStamp = findViewById(R.id.read_time_stamp);
        FloatingActionButton editNoteButton = findViewById(R.id.edit_note_button);

        final int intentId = getIntent().getIntExtra(NoteFragment.EXTRA_ID, -1);
        mNoteId = intentId;
        final String intentTitle = getIntent().getStringExtra(NoteFragment.EXTRA_TITLE);
        mNoteTitle = intentTitle;
        final String intentBody = getIntent().getStringExtra(NoteFragment.EXTRA_BODY);
        mNoteBody = intentBody;
        String intentTimeStamp = "Created " + getIntent().getStringExtra(NoteFragment.EXTRA_TIME_STAMP);
        mNoteTimeStamp = intentTimeStamp;

        title.setText(intentTitle);
        body.setText(intentBody);
        timeStamp.setText(intentTimeStamp);

        View view = findViewById(R.id.view);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int randomColor = generator.getRandomColor();

        view.setBackgroundColor(randomColor);
        toolbar.setBackgroundColor(randomColor);
        editNoteButton.getBackground().setTint(randomColor);
        editNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(getApplicationContext(), AddEditNoteActivity.class);
                editIntent.putExtra("EditId", intentId);
                editIntent.putExtra("EditTitle", intentTitle);
                editIntent.putExtra("EditBody", intentBody);
                startActivity(editIntent);
            }
        });
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
                sendMailIntent.setData(Uri.parse("email"));
                sendMailIntent.putExtra(Intent.EXTRA_EMAIL,"");
                sendMailIntent.putExtra(Intent.EXTRA_SUBJECT,mNoteTitle);
                sendMailIntent.putExtra(Intent.EXTRA_TEXT,mNoteBody);
                sendMailIntent.setType("message/rfc822");
                Intent chooser = Intent.createChooser(sendMailIntent,"Launch Email");
                startActivity(chooser);
                return true;
            case R.id.delete_note:
                Note note = new Note(mNoteTitle,mNoteBody,mNoteTimeStamp);
                note.setId(mNoteId);
                mViewModel.deleteNote(note);
                Toast.makeText(this,"Note deleted",Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
