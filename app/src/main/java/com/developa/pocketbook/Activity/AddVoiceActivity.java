package com.developa.pocketbook.Activity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Toast;

import com.developa.pocketbook.R;

import java.io.IOException;

public class AddVoiceActivity extends AppCompatActivity {

    private Chronometer mChronometer;
    private ImageButton mRecordButton;
    private Button mPlayButton;
    private Button mStopButton;
    private MediaRecorder mMediaRecorder;
    private String mOutputFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voice);

        Toolbar toolbar = findViewById(R.id.add_voice_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Record Voice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        init();
    }

    private void init(){
        mRecordButton = findViewById(R.id.record_button);
        mPlayButton = findViewById(R.id.play_button);
        mStopButton = findViewById(R.id.stop_button);
        mChronometer = findViewById(R.id.chronometer);
        mPlayButton.setEnabled(false);
        mStopButton.setEnabled(false);
        mOutputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChronometer.start();
                try {
                    mRecordButton.setEnabled(false);
                    mStopButton.setEnabled(true);
                    startRecording();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"Recording Started",Toast.LENGTH_LONG).show();
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChronometer.stop();
                try {
                    mRecordButton.setEnabled(true);
                    mStopButton.setEnabled(false);
                    mPlayButton.setEnabled(true);
                    stopRecording();
                }catch (Exception e){
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"Recording Stopped",Toast.LENGTH_LONG).show();
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MediaPlayer player = new MediaPlayer();
                try {
                    player.setDataSource(mOutputFile);
                    player.prepare();
                    player.start();
                    Toast.makeText(getApplicationContext(),"Playing Audio",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void startRecording() throws IOException {
        String fileName = "audioTest" + ".mp3";
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mMediaRecorder.setOutputFile(mOutputFile);

        mMediaRecorder.prepare();
        mMediaRecorder.start();
    }

    private void stopRecording() {
        if (null != mMediaRecorder){
            try {
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }catch (NullPointerException e){
                Toast.makeText(getApplicationContext(),"Recording Stopped",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }
}