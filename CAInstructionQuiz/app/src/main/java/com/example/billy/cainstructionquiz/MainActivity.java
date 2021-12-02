package com.example.billy.cainstructionquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "button";
    private static final String ADDBTN = "addButton";
    private static final String SUBBTN = "subButton";
    private static final String MULTBTN = "multButton";
    private static final String DIVBTN = "divButton";
    public static final String DIFFICULTY = "pref_difficulty";
    public static final String COLOR = "pref_color";
    public static final String GREEN = "Πράσινο";
    public static final String RED = "Κόκκινο";
    public static final String BLUE = "Μπλέ";
    public static final String PINK = "Ρόζ";
    public static final String PURPLE = "Μόβ";
    private Animation shakeAnimation;
    private SharedPreferences sharedPref;
    private String background_color;
    private String prev_color;

    @Override
    protected void onResume() {
        super.onResume();
        //Αρχικοποίηση της επιλογής του χρήστη για το χρώμα φόντου σε περίπτωση λάθους το αρχικοποιεί με το πράσινο.
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        background_color = sharedPref.getString(COLOR, GREEN);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.content_main);
        switch(background_color){
            case GREEN: rl.setBackgroundResource(R.drawable.blackboard_background_green); break;
            case RED: rl.setBackgroundResource(R.drawable.blackboard_background_red);  break;
            case BLUE: rl.setBackgroundResource(R.drawable.blackboard_background_blue); break;
            case PINK: rl.setBackgroundResource(R.drawable.blackboard_background_pink); break;
            case PURPLE: rl.setBackgroundResource(R.drawable.blackboard_background_purple); break;
        }
        if (!prev_color.equals(background_color)) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        background_color = sharedPref.getString(COLOR, GREEN);
        prev_color = background_color;
        switch(background_color){
            case GREEN: setTheme(R.style.AppTheme_Green_NoActionBar); break;
            case RED:   setTheme(R.style.AppTheme_Red_NoActionBar); break;
            case BLUE:  setTheme(R.style.AppTheme_Blue_NoActionBar); break;
            case PINK:  setTheme(R.style.AppTheme_Pink_NoActionBar); break;
            case PURPLE:  setTheme(R.style.AppTheme_Purple_NoActionBar); break;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake_effect);
        ImageView addBtn = (ImageView) findViewById(R.id.addButton);
        ImageView subBtn = (ImageView) findViewById(R.id.subButton);
        ImageView multBtn = (ImageView) findViewById(R.id.multButton);
        ImageView divBtn = (ImageView) findViewById(R.id.divButton);
        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        multBtn.setOnClickListener(this);
        divBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_credits) {
            Intent i = new Intent(this,AboutCredits.class);
            startActivity(i);
            return true;
        }else if (id == R.id.settings) {
            Intent i = new Intent(this,SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        //view.startAnimation(shakeAnimation);
        Intent i = new Intent(this,QuizActivity.class);
        if(view.toString().contains(ADDBTN)){
            i.putExtra(TAG,ADDBTN);
        }else if(view.toString().contains(SUBBTN)){
            i.putExtra(TAG,SUBBTN);
        }else if(view.toString().contains(MULTBTN)){
            i.putExtra(TAG,MULTBTN);
        }else{
            i.putExtra(TAG,DIVBTN);
        }
        startActivity(i);
    }

    public void infoPressed(View v){
        Intent i = new Intent(this,AboutCredits.class);
        startActivity(i);
    }

    public void settingsPressed(View v){
        Intent i = new Intent(this,SettingsActivity.class);
        startActivity(i);
    }

}
