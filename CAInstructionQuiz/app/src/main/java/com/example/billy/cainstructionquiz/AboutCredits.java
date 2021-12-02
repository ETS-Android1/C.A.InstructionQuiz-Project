package com.example.billy.cainstructionquiz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

public class AboutCredits extends AppCompatActivity {

    private final static String CREDITSMESSAGE = "Η παρούσα εφαρμογή δημιουργήθηκε από τον Βασίλη Γιογουρτσόγλου" +
            " στα πλαίσια εργασίας Project του μαθήματος Αλληλεπίδραση Ανθρώπου-Η/Υ του Τεχνολογικού Εκπαιδευτικού Ιδρύματος Αθήνας.";
    private final static String FINALANSWERS = "Συνολικές Απαντήσεις";
    private final static String ADDANSWERS = "Απαντήσεις Πρόσθεσης";
    private final static String SUBANSWERS = "Απαντήσεις Αφαίρεσης";
    private final static String MULTANSWERS = "Απαντήσεις Πολλαπλασιασμού";
    private final static String DIVANSWERS = "Απαντήσεις Διαίρεσης";
    private final static String RIGHTANSWERS = "Σωστές: ";
    private final static String WRONGANSWERS = "Λανθασμένες: ";
    private TextView creditsMessageTxtView;
    private TextView aboutMessageTxtView;
    private SharedPreferences sharedPref;
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
        setContentView(R.layout.activity_about_credits);
        creditsMessageTxtView = (TextView) findViewById(R.id.creditsMessageTxtView);
        aboutMessageTxtView = (TextView) findViewById(R.id.aboutMessageTxtView);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
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
        aboutCreditsSetTexts();
    }

    private void aboutCreditsSetTexts(){
        creditsMessageTxtView.setText(CREDITSMESSAGE);
        aboutMessageTxtView.setText(FINALANSWERS+"\n");
        aboutMessageTxtView.setText(aboutMessageTxtView.getText()+"\t\t\t"+RIGHTANSWERS+rightAnswers+" | "+WRONGANSWERS+wrongAnswers+"\n");
        aboutMessageTxtView.setText(aboutMessageTxtView.getText()+ADDANSWERS+"\n");
        aboutMessageTxtView.setText(aboutMessageTxtView.getText()+"\t\t\t"+RIGHTANSWERS+rightAddAnswers+" | "+WRONGANSWERS+wrongAddAnswers+"\n");
        aboutMessageTxtView.setText(aboutMessageTxtView.getText()+SUBANSWERS+"\n");
        aboutMessageTxtView.setText(aboutMessageTxtView.getText()+"\t\t\t"+RIGHTANSWERS+rightSubAnswers+" | "+WRONGANSWERS+wrongSubAnswers+"\n");
        aboutMessageTxtView.setText(aboutMessageTxtView.getText()+MULTANSWERS+"\n");
        aboutMessageTxtView.setText(aboutMessageTxtView.getText()+"\t\t\t"+RIGHTANSWERS+rightMultAnswers+" | "+WRONGANSWERS+wrongMultAnswers+"\n");
        aboutMessageTxtView.setText(aboutMessageTxtView.getText()+DIVANSWERS+"\n");
        aboutMessageTxtView.setText(aboutMessageTxtView.getText()+"\t\t\t"+RIGHTANSWERS+rightDivAnswers+" | "+WRONGANSWERS+wrongDivAnswers);
    }

}
