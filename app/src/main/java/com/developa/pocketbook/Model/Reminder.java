package com.developa.pocketbook.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminder_table")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String body;

    private String notifyDate;

    private String notifyTime;

    public Reminder(String body) {
        this.body = body;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getNotifyTime() {
        return notifyTime;
    }

    public String getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(String notifyDate) {
        this.notifyDate = notifyDate;
    }
}
