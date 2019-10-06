package com.developa.pocketbook.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.developa.pocketbook.Database.AppRepository;
import com.developa.pocketbook.Model.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private AppRepository mRepository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        mRepository = new AppRepository(application);
        allNotes = mRepository.getAllNotes();
    }

    public void insertNote(Note note){
        mRepository.insertNote(note);
    }

    public void updateNote(Note note){
        mRepository.updateNote(note);
    }

    public void deleteNote(Note note){
        mRepository.deleteNote(note);
    }

    public void deleteAllNotes(){
        mRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
}
