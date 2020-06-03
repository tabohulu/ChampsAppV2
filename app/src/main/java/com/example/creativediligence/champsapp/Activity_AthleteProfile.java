package com.example.creativediligence.champsapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Activity_AthleteProfile extends AppCompatActivity {
    Toolbar toolbar;
    ImageView editProfileImage;
    ImageView cancelProflieEditImage;
    ImageView addEventImage;
    CardView basicInfoCard;
    CardView extraInfoCard;
    CardView moreInfoCard;
    Spinner sportEventSpinner;
    Spinner institutionSpinner;
    RecyclerView eventsRecyclerView;
    ArrayList<String> eventsList = new ArrayList<>();

    EditText nameEditText;
    EditText dobEditText;
    EditText biographyEditText;
    EditText personalBestEditText;
    EditText seasonsBestEditText;
    EditText teamsEditText;
    EditText trophiesEditText;

    LinearLayout eventLayout;
    int editprofileState = 0;
    boolean profileEditState = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_profile3);

        toolbar = findViewById(R.id.toolbar);
        eventLayout=findViewById(R.id.event_layout);
        editProfileImage = findViewById(R.id.edit_profile_img);
        cancelProflieEditImage = findViewById(R.id.cancel_edit_img);
        addEventImage = findViewById(R.id.add_event_image);
        addEventImage.setClickable(profileEditState);
       /* basicInfoCard = findViewById(R.id.basic_info_card);
        extraInfoCard = findViewById(R.id.extra_info_card);
        moreInfoCard = findViewById(R.id.more_info_card);*/

        nameEditText = findViewById(R.id.athletes_name_edittext);
        dobEditText = findViewById(R.id.athletes_dob);
        biographyEditText = findViewById(R.id.biography_edit_text);
        /*personalBestEditText = findViewById(R.id.personal_best_edit_text);
        seasonsBestEditText = findViewById(R.id.season_best_edit_text);
        teamsEditText = findViewById(R.id.teams_edit_text);
        trophiesEditText = findViewById(R.id.trophies_edit_text);*/

        sportEventSpinner = findViewById(R.id.sport_spinner);
        sportEventSpinner.setClickable(profileEditState);
        sportEventSpinner.setEnabled(profileEditState);
        String[] temp = new String[Helper_MyData.allSports.length + 1];
        temp[0] = "--Sport--";
        for (int i = 1; i < temp.length; i++) {
            temp[i] = Helper_MyData.allSports[i - 1];
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, temp);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportEventSpinner.setAdapter(adapter);

        institutionSpinner = findViewById(R.id.institution_spinner);
        institutionSpinner.setClickable(profileEditState);
        institutionSpinner.setEnabled(profileEditState);

        eventsRecyclerView = findViewById(R.id.exp_list_for_events);
        eventsRecyclerView.hasFixedSize();
        LinearLayoutManager llm = new LinearLayoutManager(Activity_AthleteProfile.this);
        eventsRecyclerView.setLayoutManager(llm);
        eventsRecyclerView.setItemAnimator(new DefaultItemAnimator());

       /* String[] insts = {"Inst1", "Inst2", "Inst3"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, insts);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        institutionSpinner.setAdapter(adapter2);*/

//Graph Image tinz
        /*graphImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editGraphImage.getVisibility() == View.GONE) {
                    editGraphImage.setVisibility(View.VISIBLE);
                } else {
                    editGraphImage.setVisibility(View.GONE);
                }
            }
        });*/
//Image that opens new activity
        /*editGraphImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent graphDataIntent = new Intent(Activity_AthleteProfile.this, Activity_GraphData.class);
                startActivity(graphDataIntent);
            }
        });*/
        addEventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileEditState) {
                    final EditText editText = new EditText(Activity_AthleteProfile.this);
                    LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    editText.setLayoutParams(ll);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Activity_AthleteProfile.this, R.style.MyDialogTheme);
                    alertDialogBuilder.setTitle("Enter Event Name");
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if(!editText.getText().toString().trim().equals("")) {
                                eventsList.add(editText.getText().toString().trim());

                                //Toast.makeText(Activity_AthleteProfile.this, "EventsListSize:=>" + eventsList.size(), Toast.LENGTH_SHORT).show();
                                Adapter_ProfileEvents ap = new Adapter_ProfileEvents(eventsList, Activity_AthleteProfile.this,profileEditState);
                                eventsRecyclerView.setAdapter(ap);
                            }

                        }
                    }).setNegativeButton("Cancel", null);

                    alertDialogBuilder.setView(editText);
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

            }
        });

        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                if (editprofileState == 0) {
                    editProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_done));
                    editprofileState += 1;
                    profileEditState = true;
                    /*basicInfoCard.setCardElevation(10);
                    extraInfoCard.setCardElevation(10);
                    moreInfoCard.setCardElevation(10);*/
                    dobEditText.setClickable(profileEditState);
                    dobEditText.setEnabled(profileEditState);
                    biographyEditText.setBackground(getResources().getDrawable(R.drawable.black_border));
                    //eventsEditText.setBackground(getResources().getDrawable(R.drawable.black_border));
                } else {
                    editProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
                    editprofileState -= 1;
                    profileEditState = false;
                   /* basicInfoCard.setCardElevation(0);
                    extraInfoCard.setCardElevation(0);
                    moreInfoCard.setCardElevation(0);*/
                    dobEditText.setFocusable(profileEditState);
                    dobEditText.setClickable(profileEditState);
                    dobEditText.setFocusableInTouchMode(profileEditState);
                    dobEditText.setEnabled(profileEditState);
                    biographyEditText.setBackgroundColor(getResources().getColor(R.color.color_white));
                    // eventsEditText.setBackgroundColor(getResources().getColor(R.color.color_white));
                    //CreateNewProfile();
                    UpdateProfileInfo();
                }

                Adapter_ProfileEvents ap = new Adapter_ProfileEvents(eventsList, Activity_AthleteProfile.this,profileEditState);
                eventsRecyclerView.setAdapter(ap);

                nameEditText.setFocusable(profileEditState);
                nameEditText.setClickable(profileEditState);
                nameEditText.setFocusableInTouchMode(profileEditState);

                sportEventSpinner.setEnabled(profileEditState);
                sportEventSpinner.setClickable(profileEditState);

                institutionSpinner.setEnabled(profileEditState);
                institutionSpinner.setClickable(profileEditState);

                biographyEditText.setFocusableInTouchMode(profileEditState);
                biographyEditText.setFocusable(profileEditState);
                biographyEditText.setClickable(profileEditState);

                eventsRecyclerView.setClickable(profileEditState);
                eventsRecyclerView.setFocusable(profileEditState);

                // eventsEditText.setFocusableInTouchMode(profileEditState);
                //eventsEditText.setClickable(profileEditState);
                // eventsEditText.setFocusable(profileEditState);

                /*personalBestEditText.setFocusableInTouchMode(profileEditState);
                personalBestEditText.setFocusable(profileEditState);
                personalBestEditText.setClickable(profileEditState);

                seasonsBestEditText.setFocusableInTouchMode(profileEditState);
                seasonsBestEditText.setClickable(profileEditState);
                seasonsBestEditText.setFocusable(profileEditState);

                teamsEditText.setFocusableInTouchMode(profileEditState);
                teamsEditText.setFocusable(profileEditState);
                teamsEditText.setClickable(profileEditState);

                trophiesEditText.setFocusableInTouchMode(profileEditState);
                trophiesEditText.setClickable(profileEditState);
                trophiesEditText.setFocusable(profileEditState);*/

                addEventImage.setClickable(profileEditState);

            }
        });

        cancelProflieEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Activity_AthleteProfile.this, "Profile Creation Cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        final Calendar myCalendar = Calendar.getInstance();
        final int currYear = myCalendar.get(Calendar.YEAR);
        final int[] age = {0};
        final int curMonth = myCalendar.get(Calendar.MONTH);
        final int curDay = myCalendar.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                age[0] = currYear - year;
                if (curMonth < monthOfYear && curDay < dayOfMonth) {
                    age[0] = currYear - year + 1;
                }
                dobEditText.setText(sdf.format(myCalendar.getTime()) + " (" + age[0] + ")");

            }
        };
        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dobEditText.getText().toString().trim().equals("")) {
                    String[] inputText = dobEditText.getText().toString().substring(0, dobEditText.getText().toString().indexOf("(")).split("/");
                    new DatePickerDialog(Activity_AthleteProfile.this, date, Integer.parseInt(inputText[0]), Integer.parseInt(inputText[1]) - 1,
                            Integer.parseInt(inputText[2].trim())).show();
                } else {
                    new DatePickerDialog(Activity_AthleteProfile.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(Helper_MyData.activityTitles[2]);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RetrieveProfileInfo();
    }

    public void CreateNewProfile() {
        String date = "";
        String age = "";
        if (!dobEditText.getText().toString().trim().equals("")) {
            String[] tempString = dobEditText.getText().toString().split(" ");
            date = tempString[0];
            age = tempString[1];
            age = age.replace("(", " ");
            age = age.replace(")", " ");
            age.trim();
            date.trim();
        }
        ParseObject object = new ParseObject("InstitutionAthlete");
        //ParseObject object=new ParseObject("Athletes");
        object.put("DOB", date);
        object.put("Age", age);
        object.put("created_by", ParseUser.getCurrentUser().getUsername());
        object.put("Name", nameEditText.getText().toString().trim());
        object.put("Biography", biographyEditText.getText().toString().trim());
        String st="";
        for( int i=0;i<eventsList.size();i++){
            if(i>0){
                st = st +","+ eventsList.get(i);
            }else {
                st = st + eventsList.get(i);
            }

        }
        //Toast.makeText(Activity_AthleteProfile.this, ""+st, Toast.LENGTH_LONG).show();
        object.put("Event",st.trim());
        //object.put("Event", eventsEditText.getText().toString().trim());
        /*object.put("Personal_Best",personalBestEditText.getText().toString().trim());
        object.put("Seasons_Best",seasonsBestEditText.getText().toString().trim());
        object.put("Team",teamsEditText.getText().toString().trim());
        object.put("Trophies",trophiesEditText.getText().toString().trim());*/
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(Activity_AthleteProfile.this, "New Profile Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Activity_AthleteProfile.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void UpdateProfileInfo() {
        String date = "";
        String age = "";
        if (!dobEditText.getText().toString().trim().equals("")) {
            String[] tempString = dobEditText.getText().toString().split(" ");
            date = tempString[0];
            age = tempString[1];
            //Toast.makeText(this, ""+age, Toast.LENGTH_SHORT).show();
            age = age.replace("(", " ");
            age = age.replace(")", " ");
            age.trim();
            date.trim();
        }
        final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("InstitutionAthlete");
        //final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Athletes");
        query.whereEqualTo("created_by", ParseUser.getCurrentUser().getUsername());
        final String finalDate = date;
        final String finalAge = age;
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (objects.size() > 0 && e == null) {
                    for (ParseObject obj : objects) {
                        Log.d("Users", ParseUser.getCurrentUser().getUsername() + "//" + obj.getString("created_by"));
                        obj.put("DOB", finalDate);
                        obj.put("Age", finalAge);
                        obj.put("Name", nameEditText.getText().toString().trim());
                        obj.put("Biography", biographyEditText.getText().toString().trim());
                        String st="";
                        for( int i=0;i<eventsList.size();i++){
                            if(i>0){
                                st = st +","+ eventsList.get(i);
                            }else {
                                st = st + eventsList.get(i);
                            }

                        }
                        //Toast.makeText(Activity_AthleteProfile.this, ""+st, Toast.LENGTH_LONG).show();
                        obj.put("Event",st.trim());
                            /*obj.put("Personal_Best",personalBestEditText.getText().toString().trim());
                            obj.put("Seasons_Best",seasonsBestEditText.getText().toString().trim());
                            obj.put("Team",teamsEditText.getText().toString().trim());
                            obj.put("Trophies",trophiesEditText.getText().toString().trim());*/
                        obj.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(Activity_AthleteProfile.this, "Update Complete", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

                } else {
                    if (e != null) {
                        Toast.makeText(Activity_AthleteProfile.this, "Error:-" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        CreateNewProfile();
                    }
                }
            }
        });
    }

    public void RetrieveProfileInfo() {
        final ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("AllSports");
        final ParseQuery<ParseObject> query3 = new ParseQuery<ParseObject>("Institution");
        query3.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    String[] insts = new String[objects.size() + 1];
                    insts[0] = "Independent";
                    for (int i = 0; i < objects.size(); i++) {
                        insts[i + 1] = objects.get(i).getString("Name");
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Activity_AthleteProfile.this, android.R.layout.simple_spinner_item, insts);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    institutionSpinner.setAdapter(adapter2);
                }

            }
        });
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("InstitutionAthlete");
        //ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Athletes");
        query.whereEqualTo("created_by", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                /*editProfileImage.setClickable(false);
                cancelProflieEditImage.setClickable(false);*/
                if (e == null && objects.size() > 0) {
                    for (ParseObject obj : objects) {
                        if (ParseUser.getCurrentUser().getUsername().equals(obj.getString("created_by"))) {
                            nameEditText.setText(obj.getString("Name"));
                            String ageString=obj.getString("Age").trim();
                            dobEditText.setText(obj.getString("DOB") + " (" + ageString + ")");
                            biographyEditText.setText(obj.getString("Biography"));
                            eventsList= new ArrayList<>(Arrays.asList(obj.getString("Event").split(",")));
                            Adapter_ProfileEvents ap = new Adapter_ProfileEvents(eventsList, Activity_AthleteProfile.this,profileEditState);
                            eventsRecyclerView.setAdapter(ap);
                            //eventsEditText.setText(obj.getString("Event"));
                            /*personalBestEditText.setText(obj.getString("Personal_Best"));
                            seasonsBestEditText.setText(obj.getString("Seasons_Best"));
                            teamsEditText.setText(obj.getString("Team"));
                            trophiesEditText.setText(obj.getString("Trophies"));*/
                            break;
                        }
                    }


                } else {
                    Toast.makeText(Activity_AthleteProfile.this, "No data for user: " + ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

}
