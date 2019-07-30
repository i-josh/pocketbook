package com.developa.pocketbook.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.developa.pocketbook.Dao.NoteDao;
import com.developa.pocketbook.Dao.ReminderDao;
import com.developa.pocketbook.Dao.VoiceDao;
import com.developa.pocketbook.Model.Note;
import com.developa.pocketbook.Model.Reminder;
import com.developa.pocketbook.Model.Voice;

import java.util.List;

public class AppRepository {

    private NoteDao mNoteDao;
    private ReminderDao mReminderDao;
    private VoiceDao mVoiceDao;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Reminder>> allReminders;
    private LiveData<List<Voice>> allVoices;

    public AppRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        this.mNoteDao = database.mNoteDao();
        this.mReminderDao = database.mReminderDao();
        this.mVoiceDao = database.mVoiceDao();

        allNotes = mNoteDao.getAllNotes();
        allReminders = mReminderDao.getAllReminders();
        allVoices = mVoiceDao.getAllVoices();
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

    public void insertVoice(Voice voice){
        new insertVoiceTask(mVoiceDao).execute(voice);
    }

    public void deleteVoice(Voice voice){
        new deleteVoiceTask(mVoiceDao).execute(voice);
    }

    public void deleteAllVoices(){
        new deleteAllVoicesTask(mVoiceDao).execute();
    }

    public LiveData<List<Voice>> getAllVoices(){
        return allVoices;
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

    private static class insertVoiceTask extends AsyncTask<Voice,Void,Void>{
        private VoiceDao mVoiceDao;

        insertVoiceTask(VoiceDao voiceDao) {
            mVoiceDao = voiceDao;
        }

        @Override
        protected Void doInBackground(Voice... voices) {
            mVoiceDao.insertVoice(voices[0]);
            return null;
        }
    }

    private static class deleteVoiceTask extends AsyncTask<Voice,Void,Void>{
        private VoiceDao mVoiceDao;

        deleteVoiceTask(VoiceDao voiceDao) {
            mVoiceDao = voiceDao;
        }

        @Override
        protected Void doInBackground(Voice... voices) {
            mVoiceDao.deleteVoice(voices[0]);
            return null;
        }
    }

    private static class deleteAllVoicesTask extends AsyncTask<Void,Void,Void>{
        private VoiceDao mVoiceDao;

        deleteAllVoicesTask(VoiceDao voiceDao) {
            mVoiceDao = voiceDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mVoiceDao.deleteAllVoices();
            return null;
        }
    }
}
