package com.example.creativediligence.champsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Activity_Login extends AppCompatActivity {
    SharedPreferences prefs;
    EditText username;
   /* EditText email;
    EditText phonenumber;
    EditText password;*/
    Button signup;
    TextView tv;
    Boolean isSignedup;
    Boolean isLoggedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        prefs = getSharedPreferences("com.example.creativediligence.champsapp", MODE_PRIVATE);
        isSignedup = prefs.getBoolean("signedup", true);
        isLoggedin=prefs.getBoolean("loggedin",false);

        username = findViewById(R.id.username_edit);
       /* email = findViewById(R.id.email_edit);
        phonenumber = findViewById(R.id.phonenumber_edit);
        password = findViewById(R.id.password_edit);*/
        signup = findViewById(R.id.signup_button);
        tv = findViewById(R.id.login_tv);
        CheckMode();
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = signup.getText().toString();
                String textviewText = tv.getText().toString();
                signup.setText(textviewText);
                tv.setText(buttonText);

               /* if (!signup.getText().toString().equals("signup")) {
                    email.setVisibility(View.GONE);
                    phonenumber.setVisibility(View.GONE);
                } else {
                    email.setVisibility(View.VISIBLE);
                    phonenumber.setVisibility(View.VISIBLE);
                }*/
            }
        });


    }

    @Override
    public void onBackPressed() {

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        super.onBackPressed();
    }

    public void Signup(View view) {
        ParseUser.logOut();
        switch (signup.getText().toString()){
            case "signup":
                if(username.getVisibility()==View.INVISIBLE){
                    username.setVisibility(View.VISIBLE);
                }else {
                    if (/*email.getText().toString().equals("") || password.getText().toString().equals("")
                        || */username.getText().toString().equals("") /*|| phonenumber.getText().toString().equals("")*/) {
                        Toast.makeText(Activity_Login.this, "Empty input available", Toast.LENGTH_SHORT).show();
                    } else if (ParseUser.getCurrentUser() == null) {
                        ParseUser user = new ParseUser();
                        user.setUsername(username.getText().toString());
                        user.setPassword(username.getText().toString());
                        user.put("userRole","user");
                   /* user.setEmail(email.getText().toString());
                    user.put("phonenumber", phonenumber.getText().toString());*/
                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(Activity_Login.this, "User " + username.getText().toString() + " created succesfully", Toast.LENGTH_LONG).show();

                                    prefs.edit().putBoolean("signedup", true).apply();
                                    prefs.edit().putBoolean("loggedin",true).apply();
                                    Intent intent = new Intent(Activity_Login.this, Activity_HomePage.class);
                                    Activity_Login.this.startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(Activity_Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }

                break;

            case "login":
                if ( /*password.getText().toString().equals("")
                        ||*/ username.getText().toString().equals("") ) {
                    Toast.makeText(Activity_Login.this, "Empty input available", Toast.LENGTH_SHORT).show();
                }else {
                    if (ParseUser.getCurrentUser()!=null){
                        ParseUser.logOut();
                    }


                    ParseUser.logInInBackground(username.getText().toString(), username.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user !=null){
                                prefs.edit().putBoolean("signedup", true).apply();
                                prefs.edit().putBoolean("loggedin",true).apply();
                                Toast.makeText(Activity_Login.this, "Success! Logged in as "+username.getText().toString(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Activity_Login.this, Activity_HomePage.class);
                                Activity_Login.this.startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(Activity_Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
        }



    }

    public void CheckMode(){
        if (isSignedup && !isLoggedin){
            String buttonText = signup.getText().toString();
            String textviewText = tv.getText().toString();
            signup.setText(textviewText);
            tv.setText(buttonText);
tv.setVisibility(View.INVISIBLE);

               /* email.setVisibility(View.GONE);
                phonenumber.setVisibility(View.GONE);*/

        }else if(!isSignedup ){
            tv.setVisibility(View.INVISIBLE);
            username.setVisibility(View.INVISIBLE);
        }
    }
}
