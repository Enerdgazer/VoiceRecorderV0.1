package com.example.SimpleVoiceRecorder.gotov.Model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Sound.class}, version = 2)
public abstract class SoundDataBase extends RoomDatabase {
    public abstract SoundDao soundDao();

}
