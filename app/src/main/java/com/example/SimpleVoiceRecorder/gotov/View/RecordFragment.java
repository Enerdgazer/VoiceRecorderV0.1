package com.example.SimpleVoiceRecorder.gotov.View;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.SimpleVoiceRecorder.R;
import com.example.SimpleVoiceRecorder.gotov.Model.SaveSound;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecordFragment extends Fragment {
    boolean runStop = true;
    private FloatingActionButton floatButton;
    private Chronometer chronometer;
    private SaveSound saveSound;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        floatButton = view.findViewById(R.id.fab);
        chronometer = view.findViewById(R.id.Chronometer);
        floatButton.setOnClickListener(v -> {
            if (runStop) {
                Toast toast = Toast.makeText(getActivity(),
                        "Запись началась", Toast.LENGTH_SHORT);
                toast.show();
                floatButton.setImageResource(R.drawable.ic_baseline_stop_24);
                saveSound = new SaveSound();
                saveSound.go(runStop);
                doStart();
                runStop = false;
            } else doStop();
        });

        return view;
    }

    private void doStop() {
        floatButton.setImageResource(R.drawable.ic_baseline_mic_24);
        Toast toast = Toast.makeText(getActivity(),
                "Запись закончена", Toast.LENGTH_SHORT);
        toast.show();
        saveSound.go(runStop);
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        runStop = true;
    }

    private void doStart() {
        long realtime = SystemClock.elapsedRealtime();
        chronometer.setBase(realtime);
        chronometer.start();
        runStop = false;
    }
}