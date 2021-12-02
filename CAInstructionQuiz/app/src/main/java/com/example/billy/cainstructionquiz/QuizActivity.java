package com.example.billy.cainstructionquiz;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.RoundingMode;
import java.security.SecureRandom;
import java.text.DecimalFormat;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "button";
    private static final String ADDBTN = "addButton";
    private static final String SUBBTN = "subButton";
    private static final String MULTBTN = "multButton";
    private static final String DIVBTN = "divButton";
    private static final String QUESTION = "Πόσο κάνει ";
    private static final String ADDACTION = " σύν ";
    private static final String SUBACTION = " μείον ";
    private static final String MULTACTION = " επί ";
    private static final String DIVACTION = " διά ";
    private static final String QUESTIONMARK = ";";
    private static final String WINMESSAGE = "Μπράβο!";
    private static final String LOSEMESSAGE = "Προσπάθησε ξανά, αυτή την φόρα θα τα καταφέρεις!";
    private static final String ERRORMESSAGE = "Προσπάθησε ξανά, έδωσες λάθος αριθμό!";
    private static final String EASY = "Εύκολο";
    private static final String MEDIUM = "Μέτριο";
    private static final String HARD = "Δύσκολο";
    private SecureRandom random = new SecureRandom();
    private int number1;
    private int number2;
    private int rightAnswers;
    private int rightAddAnswers;
    private int rightSubAnswers;
    private int rightMultAnswers;
    private int rightDivAnswers;
    private int wrongAnswers;
    private int wrongAddAnswers;
    private int wrongSubAnswers;
    private int wrongMultAnswers;
    private int wrongDivAnswers;
    private String action="";
    private TextView questionTxtView;
    private EditText answerEditText;
    private Animation shakeAnimation;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String difficulty;
    private String background_color;

    @Override
    protected void onResume() {
        super.onResume();
        String background_color = sharedPref.getString(MainActivity.COLOR, MainActivity.RED);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_quiz);
        switch(background_color){
            case MainActivity.GREEN: rl.setBackgroundResource(R.drawable.blackboard_background_green); break;
            case MainActivity.RED: rl.setBackgroundResource(R.drawable.blackboard_background_red); break;
            case MainActivity.BLUE: rl.setBackgroundResource(R.drawable.blackboard_background_blue); break;
            case MainActivity.PINK: rl.setBackgroundResource(R.drawable.blackboard_background_pink); break;
            case MainActivity.PURPLE: rl.setBackgroundResource(R.drawable.blackboard_background_purple); break;
        }
    }

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
        setContentView(R.layout.activity_quiz);
        //Αρχικοποίηση του animation του κουμπιού
        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake_effect);
        questionTxtView = (TextView) findViewById(R.id.questionTxtView);
        answerEditText = (EditText) findViewById(R.id.answerEditText);
        //KeyListener για να τρέχει την συνάρτηση υπολογισμού και με το πλήκτρο enter.
        answerEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    checkProcess();
                    return true;
                }
                return false;
            }
        });
        //Αρχικοποίηση του Default SharedPreference Manager και Editor
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();
        //Εισαγωγή των μεταβλητών από τα SharedPreferrence για τα στατιστικά του παιχνιδιού
        int defaultValue = getResources().getInteger(R.integer.answers_default);
        rightAnswers = sharedPref.getInt(getString(R.string.right_answers), defaultValue);
        rightAddAnswers = sharedPref.getInt(getString(R.string.right_add_answers), defaultValue);
        rightSubAnswers = sharedPref.getInt(getString(R.string.right_sub_answers), defaultValue);
        rightMultAnswers = sharedPref.getInt(getString(R.string.right_mult_answers), defaultValue);
        rightDivAnswers = sharedPref.getInt(getString(R.string.right_div_answers), defaultValue);
        wrongAnswers = sharedPref.getInt(getString(R.string.wrong_answers), defaultValue);
        wrongAddAnswers = sharedPref.getInt(getString(R.string.wrong_add_answers), defaultValue);
        wrongSubAnswers = sharedPref.getInt(getString(R.string.wrong_sub_answers), defaultValue);
        wrongMultAnswers = sharedPref.getInt(getString(R.string.wrong_mult_answers), defaultValue);
        wrongDivAnswers = sharedPref.getInt(getString(R.string.wrong_div_answers), defaultValue);
        //Εισαγωγή της μεταβλητής από τα SharedPreferrence για την δυσκολία του παιχνιδιού
        //Σε περίπτωση προβλήματος με την μεταβλητή του SharedPreferrence για την δυσκολία να βάζει αυτόματα εύκολο
        difficulty = sharedPref.getString(MainActivity.DIFFICULTY, EASY);
        //Αρχικοποίηση των αριθμών για την πράξη βάση της δυσκολίας
        setRandoms();
        //Εύρεση πράξης από τα στοιχεία που προήρθαν από το προηγούμενο Activity
        String buttonString="";
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b!=null) {
            buttonString = (String) b.get(TAG);
        }
        switch(buttonString){
            case ADDBTN: action=ADDACTION; break;
            case SUBBTN: action=SUBACTION; break;
            case MULTBTN: action=MULTACTION; break;
            case DIVBTN: action=DIVACTION; break;
        }
        questionTxtView.setText(QUESTION+String.valueOf(number1)+action+String.valueOf(number2)+QUESTIONMARK);
    }

    private void setRandoms(){
        switch(difficulty){
            case EASY:
                number1 = random.nextInt(10 - 1) + 1;
                number2 = random.nextInt(10 - 1) + 1;
                break;
            case MEDIUM:
                number1 = random.nextInt(100 - 1) + 1;
                number2 = random.nextInt(100 - 1) + 1;
                break;
            case HARD:
                number1 = random.nextInt(1000 - 1) + 1;
                number2 = random.nextInt(1000 - 1) + 1;
                break;
        }
    }

    // Η συνάρτηση dialog παίρνει μια μεταβλητή integer οπού όταν είναι 0 σημαίνει ότι είναι νικητήριο dialog και όταν είναι 1 είναι ηττημένο dialog
    // ενώ όταν είναι -1 (ή οποιοσδήποτε άλλος ακέραιος) σημαίνει οτι υπήρξε κάποιο πρόβλημα με τον αριθμό.
    private void dialog(int win_or_lose){
        final Dialog dialog = new Dialog(QuizActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        TextView text = (TextView) dialog.findViewById(R.id.textView);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        if(win_or_lose==0){
            text.setText(WINMESSAGE);
            rightAnswers++;
            editor.putInt(getString(R.string.right_answers), rightAnswers);
            image.setImageResource(R.drawable.star);
            final MediaPlayer mediaPlayer = MediaPlayer.create(QuizActivity.this, R.raw.tada);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                }
            });
        }else if (win_or_lose==1){
            image.setImageResource(R.drawable.sad_face);
            text.setText(LOSEMESSAGE);
            wrongAnswers++;
            editor.putInt(getString(R.string.wrong_answers), wrongAnswers);
        }else{
            image.setImageResource(R.drawable.error_icon);
            text.setText(ERRORMESSAGE);
        }
        editor.commit();
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 5000);
    }

    private void resetValues(){
        setRandoms();
        answerEditText.setText("");
        questionTxtView.setText(QUESTION+String.valueOf(number1)+action+String.valueOf(number2)+QUESTIONMARK);
    }

    private void checkProcess(){
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.HALF_UP);
        int counter = 0;
        for( int i=0; i<answerEditText.getText().toString().length(); i++ ) {
            if( answerEditText.getText().toString().charAt(i) == '.' ||  answerEditText.getText().toString().charAt(i) == ',') {
                counter++;
            }
        }
        if(counter==0 || counter==1) {
            String answer_string = df.format(Double.parseDouble(answerEditText.getText().toString())).replace(',','.');
            double answer = Double.parseDouble(answer_string);
            switch (action) {
                case ADDACTION:
                    if (answer == (number1 + number2)) {
                        dialog(0);
                        resetValues();
                        rightAddAnswers++;
                        editor.putInt(getString(R.string.right_add_answers), rightAddAnswers);
                    } else {
                        dialog(1);
                        wrongAddAnswers++;
                        editor.putInt(getString(R.string.wrong_add_answers), wrongAddAnswers);
                    }
                    break;
                case SUBACTION:
                    if (answer == (number1 - number2)) {
                        dialog(0);
                        resetValues();
                        rightSubAnswers++;
                        editor.putInt(getString(R.string.right_sub_answers), rightSubAnswers);
                    } else {
                        dialog(1);
                        wrongSubAnswers++;
                        editor.putInt(getString(R.string.wrong_sub_answers), wrongSubAnswers);
                    }
                    break;
                case MULTACTION:
                    if (answer == (number1 * number2)) {
                        dialog(0);
                        resetValues();
                        rightMultAnswers++;
                        editor.putInt(getString(R.string.right_mult_answers), rightMultAnswers);
                    } else {
                        dialog(1);
                        wrongMultAnswers++;
                        editor.putInt(getString(R.string.wrong_mult_answers), wrongMultAnswers);
                    }
                    break;
                case DIVACTION:
                    if (answer == Double.parseDouble(df.format((double) number1 / number2).replace(',','.'))) {
                        dialog(0);
                        resetValues();
                        rightDivAnswers++;
                        editor.putInt(getString(R.string.right_div_answers), rightDivAnswers);
                    } else {
                        dialog(1);
                        wrongDivAnswers++;
                        editor.putInt(getString(R.string.wrong_div_answers), wrongDivAnswers);
                    }
                    break;
            }
            editor.commit();
        }else {
            dialog(-1);
        }
    }

    public void checkButtonPressed(View v){
        v.startAnimation(shakeAnimation);
        checkProcess();
    }
}
