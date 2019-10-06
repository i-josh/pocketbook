package com.developa.pocketbook.Database;

import android.content.Context;

import com.developa.pocketbook.Model.Note;
import com.developa.pocketbook.Model.Reminder;
import com.developa.pocketbook.Dao.NoteDao;
import com.developa.pocketbook.Dao.ReminderDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class,Reminder.class},version = 5,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract NoteDao mNoteDao();
    public abstract ReminderDao mReminderDao();

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
