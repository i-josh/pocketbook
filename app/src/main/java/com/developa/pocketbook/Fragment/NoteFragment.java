package com.developa.pocketbook.Fragment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.developa.pocketbook.Activity.ReadNoteActivity;
import com.developa.pocketbook.Adapter.NoteAdapter;
import com.developa.pocketbook.Activity.AddEditNoteActivity;
import com.developa.pocketbook.Model.Note;
import com.developa.pocketbook.R;
import com.developa.pocketbook.ViewModel.NoteViewModel;

import java.util.List;

public class NoteFragment extends Fragment {

    public static final int NEW_NOTE_REQUEST =1;
    public static final int EDIT_NOTE_REQUEST =2;
    public static final String EXTRA_TITLE = "com.developa.pocketbook.Fragment.EXTRA_TITLE";
    public static final String EXTRA_BODY = "com.developa.pocketbook.Fragment.EXTRA_BODY" ;
    public static final String EXTRA_TIME_STAMP = "com.developa.pocketbook.Fragment.EXTRA_TIME_STAMP";
    private View customSuccessToast;

    private NoteViewModel mNoteViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_fragment,container,false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        customSuccessToast = getLayoutInflater().inflate(R.layout.custom_success_toast,(ViewGroup) view.findViewById(R.id.success_toast));

        RecyclerView recyclerView = view.findViewById(R.id.note_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        FloatingActionButton buttonNewNote = view.findViewById(R.id.button_add_note);
        buttonNewNote.setOnClickListener(noteButtonClickListener);
        mNoteViewModel = ViewModelProviders.of(getActivity()).get(NoteViewModel.class);
        mNoteViewModel.getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                mNoteViewModel.deleteNote(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Snackbar snackbar = Snackbar.make(view.findViewById(R.id.note_fragment),"Note deleted",Snackbar.LENGTH_LONG);
                snackbar.setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //restore note
                    }
                });
                snackbar.show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(getActivity(),ReadNoteActivity.class);
                intent.putExtra(EXTRA_TITLE,note.getTitle());
                intent.putExtra(EXTRA_BODY,note.getBody());
                intent.putExtra(EXTRA_TIME_STAMP,note.getTimeStamp());
                startActivity(intent);
            }
        });
    }

    private View.OnClickListener noteButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent noteIntent = new Intent(getActivity(),AddEditNoteActivity.class);
            startActivityForResult(noteIntent,NEW_NOTE_REQUEST);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        if (requestCode == NEW_NOTE_REQUEST && resultCode == Activity.RESULT_OK){
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String body = data.getStringExtra(AddEditNoteActivity.EXTRA_BODY);
            String timeStamp = data.getStringExtra(AddEditNoteActivity.EXTRA_TIME_STAMP);

            Note newNote = new Note(title,body,timeStamp);

            mNoteViewModel.insertNote(newNote);
            Toast toast = Toast.makeText(getActivity(),null,Toast.LENGTH_LONG);
            toast.setView(customSuccessToast);
            toast.show();
        }
    }
}
