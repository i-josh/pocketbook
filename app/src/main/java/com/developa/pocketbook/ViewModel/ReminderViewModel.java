package com.developa.pocketbook.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.developa.pocketbook.Database.AppRepository;
import com.developa.pocketbook.Model.Reminder;

import java.util.List;

public class ReminderViewModel extends AndroidViewModel {

    private AppRepository mRepository;
    private LiveData<List<Reminder>> allReminders;

    public ReminderViewModel(@NonNull Application application) {
        super(application);

        mRepository = new AppRepository(application);
        allReminders = mRepository.getAllReminders();
    }

    public void insertReminer(Reminder reminder){
        mRepository.insertReminder(reminder);
    }

    public void updateReminder(Reminder reminder){
        mRepository.updateReminder(reminder);
    }

    public void deleteReminder(Reminder reminder){
        mRepository.deleteReminder(reminder);
    }

    public void deleteAllReminders(){
        mRepository.deleteAllReminders();
    }

    public LiveData<List<Reminder>> getAllReminders(){
        return allReminders;
    }
}
