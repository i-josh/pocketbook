package com.developa.pocketbook.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "voice_table")
public class Voice {

    public Voice(String title, String voiceFilePath, long voiceDuration) {
        this.title = title;
        this.voiceFilePath = voiceFilePath;
        this.voiceDuration = voiceDuration;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String voiceFilePath;

    private String timeStamp;

    private long voiceDuration;

    public long getVoiceDuration() {
        return voiceDuration;
    }

    public void setVoiceDuration(long voiceDuration) {
        this.voiceDuration = voiceDuration;
    }

    public Voice(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getVoiceFilePath() {
        return voiceFilePath;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setVoiceFilePath(String voiceFilePath) {
        this.voiceFilePath = voiceFilePath;
    }
}
