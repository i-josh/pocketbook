package com.developa.pocketbook.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.developa.pocketbook.Database.AppRepository;
import com.developa.pocketbook.Model.Voice;

import java.util.List;

public class VoiceViewModel extends AndroidViewModel {

    private AppRepository mRepository;
    private LiveData<List<Voice>> allVoices;


    public VoiceViewModel(@NonNull Application application) {
        super(application);

        mRepository = new AppRepository(application);
        allVoices = mRepository.getAllVoices();
    }

    public void insertVoice(Voice voice){
        mRepository.insertVoice(voice);
    }

    public void deleteVoice(Voice voice){
        mRepository.deleteVoice(voice);
    }

    public void deleteAllVoices(){
        mRepository.deleteAllVoices();
    }

    public LiveData<List<Voice>> getAllVoices(){
        return allVoices;
    }
}
