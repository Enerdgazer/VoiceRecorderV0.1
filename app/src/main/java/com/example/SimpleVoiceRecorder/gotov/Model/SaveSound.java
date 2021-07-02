package com.example.SimpleVoiceRecorder.gotov.Model;

import android.media.MediaRecorder;
import android.os.Environment;

import com.example.SimpleVoiceRecorder.gotov.Repository.Repository;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SaveSound {
    private MediaRecorder recorder;
    private String fileName;
    private String filePath;
    private long startingTime = 0;
    private long soundPlayTime = 0;

    public void go(boolean startStop) {
        //Creating a Folder
        File folder = new File(Environment.getExternalStorageDirectory() + "/MyRecorder");
        if (!folder.exists()) {
            folder.mkdir();
        }
        if (startStop) {
            doFileNameAndPath();
            try {
                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                recorder.setOutputFile(filePath);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                recorder.prepare();
                recorder.start();
                startingTime = System.currentTimeMillis();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            if (recorder != null) {
                recorder.stop();
                soundPlayTime = System.currentTimeMillis() - startingTime;
                recorder.release();
                recorder = null;
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy" + " hh:mm");
                long minutes = TimeUnit.MILLISECONDS.toMinutes(soundPlayTime);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(soundPlayTime) - TimeUnit.MINUTES.toSeconds(minutes);
                String lengthSound =  String.format("%02d:%02d", minutes, seconds);

                saveSoundInBd(dateFormat.format(date), fileName, filePath, lengthSound);
            }
        }

    }
    //Saving a record
    private void saveSoundInBd(String date, String filename, String mFilePath, String soundPlayTime) {
        Sound sound = new Sound();
        sound.setTimeAdded(date);
        sound.setRecordName(filename);
        sound.setFilePath(mFilePath);
        sound.setLength(soundPlayTime);
        Repository.getInstance().insert(sound);

    }
//Creating a write file in the device memory
    private void doFileNameAndPath() {
        final AtomicInteger count = new AtomicInteger();
        Thread t = new Thread(() -> {
            int num = Repository.getInstance().getCount();
            count.set(num);
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        File f;
        fileName = "AudioRecording" + "_" + (count.get() + 1) + ".mp4";
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        filePath += "/MyRecorder/" + fileName;
        f = new File(filePath);

    }

}
