package com.example.SimpleVoiceRecorder.gotov.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.SimpleVoiceRecorder.gotov.Model.Sound;

import java.util.List;

@Dao
public interface SoundDao {

    @Query("SELECT * FROM Sound")
    LiveData<List<Sound>> getAll();

    @Query("SELECT * FROM sound WHERE id = :id")
    LiveData<Sound> getById(long id);

    @Query("SELECT COUNT(*) FROM Sound")
    int getCount();

    @Insert
    void insert(Sound sound);

    @Delete
    void delete(Sound sound);
}
