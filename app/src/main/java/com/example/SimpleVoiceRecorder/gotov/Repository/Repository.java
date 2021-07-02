package com.example.SimpleVoiceRecorder.gotov.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.SimpleVoiceRecorder.gotov.Model.Sound;
import com.example.SimpleVoiceRecorder.gotov.Model.SoundDao;
import com.example.SimpleVoiceRecorder.gotov.Model.SoundDataBase;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository {
    private static Repository INSTANCE = null;

    private final SoundDao soundDao;

    private Repository(Context context) {
        SoundDataBase db = Room.databaseBuilder(context.getApplicationContext(),
                SoundDataBase.class,
                "database").build();
        soundDao = db.soundDao();

    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(context);
        }
    }

    public static Repository getInstance() {
        return INSTANCE;
    }


    public int getCount() {

        return soundDao.getCount();
    }

    public LiveData<List<Sound>> getAllSound() {
        return soundDao.getAll();
    }

    //Adding an entry to the database asynchronously(JavaRX)
    public void insert(Sound sound) {
        Completable.fromAction(() -> soundDao.insert(sound)).subscribeOn(Schedulers.io()).subscribe();
    }
    //Asynchronous deletion of a database entry(JavaRx)
    public void delete(Sound sound) {
        Completable.fromAction(() -> soundDao.delete(sound)).subscribeOn(Schedulers.io()).subscribe();

    }


}
