package com.example.SimpleVoiceRecorder.gotov.View;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.SimpleVoiceRecorder.R;
import com.example.SimpleVoiceRecorder.gotov.Model.Sound;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class PlaySoundFragment extends DialogFragment {
    final Handler handler = new Handler();
    private FloatingActionButton floatingActionButton;
    private SeekBar seekBar;
    private TextView nameFile;
    private TextView fileLength;
    private TextView textProgress;
    private Sound sound;
    private MediaPlayer mediaPlayer = null;
    private final Runnable runnable = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            if (mediaPlayer != null) {

                int mCurrentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mCurrentPosition);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(mCurrentPosition);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(mCurrentPosition)
                        - TimeUnit.MINUTES.toSeconds(minutes);
                textProgress.setText(String.format("%02d:%02d", minutes, seconds));
                updateSeekBar();
            }
        }
    };
    private boolean isPlaying = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_play_sound, null);
        builder.setView(view);
        floatingActionButton = view.findViewById(R.id.fab_play);
        seekBar = view.findViewById(R.id.seekbar);
        nameFile = view.findViewById(R.id.file_name);
        fileLength = view.findViewById(R.id.file_length_text);
        textProgress = view.findViewById(R.id.progress_text);
        nameFile.setText(sound.getRecordName());
        fileLength.setText(sound.getLength());

        ColorFilter filter = new LightingColorFilter
                (getResources().getColor(R.color.red), getResources().getColor(R.color.red));
        seekBar.getProgressDrawable().setColorFilter(filter);
        seekBar.getThumb().setColorFilter(filter);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                    handler.removeCallbacks(runnable);

                    long minutes = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition());
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition())
                            - TimeUnit.MINUTES.toSeconds(minutes);
                    textProgress.setText(String.format("%02d:%02d", minutes, seconds));

                    updateSeekBar();

                } else if (mediaPlayer == null && fromUser) {
                    prepareMediaPlayerFromPoint(progress);
                    updateSeekBar();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    handler.removeCallbacks(runnable);
                }

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (mediaPlayer != null) {
                    handler.removeCallbacks(runnable);
                    mediaPlayer.seekTo(seekBar.getProgress());

                    long minutes = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition());
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition())
                            - TimeUnit.MINUTES.toSeconds(minutes);
                    textProgress.setText(String.format("%02d:%02d", minutes, seconds));
                    updateSeekBar();
                }
            }
        });
        floatingActionButton.setOnClickListener(v -> {
            onPlay(isPlaying);
            isPlaying = !isPlaying;

        });

        return builder.create();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            stopPlaying();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            stopPlaying();
        }
    }

    private void onPlay(boolean isPlaying) {
        if (!isPlaying) {

            if (mediaPlayer == null) {
                startPlaying();
            } else {
                resumePlaying();
            }

        } else {

            pausePlaying();
        }
    }

    private void startPlaying() {
        floatingActionButton.setImageResource(R.drawable.ic_baseline_pause_24);
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(sound.getFilePath());
            mediaPlayer.prepare();
            seekBar.setMax(mediaPlayer.getDuration());
            mediaPlayer.setOnPreparedListener(mp -> mediaPlayer.start());
        } catch (IOException e) {
            Log.e("LOG_TAG", "prepare() failed");
        }

        mediaPlayer.setOnCompletionListener(mp -> stopPlaying());

        updateSeekBar();

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void prepareMediaPlayerFromPoint(int progress) {

        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(sound.getFilePath());
            mediaPlayer.prepare();
            seekBar.setMax(mediaPlayer.getDuration());
            mediaPlayer.seekTo(progress);

            mediaPlayer.setOnCompletionListener(mp -> stopPlaying());

        } catch (IOException e) {
            Log.e("Tag", "prepare() failed");
        }


        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void pausePlaying() {
        floatingActionButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        handler.removeCallbacks(runnable);
        mediaPlayer.pause();
    }

    private void resumePlaying() {
        floatingActionButton.setImageResource(R.drawable.ic_baseline_pause_24);
        handler.removeCallbacks(runnable);
        mediaPlayer.start();
        updateSeekBar();
    }

    private void stopPlaying() {
        floatingActionButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        handler.removeCallbacks(runnable);
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;

        seekBar.setProgress(seekBar.getMax());
        isPlaying = !isPlaying;

        textProgress.setText(fileLength.getText());
        seekBar.setProgress(seekBar.getMax());

        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void updateSeekBar() {
        handler.postDelayed(runnable, 1000);
    }

    public void setData(Sound sound) {
        this.sound = sound;
    }
}