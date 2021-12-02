package com.example.billy.cainstructionquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class SettingsActivity extends Activity {

    private SharedPreferences sharedPref;
    private String background_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        background_color = sharedPref.getString(MainActivity.COLOR, MainActivity.GREEN);
        switch(background_color){
            case MainActivity.GREEN: setTheme(R.style.AppTheme_Green); break;
            case MainActivity.RED:   setTheme(R.style.AppTheme_Red); break;
            case MainActivity.BLUE:  setTheme(R.style.AppTheme_Blue); break;
            case MainActivity.PINK:  setTheme(R.style.AppTheme_Pink); break;
            case MainActivity.PURPLE:  setTheme(R.style.AppTheme_Purple); break;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }
}
