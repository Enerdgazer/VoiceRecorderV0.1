package com.example.SimpleVoiceRecorder.gotov.Repository;

import android.app.Application;

//Class for initializing the repository at the start of the application
public class IntentApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Repository.initialize(this);
    }
    }

