package com.developa.pocketbook.Dao;


import com.developa.pocketbook.Model.Note;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
