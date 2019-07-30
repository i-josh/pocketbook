package com.developa.pocketbook.Model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String body;

    private String timeStamp;

    public Note(String title, String body, String timeStamp) {
        this.title = title;
        this.body = body;
        this.timeStamp = timeStamp;
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

    public String getBody() {
        return body;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

}
