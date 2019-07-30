package com.developa.pocketbook.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.developa.pocketbook.Model.Reminder;

import java.util.List;

@Dao
public interface ReminderDao {

    @Insert
    void insertReminder(Reminder reminder);

    @Update
    void updateReminder(Reminder reminder);

    @Delete
    void deleteReminder(Reminder reminder);

    @Query("DELETE FROM REMINDER_TABLE")
    void deleteAllreminders();

    @Query("SELECT * FROM REMINDER_TABLE ORDER BY ID DESC")
    LiveData<List<Reminder>> getAllReminders();
}
