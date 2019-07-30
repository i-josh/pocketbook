package com.developa.pocketbook.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.developa.pocketbook.Model.Note;
import com.developa.pocketbook.Model.Reminder;
import com.developa.pocketbook.Model.Voice;
import com.developa.pocketbook.Dao.NoteDao;
import com.developa.pocketbook.Dao.ReminderDao;
import com.developa.pocketbook.Dao.VoiceDao;

@Database(entities = {Note.class,Reminder.class,Voice.class},version = 5)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract NoteDao mNoteDao();
    public abstract ReminderDao mReminderDao();
    public abstract VoiceDao mVoiceDao();

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
