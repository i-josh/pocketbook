package com.developa.pocketbook.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
