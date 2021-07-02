package com.example.SimpleVoiceRecorder.gotov.Model;

import com.example.SimpleVoiceRecorder.gotov.Repository.Repository;

import java.io.File;

public class DeleteFileSound {
    private final Repository repository = Repository.getInstance();

    public void deleteFile(String filepath){
        File file = new File(filepath);
        file.delete();
    }
    public void deleteFileInBd(Sound sound){
        repository.delete(sound);

    }
}
