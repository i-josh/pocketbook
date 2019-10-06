package com.developa.pocketbook.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.developa.pocketbook.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean theme = preferences.getBoolean("theme", false);
        if (theme)
            setTheme(R.style.DarkThemeNoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
