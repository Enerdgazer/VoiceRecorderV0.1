package com.example.SimpleVoiceRecorder;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.SimpleVoiceRecorder.gotov.Model.SoundDao;
import com.example.SimpleVoiceRecorder.gotov.Model.SoundDataBase;
import com.example.SimpleVoiceRecorder.gotov.Model.Sound;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SoundDaoTest {
    private SoundDao soundDao;
    private SoundDataBase soundDataBase;


    @Before
    public void setUp() throws Exception {
        soundDataBase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                SoundDataBase.class)
                .build();
        soundDao = soundDataBase.soundDao();
    }

    @Test
    public void whenInsertEmployee() throws Exception {

        Sound sound1 = new Sound();
        sound1.setFilePath("FilePathSound1");
        sound1.setLength("100");
        sound1.setRecordName("Name_Sound1");
        sound1.setTimeAdded("11.22.2022");

        Sound sound2 = new Sound();
        sound2.setFilePath("FilePathSound2");
        sound2.setLength("120");
        sound2.setRecordName("Name_Sound2");
        sound2.setTimeAdded("18.22.2022");

        soundDao.insert(sound1);
        soundDao.insert(sound2);

        assertEquals(2, soundDao.getCount());

    }
    @Test
    public void whenDelete()throws Exception {
        Sound sound1 = new Sound();
        sound1.id=1;
        sound1.setFilePath("FilePathSound1");
        sound1.setLength("100");
        sound1.setRecordName("Name_Sound1");
        sound1.setTimeAdded("11.22.2022");

        Sound sound2 = new Sound();
        sound2.id=2;
        sound2.setFilePath("FilePathSound2");
        sound2.setLength("120");
        sound2.setRecordName("Name_Sound2");
        sound2.setTimeAdded("18.22.2022");

        soundDao.insert(sound1);
        soundDao.insert(sound2);
        soundDao.delete(sound1);

        assertEquals(1, soundDao.getCount());

    }

    @After
    public void tearDown() throws Exception {
        soundDataBase.close();
    }
}