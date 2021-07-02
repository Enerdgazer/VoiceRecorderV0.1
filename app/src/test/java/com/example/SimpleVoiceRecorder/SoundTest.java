package com.example.SimpleVoiceRecorder;

import com.example.SimpleVoiceRecorder.gotov.Model.Sound;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SoundTest {
    Sound sound;

    @Before
    public void setUp() throws Exception {
        sound = new Sound();
        sound.id=1;
        sound.setTimeAdded("122");
        sound.setRecordName("Name1");
        sound.setLength("03");
        sound.setFilePath("FilePath");
    }

    @Test
    public void getId() {
        assertEquals(1, sound.getId());
    }

    @Test
    public void getRecordName() {
        assertEquals("Name1", sound.getRecordName());

    }

    @Test
    public void getFilePath() {
        assertEquals("FilePath", sound.getFilePath());

    }

    @Test
    public void getLength() {
        assertEquals("03", sound.getLength());

    }

    @Test
    public void getTimeAdded() {
        assertEquals("122", sound.getTimeAdded());

    }

}