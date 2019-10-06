package com.developa.pocketbook.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developa.pocketbook.Activity.ReadNoteActivity;
import com.developa.pocketbook.Adapter.NoteAdapter;
import com.developa.pocketbook.Activity.AddEditNoteActivity;
import com.developa.pocketbook.Model.Note;
import com.developa.pocketbook.R;
import com.developa.pocketbook.ViewModel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NoteFragment extends Fragment {
    public static final String EXTRA_TITLE = "com.developa.pocketbook.Fragment.EXTRA_TITLE";
    public static final String EXTRA_BODY = "com.developa.pocketbook.Fragment.EXTRA_BODY" ;
    public static final String EXTRA_TIME_STAMP = "com.developa.pocketbook.Fragment.EXTRA_TIME_STAMP";
    public static final String EXTRA_ID = "com.developa.pocketbook.Fragment.EXTRA_ID";

    private NoteViewModel mNoteViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_fragment,container,false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //View emptyView = view.findViewById(R.id.empty_note_view);
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
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                mNoteViewModel.deleteNote(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Snackbar snackbar = Snackbar.make(view.findViewById(R.id.note_fragment),
                        "Note deleted",Snackbar.LENGTH_LONG);
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
                intent.putExtra(EXTRA_ID,note.getId());
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
            startActivity(noteIntent);
        }
    };
}
