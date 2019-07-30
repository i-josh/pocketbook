package com.developa.pocketbook.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.developa.pocketbook.Model.Voice;

import java.util.List;

@Dao
public interface VoiceDao {

    @Insert
    void insertVoice(Voice voice);

    @Delete
    void deleteVoice(Voice voice);

    @Query("DELETE FROM VOICE_TABLE")
    void deleteAllVoices();

    @Query("SELECT * FROM VOICE_TABLE ORDER BY ID DESC")
    LiveData<List<Voice>> getAllVoices();
}
