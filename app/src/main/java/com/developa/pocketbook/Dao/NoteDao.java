package com.developa.pocketbook.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.developa.pocketbook.Model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insertNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("DELETE FROM NOTE_TABLE ")
    void deleteAllNotes();

    @Query("SELECT * FROM NOTE_TABLE ORDER BY ID DESC")
    LiveData<List<Note>> getAllNotes();

}
