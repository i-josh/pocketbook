package com.developa.pocketbook.Activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.developa.pocketbook.Model.Note;
import com.developa.pocketbook.R;
import com.developa.pocketbook.ViewModel.NoteViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEditNoteActivity extends AppCompatActivity {
    private View customWarningToast;

    private int mEditId;

    private EditText mNoteTitle;
    private EditText mNoteBody;

    private NoteViewModel mViewModel;
    private View customSuccessToast;
    boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        Toolbar toolbar = findViewById(R.id.add_edit_toolbar);
        setSupportActionBar(toolbar);

        customSuccessToast = getLayoutInflater().inflate(R.layout.custom_success_toast,
                (ViewGroup)findViewById(R.id.success_toast));
        customWarningToast = getLayoutInflater().inflate(R.layout.custom_warning_toast,
                (ViewGroup) findViewById(R.id.warning_toast));

        getSupportActionBar().setTitle("Add Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        mNoteTitle = findViewById(R.id.et_note_title);
        mNoteBody = findViewById(R.id.et_note_body);
        mViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        Intent intent = getIntent();
        isEdit = intent.hasExtra("EditId");
        if (isEdit){
            getSupportActionBar().setTitle("Edit Note");
            mEditId = intent.getIntExtra("EditId",-1);
            String editedTitle = intent.getStringExtra("EditTitle");
            String editedBody = intent.getStringExtra("EditBody");
            mNoteTitle.setText(editedTitle);
            mNoteBody.setText(editedBody);
        }
    }

    private void saveNote() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        String timeStamp = dateFormat.format(date);

        String title = mNoteTitle.getText().toString();
        String body = mNoteBody.getText().toString();

        if (title.trim().isEmpty() || body.trim().isEmpty()) {
            Toast toast = Toast.makeText(this,null,Toast.LENGTH_LONG);
            toast.setView(customWarningToast);
            toast.show();
            return;
        }

        Note note = new Note(title,body,timeStamp);

        if (isEdit){
            if (mEditId == -1){
                Toast.makeText(this, "note cant be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            note.setId(mEditId);
            mViewModel.updateNote(note);
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
        }else {
            mViewModel.insertNote(note);

            Toast toast = Toast.makeText(this, null, Toast.LENGTH_LONG);
            toast.setView(customSuccessToast);
            toast.show();

            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_edit_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.note_save) {
            saveNote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
