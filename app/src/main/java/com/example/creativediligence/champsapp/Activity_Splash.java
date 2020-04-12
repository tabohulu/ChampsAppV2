package com.example.creativediligence.champsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.parse.ParseUser;


public class Activity_Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    SharedPreferences prefs;
    static String TAG=Activity_Splash.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefs = getSharedPreferences("com.example.creativediligence.champsapp", MODE_PRIVATE);

        if (!prefs.contains("signedup")) {
            prefs.edit().putBoolean("signedup", false).apply();
        }
        if (!prefs.contains("loggedin")) {
            prefs.edit().putBoolean("loggedin", false).apply();
        }

        final Boolean isSignedUp = prefs.getBoolean("signedup", true);
        final Boolean isLoggedIn = prefs.getBoolean("loggedin", true);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                if (isSignedUp && isLoggedIn) {
                    Intent mainIntent = new Intent(Activity_Splash.this, Activity_HomePage.class);
                    Activity_Splash.this.startActivity(mainIntent);
                }else{
                    Intent mainIntent = new Intent(Activity_Splash.this, Activity_Login.class);
                    Activity_Splash.this.startActivity(mainIntent);
                }
                Activity_Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
