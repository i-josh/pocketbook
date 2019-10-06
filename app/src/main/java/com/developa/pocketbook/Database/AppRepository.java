package com.developa.pocketbook.Database;

import android.app.Application;
import android.os.AsyncTask;

import com.developa.pocketbook.Dao.NoteDao;
import com.developa.pocketbook.Dao.ReminderDao;
import com.developa.pocketbook.Model.Note;
import com.developa.pocketbook.Model.Reminder;

import java.util.List;

import androidx.lifecycle.LiveData;

public class AppRepository {

    private NoteDao mNoteDao;
    private ReminderDao mReminderDao;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Reminder>> allReminders;

    public AppRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        this.mNoteDao = database.mNoteDao();
        this.mReminderDao = database.mReminderDao();

        allNotes = mNoteDao.getAllNotes();
        allReminders = mReminderDao.getAllReminders();
    }

    public void insertNote(Note note){
        new insertNoteTask(mNoteDao).execute(note);
    }

    public void updateNote(Note note){
        new updateNoteTask(mNoteDao).execute(note);
    }

    public void deleteNote(Note note){
        new deleteNoteTask(mNoteDao).execute(note);
    }

    public void deleteAllNotes(){
        new deleteAllNotesTask(mNoteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    public void insertReminder(Reminder reminder){
        new insertReminderTask(mReminderDao).execute(reminder);
    }

    public void updateReminder(Reminder reminder){
        new updateReminderTask(mReminderDao).execute(reminder);
    }

    public void deleteReminder(Reminder reminder){
        new deleteReminderTask(mReminderDao).execute(reminder);
    }

    public void deleteAllReminders(){
        new deleteAllRemindersTask(mReminderDao).execute();
    }

    public LiveData<List<Reminder>> getAllReminders(){
        return allReminders;
    }

    private static class insertNoteTask extends AsyncTask<Note,Void,Void>{
        private NoteDao mNoteDao;

        insertNoteTask(NoteDao noteDao) {
            mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.insertNote(notes[0]);
            return null;
        }
    }

    private static class updateNoteTask extends AsyncTask<Note,Void,Void>{
        private NoteDao mNoteDao;

        updateNoteTask(NoteDao noteDao) {
            mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.updateNote(notes[0]);
            return null;
        }
    }

    private static class deleteNoteTask extends AsyncTask<Note,Void,Void>{
        private NoteDao mNoteDao;

        deleteNoteTask(NoteDao noteDao) {
            mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.deleteNote(notes[0]);
            return null;
        }
    }

    private static class deleteAllNotesTask extends AsyncTask<Void,Void,Void>{
        private NoteDao mNoteDao;

        deleteAllNotesTask(NoteDao noteDao) {
            mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDao.deleteAllNotes();
            return null;
        }
    }

    private static class insertReminderTask extends AsyncTask<Reminder,Void,Void> {
        private ReminderDao mReminderDao;

        insertReminderTask(ReminderDao reminderDao) {
            mReminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Reminder... reminders) {
            mReminderDao.insertReminder(reminders[0]);
            return null;
        }
    }
    private static class updateReminderTask extends AsyncTask<Reminder,Void,Void> {
        private ReminderDao mReminderDao;

        updateReminderTask(ReminderDao reminderDao) {
            mReminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Reminder... reminders) {
            mReminderDao.updateReminder(reminders[0]);
            return null;
        }
    }
    private static class deleteReminderTask extends AsyncTask<Reminder,Void,Void> {
        private ReminderDao mReminderDao;

        deleteReminderTask(ReminderDao reminderDao) {
            mReminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Reminder... reminders) {
            mReminderDao.deleteReminder(reminders[0]);
            return null;
        }
    }
    private static class deleteAllRemindersTask extends AsyncTask<Void,Void,Void> {
        private ReminderDao mReminderDao;

        deleteAllRemindersTask(ReminderDao reminderDao) {
            mReminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mReminderDao.deleteAllreminders();
            return null;
        }
    }
}
