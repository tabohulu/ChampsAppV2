package com.example.creativediligence.champsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.roomorama.caldroid.CaldroidFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Helper_DialogCreator {
    static String TAG = "DialogCreator";
    View pos = null;
    int posi = -1;
    int counter = 0;
    Context mContext;
    int thingsAdded;


    public void NewEventDialog(final Context mContext, final RecyclerView rv, final TextView infoTv) {
        final ParseUser currentuser = ParseUser.getCurrentUser();
        LayoutInflater myLayout = LayoutInflater.from(mContext);
        final View dialogView = myLayout.inflate(R.layout.new_event_dialog_layout, null);
        final EditText newEventName = dialogView.findViewById(R.id.new_event_name);
        final Spinner eventSport =dialogView.findViewById(R.id.event_sport_spinner);
        final RecyclerView participatingTeamsRv =dialogView.findViewById(R.id.participating_teams_rv);
        final TextView affiliateTeam=dialogView.findViewById(R.id.affiliateInstuTv);
        final DatePicker eventDate=dialogView.findViewById(R.id.event_datepicker);
        final TimePicker eventTime =dialogView.findViewById(R.id.event_timepicker);
        eventTime.setIs24HourView(true);
        affiliateTeam.setText(currentuser.getUsername());
        final String[] dateString = new String[1];
        final String[] timeString = new String[1];



        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext, R.style.MyDialogTheme);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String eventName = newEventName.getText().toString();
                final String selectedEventSport=eventSport.getSelectedItem().toString();

                 dateString[0] =eventDate.getDayOfMonth()+"/"+(eventDate.getMonth()+1)+"/"+eventDate.getYear();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    eventDate.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                            dateString[0]=dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                        }
                    });
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    timeString[0] =eventTime.getHour()+":"+eventTime.getMinute();
                }

                eventTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                        @Override
                        public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                            timeString[0] =hourOfDay+":"+minute;
                        }
                    });

                if (eventName.trim().matches("")) {
                    Toast.makeText(mContext, "Nothing Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Something Saved", Toast.LENGTH_SHORT).show();
                    //InstitutionsCoachesFragment.mData.add(coachName);


                    ParseObject theEvent = new ParseObject("InstitutionEvent");
                    theEvent.put("mEventName", eventName);
                    theEvent.put("createdBy", currentuser.getUsername());
                    theEvent.put("eventSport",selectedEventSport);
                    theEvent.put("eventDate", dateString[0]);
                    theEvent.put("eventTime", timeString[0]);
                    //coach.put("teamType",assignedTeam.getSelectedItem().toString());
                    theEvent.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.d(TAG, "sucess");
                                Toast.makeText(mContext, " New Event " + eventName + " created!", Toast.LENGTH_SHORT).show();
                                PopulateRecyclerViewEvent(rv,infoTv,mContext);
                            } else {
                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // prefs.edit().putStringSet("groups", new HashSet<String>(GroupsFragment.mData)).apply();


                }
            }
        }).setNegativeButton("Cancel", null);
        alertDialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void NewInstitutionDialog(final Context mContext, final RecyclerView rv, final TextView infoTv, final String currentFragment) {

        LayoutInflater myLayout = LayoutInflater.from(mContext);
        final View dialogView = myLayout.inflate(R.layout.new_institution_admin_dialog_layout, null);

        final Button uploadExcelButton =dialogView.findViewById(R.id.upload_excel_file);
        // final RecyclerView rv = dialogView.findViewById(R.id.friends_rv);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext, R.style.MyDialogTheme);

        alertDialogBuilder
                .setNegativeButton("Cancel", null);
        alertDialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        uploadExcelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
                Toast.makeText(mContext, "Button Pressed", Toast.LENGTH_SHORT).show();
                new FileExploreDialog().OpenDialog(mContext,currentFragment,rv,infoTv);
                //PopulateRecyclerViewAthlete(rv,infoTv,mContext);

            }
        });
        alertDialog.show();
    }

    public void NewAthleteDialog(final Context mContext, final RecyclerView rv, final TextView infoTv,final String currentFragment) {

        LayoutInflater myLayout = LayoutInflater.from(mContext);
        final View dialogView = myLayout.inflate(R.layout.new_athlete_dialog_layout, null);
        final EditText newCoachName = dialogView.findViewById(R.id.new_team_name);
        final Button uploadExcelButton =dialogView.findViewById(R.id.upload_excel_file);

        final ParseUser currentuser = ParseUser.getCurrentUser();
        final TextView affiliateTeam=dialogView.findViewById(R.id.affiliateInstuTv);
        affiliateTeam.setText(currentuser.getUsername());
        // final RecyclerView rv = dialogView.findViewById(R.id.friends_rv);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext, R.style.MyDialogTheme);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String coachName = newCoachName.getText().toString();
                if (coachName.trim().matches("")) {
                    Toast.makeText(mContext, "Nothing Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Something Saved", Toast.LENGTH_SHORT).show();
                    //InstitutionsCoachesFragment.mData.add(coachName);


                    ParseObject coach = new ParseObject("InstitutionAthlete");
                    coach.put("athleteName", coachName);
                    coach.put("affiliateInstitution", currentuser.getUsername());
                    //coach.put("teamType",assignedTeam.getSelectedItem().toString());
                    coach.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.d(TAG, "sucess");
                                Toast.makeText(mContext, " New Athlete " + coachName + " created!", Toast.LENGTH_SHORT).show();
                                PopulateRecyclerViewAthlete(rv,infoTv,mContext,currentFragment);
                            } else {
                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // prefs.edit().putStringSet("groups", new HashSet<String>(GroupsFragment.mData)).apply();


                }
            }
        }).setNegativeButton("Cancel", null);
        alertDialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        uploadExcelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
                Toast.makeText(mContext, "Button Pressed", Toast.LENGTH_SHORT).show();
                new FileExploreDialog().OpenDialog(mContext,currentFragment,rv,infoTv);
                //PopulateRecyclerViewAthlete(rv,infoTv,mContext);

            }
        });
        alertDialog.show();
    }



    public void NewTeamDialog(final Context mContext, final RecyclerView rv, final TextView infoTv,final String currentFragment) {
        LayoutInflater myLayout = LayoutInflater.from(mContext);
        final View dialogView = myLayout.inflate(R.layout.new_team_dialog_layout, null);
        final EditText newCoachName = dialogView.findViewById(R.id.new_team_name);
        final Spinner assignedTeam =dialogView.findViewById(R.id.teams_spinner);
        final ParseUser currentuser = ParseUser.getCurrentUser();
        final TextView affiliateTeam=dialogView.findViewById(R.id.affiliateInstuTv);
        final Button uploadExcelButton =dialogView.findViewById(R.id.upload_excel_file);

        affiliateTeam.setText(currentuser.getUsername());
        // final RecyclerView rv = dialogView.findViewById(R.id.friends_rv);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext, R.style.MyDialogTheme);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String coachName = newCoachName.getText().toString();
                if (coachName.trim().matches("")) {
                    Toast.makeText(mContext, "Nothing Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Something Saved", Toast.LENGTH_SHORT).show();
                    //InstitutionsCoachesFragment.mData.add(coachName);


                    ParseObject coach = new ParseObject("InstitutionTeam");
                    coach.put("teamName", coachName);
                    coach.put("affiliateInstitute", currentuser.getUsername());
                    coach.put("teamType",assignedTeam.getSelectedItem().toString());
                    coach.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.d(TAG, "sucess");
                                Toast.makeText(mContext, " New Team " + coachName + " created!", Toast.LENGTH_SHORT).show();
                                PopulateRecyclerViewTeam(rv,infoTv,mContext,currentFragment);
                            } else {
                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // prefs.edit().putStringSet("groups", new HashSet<String>(GroupsFragment.mData)).apply();


                }
            }
        }).setNegativeButton("Cancel", null);
        alertDialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        uploadExcelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
                Toast.makeText(mContext, "Button Pressed", Toast.LENGTH_SHORT).show();
                new FileExploreDialog().OpenDialog(mContext,currentFragment,rv,infoTv);
                //PopulateRecyclerViewAthlete(rv,infoTv,mContext);

            }
        });
        alertDialog.show();
    }

    public void NewCoachDialog(final Context mContext, final RecyclerView rv, final TextView infoTv, final String currentFragment) {
        LayoutInflater myLayout = LayoutInflater.from(mContext);
        final View dialogView = myLayout.inflate(R.layout.new_coach_dialog_layout, null);
        final EditText newCoachName = dialogView.findViewById(R.id.new_coach_name);
        final Spinner assignedTeam =dialogView.findViewById(R.id.teams_spinner);
        final ParseUser currentuser = ParseUser.getCurrentUser();
        final TextView affiliateTeam=dialogView.findViewById(R.id.affiliateInstuTv);
        affiliateTeam.setText(currentuser.getUsername());
        final Button uploadExcelButton =dialogView.findViewById(R.id.upload_excel_file);
        // final RecyclerView rv = dialogView.findViewById(R.id.friends_rv);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext, R.style.MyDialogTheme);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String coachName = newCoachName.getText().toString();
                if (coachName.trim().matches("")) {
                    Toast.makeText(mContext, "Nothing Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Something Saved", Toast.LENGTH_SHORT).show();
                    //InstitutionsCoachesFragment.mData.add(coachName);


                    ParseObject coach = new ParseObject("InstitutionCoach");
                    coach.put("coachName", coachName);
                    coach.put("affiliateTeam", currentuser.getUsername());
                    coach.put("assignedTeam",assignedTeam.getSelectedItem().toString());
                    coach.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.d(TAG, "sucess");
                                Toast.makeText(mContext, " New Coach " + coachName + " created!", Toast.LENGTH_SHORT).show();
                                PopulateRecyclerViewCoach(rv,infoTv,mContext,currentFragment);
                            } else {
                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // prefs.edit().putStringSet("groups", new HashSet<String>(GroupsFragment.mData)).apply();


                }
            }
        }).setNegativeButton("Cancel", null);
        alertDialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        uploadExcelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
                Toast.makeText(mContext, "Button Pressed", Toast.LENGTH_SHORT).show();
                new FileExploreDialog().OpenDialog(mContext,currentFragment,rv,infoTv);
                //PopulateRecyclerViewCoach(rv,infoTv,mContext);

            }
        });
        alertDialog.show();
    }

    public void PopulateRecyclerViewEvent(final RecyclerView rv,final TextView infoTv,final Context mContext) {
        final ArrayList<String> mData=new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("InstitutionEvent");
        query.whereEqualTo("createdBy", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    List<ParseObject> storedData=objects;
                    for (ParseObject ob : objects) {
                        mData.add(ob.getString("mEventName"));
                    }

                    infoTv.setVisibility(View.GONE);

                    //rv = (RecyclerView) rootview.findViewById(R.id.instu_coach_fragment_rv);
                    rv.setHasFixedSize(true);

                    EventsFragmentAdapterMain adapter = new EventsFragmentAdapterMain(mContext, R.layout.home_fragment_layout_custom, mData,storedData);
                    rv.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(mContext);
                    rv.setLayoutManager(llm);

                } else {
                    if(e!=null) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext, "No Data Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void PopulateRecyclerViewAthlete(final RecyclerView rv, final TextView infoTv, final Context mContext, final String currentFragment) {
        final ArrayList<String> mData=new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("InstitutionAthlete");
        query.whereEqualTo("Institution", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject ob : objects) {
                        mData.add(ob.getString("Name"));
                    }

                    infoTv.setVisibility(View.GONE);

                    //rv = (RecyclerView) rootview.findViewById(R.id.instu_coach_fragment_rv);
                    rv.setHasFixedSize(true);

                   InstitutionAthleteRecylerviewAdapter adapter = new InstitutionAthleteRecylerviewAdapter(mContext, R.layout.home_fragment_layout_custom, mData,currentFragment);
                    rv.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(mContext);
                    rv.setLayoutManager(llm);

                } else {
                    if(e!=null) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext, "No Data Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


   public void PopulateRecyclerViewTeam(final RecyclerView rv, final TextView infoTv, final Context mContext, final String currentFragment) {
        final ArrayList<String> mData=new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("InstitutionTeam");
        query.whereEqualTo("affiliateInstitute", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject ob : objects) {
                        mData.add(ob.getString("teamName"));
                    }

                    infoTv.setVisibility(View.GONE);

                    //rv = (RecyclerView) rootview.findViewById(R.id.instu_coach_fragment_rv);
                    rv.setHasFixedSize(true);

                    CoachesFragmentAdapterMain adapter = new CoachesFragmentAdapterMain(mContext, R.layout.home_fragment_layout_custom, mData,currentFragment);
                    rv.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(mContext);
                    rv.setLayoutManager(llm);

                } else {
                    if(e!=null) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext, "No Data Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }

    public void PopulateRecyclerViewCoach(final RecyclerView rv, final TextView infoTv, final Context mContext, final String currentFragment) {
final ArrayList<String> mData=new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("InstitutionCoach");
        query.whereEqualTo("Institution", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject ob : objects) {
                        mData.add(ob.getString("Name"));
                    }

                    infoTv.setVisibility(View.GONE);

                    //rv = (RecyclerView) rootview.findViewById(R.id.instu_coach_fragment_rv);
                    rv.setHasFixedSize(true);

                   CoachesFragmentAdapterMain adapter = new CoachesFragmentAdapterMain(mContext, R.layout.home_fragment_layout_custom, mData,currentFragment);
                    rv.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(mContext);
                    rv.setLayoutManager(llm);

                } else {
                    if(e!=null) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext, "No Data Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }

    public void NewGroupDialog(final Context mContext) {
        ArrayList<String> friends = new ArrayList<>(Arrays.asList("Friend1", "Friend2"));
        LayoutInflater myLayout = LayoutInflater.from(mContext);
        final View dialogView = myLayout.inflate(R.layout.new_group_dialog_layout, null);
        final TextView newGroupName = dialogView.findViewById(R.id.new_group_name);
        // final RecyclerView rv = dialogView.findViewById(R.id.friends_rv);
        PopulateRecyclerView(dialogView, mContext, friends);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext, R.style.MyDialogTheme);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String groupName = newGroupName.getText().toString();
                if (groupName.trim().matches("")) {
                    Toast.makeText(mContext, "Nothing Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Something Saved", Toast.LENGTH_SHORT).show();

                    GroupsFragment.mData.add(groupName);
                    ParseUser currentuser = ParseUser.getCurrentUser();
                    ParseObject groups = new ParseObject("Groups");
                    groups.put("groupName", groupName);
                    groups.put("userName", currentuser.getUsername());
                    groups.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.d(TAG, "sucess");
                                Toast.makeText(mContext, " New Group " + groupName + " created!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    // prefs.edit().putStringSet("groups", new HashSet<String>(GroupsFragment.mData)).apply();


                }
            }
        }).setNegativeButton("Cancel", null);
        alertDialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void NewPostDialog(final Context mContext, final String groupName, final RecyclerView rv) {

        LayoutInflater myLayout = LayoutInflater.from(mContext);
        final View dialogView = myLayout.inflate(R.layout.new_post_custom_layout, null);
        final EditText newPost = dialogView.findViewById(R.id.new_post);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext, R.style.MyDialogTheme);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String postContent = newPost.getText().toString();
                if (postContent.trim().matches("")) {
                    Toast.makeText(mContext, "Nothing Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Something Saved", Toast.LENGTH_SHORT).show();
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(currentTime);

                    DateFormat date = new SimpleDateFormat("HH:mm a");
                    String localTime = date.format(currentTime);

                    ParseObject posts = new ParseObject(mContext.getResources().getString(R.string.GroupClassName));
                    posts.put(mContext.getResources().getString(R.string.GroupClassElem_groupName), groupName);
                    posts.put(mContext.getResources().getString(R.string.GroupClassElem_postCreator), ParseUser.getCurrentUser().getUsername());
                    posts.put(mContext.getResources().getString(R.string.GroupClassElem_postContent), postContent);
                    posts.put(mContext.getResources().getString(R.string.GroupClassElem_postDateTime), formattedDate + " " + localTime);
                    posts.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(mContext, "Post Created", Toast.LENGTH_LONG).show();
                                new ParseQueries().GetGroupData(mContext, groupName, rv);
                            } else {
                                Toast.makeText(mContext, "Unable to create post", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        }).setNegativeButton("Cancel", null)
                .setTitle("New Post");
        alertDialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    public void CalendarDialog(final Context mContext, String dayOfMonth, String dayOfWeek,
                               final ArrayList<CalendarEventsItem> eventsItems, final String eventDate, ProgressBar pbar, CaldroidFragment cf) {
        LayoutInflater myLayout = LayoutInflater.from(mContext);
        final View dialogView = myLayout.inflate(R.layout.calendar_custom_layout, null);
        Button createNewEvent =dialogView.findViewById(R.id.new_event_button);

        TextView tv =dialogView.findViewById(R.id.no_data_info_tv);




        RecyclerView rv=dialogView.findViewById(R.id.events_rv);
        rv.setHasFixedSize(true);

        if(eventsItems!=null ) {
            tv.setVisibility(View.INVISIBLE);
            CalendarEventsAdapter adapter = new CalendarEventsAdapter(mContext, R.layout.calendar_event_layout, eventsItems,pbar,cf);
            rv.setAdapter(adapter);
            LinearLayoutManager llm = new LinearLayoutManager(mContext);
            rv.setLayoutManager(llm);
        }

        TextView name = dialogView.findViewById(R.id.event_day);
        name.setText(dayOfWeek);
        TextView age = dialogView.findViewById(R.id.event_date);
        age.setText(dayOfMonth);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);
        alertDialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        createNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CalendarEventsDetailsActivity.class);
                intent.putExtra("eventDate",eventDate);
                mContext.startActivity(intent);
                alertDialog.dismiss();
            }
        });


        alertDialog.show();

    }

    /*public void DialogCreatorAthletes(final Context mContext, final AthleteModel mAthlete) {
        LayoutInflater myLayout = LayoutInflater.from(mContext);
        final View dialogView = myLayout.inflate(R.layout.athletes_listview_custom_layout, null);
        TextView name = dialogView.findViewById(R.id.athlete_name);
        name.setText(mAthlete.getmName());
        TextView age = dialogView.findViewById(R.id.athlete_age);
        Log.d(TAG, mAthlete.getmAge() + "/" + mAthlete.getmPb());
        age.setText(String.valueOf(mAthlete.getmAge()));
        TextView pb = dialogView.findViewById(R.id.athlete_PB);
        pb.setText(String.valueOf(mAthlete.getmPb()));
        TextView details = dialogView.findViewById(R.id.details_tv);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);
        alertDialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AthleteDetailsActivity.class);
                intent.putExtra("athleteName", mAthlete.getmName());
                Bundle bundle = new Bundle();

                mContext.startActivity(intent);
                alertDialog.dismiss();
            }
        });

    }*/

    /*public void CalendarTinz(Context mContext){
        Calendar cal = Calendar.getInstance();
        DialogFragment df =CaldroidFragment.newInstance("Cal",cal.get(Calendar.MONTH) + 1,cal.get(Calendar.YEAR));
        df.show();
    }*/


    /*public void MemAreaAuth(final Context mContext){
        LayoutInflater myLayout=LayoutInflater.from(mContext);
        final View dialogView =myLayout.inflate(R.layout.mem_area_auth_layout,null);
        final EditText authCodeInput=dialogView.findViewById(R.id.auth_code_edit);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext, R.style.MyDialogTheme);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((Activity)mContext).finish();
                    }
                })
        ;
        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("Auth");
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String auth=ParseUser.getCurrentUser().get("memAreaAuth").toString();
                String userAuth=authCodeInput.getText().toString();
                if (!auth.equals(userAuth)){
                    Toast.makeText(mContext,"empty string or wrong code entered",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(mContext,"Authentication Succesful",Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
    }*/

    /*public void DialogCreatorAthletes(final Context mContext, final AthleteModel mAthlete, int coach) {
        LayoutInflater myLayout = LayoutInflater.from(mContext);
        final View dialogView = myLayout.inflate(R.layout.athletes_listview_custom_layout, null);
        TextView name = dialogView.findViewById(R.id.athlete_name);
        name.setText(mAthlete.getmName());
        TextView age = dialogView.findViewById(R.id.athlete_age);
        Log.d(TAG, mAthlete.getmAge() + "/" + mAthlete.getmPb());
        age.setText(String.valueOf(mAthlete.getmAge()));
        TextView pb = dialogView.findViewById(R.id.athlete_PB);
        pb.setText(String.valueOf((int) Math.round(mAthlete.getmPb())));

        TextView pbTitle = dialogView.findViewById(R.id.athletes_PB_title);
        pbTitle.setText("Wins");
        TextView details = dialogView.findViewById(R.id.details_tv);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);
        alertDialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CoachDetailsActivity.class);
                intent.putExtra("athleteName", mAthlete.getmName());
                Bundle bundle = new Bundle();

                mContext.startActivity(intent);
                alertDialog.dismiss();
            }
        });
    }*/

    private void SaveBracketInBackGround(ParseObject po, final String eventString) {
        po.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(TAG, "sucess");
                    Toast.makeText(mContext, " added bracket for " + eventString, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /*public AlertDialog CreateAlertDialog(Context mContext) {
        LayoutInflater myLayout = LayoutInflater.from(mContext);
        final View dialogView = myLayout.inflate(R.layout.progress_layout, null);
        ProgressBar pb = dialogView.findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext, R.style.MyDialogTheme);
        alertDialogBuilder.setView(dialogView);

        return alertDialogBuilder.create();
    }*/

    public void QueryAthletes(final Context context, final String eventString, final String generalevenet, final int cards) {
        final String[] bracketString={""};
        final ArrayList<String> athletes = new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Athletes");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject obs : objects) {
                        athletes.add(obs.getString("name"));
                    }
                    mContext = context;
                    LayoutInflater myLayout = LayoutInflater.from(mContext);
                    final View dialogView = myLayout.inflate(R.layout.events_athletes_recyclerview, null);
                    RecyclerView rv = dialogView.findViewById(R.id.events_athletes_rv);
                    rv.setHasFixedSize(true);


                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                    rv.setLayoutManager(layoutManager);
                    final Adapter_DialogCreatorEventAthletes adapter = new Adapter_DialogCreatorEventAthletes(mContext, athletes, eventString, cards);
                    //PointsStandingAdapter adapter = new PointsStandingAdapter(pointData, cardPosition);
                    rv.setAdapter(adapter);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            mContext, R.style.MyDialogTheme);
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            counter = 0;
                            ParseQuery<ParseObject> bracketQuery = new ParseQuery<ParseObject>("Bracket");
                            final ParseObject bracket = new ParseObject("Bracket");
                            if (generalevenet.equals(eventString)) {
                                bracket.put("bracketname", eventString);
                                bracketString[0]=eventString;
                            } else {
                                bracket.put("bracketname", generalevenet + " " + eventString);
                                bracketString[0]=generalevenet + " " + eventString;
                            }
                            bracket.put("username", ParseUser.getCurrentUser().getUsername());
                            thingsAdded = 0;
                            for (int ij = 0; ij < athletes.size(); ij++) {
                                if (adapter.mData[ij] != "-1") {
                                    bracket.add("position", adapter.mData[ij]);
                                    bracket.add("athlete", athletes.get(ij));
                                    thingsAdded++;
                                }



                            }
                            Log.d(TAG,thingsAdded+"");
                            if(thingsAdded>0) {
                                //check if current posts already exist
                                bracketQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
                                bracketQuery.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {

                                        if (e == null & objects.size() > 0) {
                                            Log.d(TAG, thingsAdded + "A");
                                            for (ParseObject ob : objects) {


                                                if (ob.getString("bracketname").equals(bracketString[0])) {
                                                    counter++;
                                                    Toast.makeText(mContext, "bracket already exists for " + bracketString[0], Toast.LENGTH_SHORT).show();
                                                    break;
                                                }
                                            }
                                            if (counter < 1 && thingsAdded > 0) {
                                                SaveBracketInBackGround(bracket, bracketString[0]);

                                            }

                                        } else{
                                            if(e!=null){
                                                Toast.makeText(mContext, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(mContext, "No Data Exists", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            } else {
                                Log.d(TAG, thingsAdded + "C");
                                Toast.makeText(mContext, "Nothing Selected,Nothing Saved", Toast.LENGTH_SHORT).show();
                            }

                        }
                    })
                            .setNegativeButton("Cancel", null);
                    alertDialogBuilder.setView(dialogView);

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.setTitle("Add Athlete to your Bracket(Finals Only)");
                    alertDialog.show();
                } else {
                    Log.d(TAG, e.getMessage());
                }
            }
        });
    }

    public void PopulateRecyclerView(View rootview, Context mContext, ArrayList<String> mData) {

        RecyclerView rv = (RecyclerView) rootview.findViewById(R.id.friends_rv);
        rv.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv.setLayoutManager(layoutManager);

        FriendListAdapterMain adapter = new FriendListAdapterMain(mContext, R.layout.friends_custom_layout, mData);
        rv.setAdapter(adapter);


    }


}
