package com.developa.pocketbook.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.developa.pocketbook.Dao.VoiceDao;
import com.developa.pocketbook.Database.AppDatabase;
import com.developa.pocketbook.Model.Voice;
import com.developa.pocketbook.R;

import java.io.File;
import java.io.IOException;

public class RecordService extends Service {

    private static final String LOG_TAG = RecordService.class.getSimpleName();

    private Context mContext;
    private VoiceDao mVoiceDao;
    private String mFileName = null;
    private String mFilePath = null;
    private MediaRecorder mRecorder = null;

    private long mStartTimeMillis = 0;

    public RecordService(Context context){
        mContext = context.getApplicationContext();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase appDatabase = AppDatabase.getInstance(mContext);
        mVoiceDao = appDatabase.mVoiceDao();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mRecorder != null)
            stopRecording();
        super.onDestroy();
    }

    public void startRecording(){
        setFileNameAndPath();

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFilePath);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setAudioChannels(1);

        try {
            mRecorder.prepare();
            mRecorder.start();
            mStartTimeMillis = System.currentTimeMillis();

        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    public void setFileNameAndPath() {
        int count = 0;
        File f;

        do {
            count ++;
            mFileName = getString(R.string.default_file_name)
                    + "_" + (mVoiceDao.getCount() + count) + ".mp4";
            mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFilePath += "/SoundRecorder/" + mFileName;

            f = new File(mFilePath);
        }while (f.exists() && !f.isDirectory());
    }

    public void stopRecording() {
        mRecorder.stop();
        long elapsedMillis = (System.currentTimeMillis() - mStartTimeMillis);
        mRecorder.release();
        Toast.makeText(this, getString(R.string.toast_recording_finish) + " " + mFilePath, Toast.LENGTH_LONG).show();

        mRecorder = null;

        try {
            Voice voice = new Voice(mFileName, mFilePath, elapsedMillis);
            mVoiceDao.insertVoice(voice);

        } catch (Exception e){
            Log.e(LOG_TAG, "exception", e);
        }
    }
}
