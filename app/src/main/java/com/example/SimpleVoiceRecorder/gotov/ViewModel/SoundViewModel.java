package com.example.SimpleVoiceRecorder.gotov.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.SimpleVoiceRecorder.gotov.Repository.Repository;
import com.example.SimpleVoiceRecorder.gotov.Model.Sound;

import java.util.List;

public class SoundViewModel extends ViewModel {
    private LiveData<List<Sound>> listSound;
    private final Repository repository = Repository.getInstance();

    public LiveData<List<Sound>> getData() {
        if (listSound == null) {
            listSound = repository.getAllSound();
        }
        return listSound;

    }
}
