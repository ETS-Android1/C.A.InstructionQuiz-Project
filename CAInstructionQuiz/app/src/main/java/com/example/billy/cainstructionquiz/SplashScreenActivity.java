package com.example.billy.cainstructionquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

public class SplashScreenActivity extends AppCompatActivity {
    public static final String COLOR = "pref_color";
    public static final String GREEN = "Πράσινο";
    public static final String RED = "Κόκκινο";
    public static final String BLUE = "Μπλέ";
    public static final String PINK = "Ρόζ";
    public static final String PURPLE = "Μόβ";
    private SharedPreferences sharedPref;
    private String background_color;

    @Override
    protected void onResume() {
        super.onResume();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        background_color = sharedPref.getString(COLOR, GREEN);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_splash_screen);
        switch(background_color){
            case GREEN: rl.setBackgroundResource(R.drawable.blackboard_background_green); break;
            case RED: rl.setBackgroundResource(R.drawable.blackboard_background_red);  break;
            case BLUE: rl.setBackgroundResource(R.drawable.blackboard_background_blue); break;
            case PINK: rl.setBackgroundResource(R.drawable.blackboard_background_pink); break;
            case PURPLE: rl.setBackgroundResource(R.drawable.blackboard_background_purple); break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Intent i=new Intent(SplashScreenActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();//destroying Splash activity
                    }
                }, 10000); // 4000 milliseconds για καθυστέρηση 10 δευτερολέπτων
    }
}