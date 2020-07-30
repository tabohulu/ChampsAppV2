package com.example.creativediligence.champsapp;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.List;

public class CalendarEventsDetailsActivity extends AppCompatActivity {

    EditText eventNameEditText;
    TextView eventStartTimeTextView;
    TextView eventEndTimeTextView;
    EditText eventTypeEditText;

    Button eventDeleteButton;
    Button eventSaveButton;

    String eventDate;
    String mEventName ;
    String mEventStart;
    String mEventEnd;
    String mEventType;

    public static void HideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_events_details);
        eventDate = getIntent().getStringExtra("eventDate");

        //Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Initialize xml Elements
        eventNameEditText = findViewById(R.id.event_name_textView);
        eventStartTimeTextView = findViewById(R.id.event_startTime);
        eventEndTimeTextView = findViewById(R.id.event_endTime);
        eventTypeEditText = findViewById(R.id.event_type);

        eventSaveButton = findViewById(R.id.event_save_button);
        eventDeleteButton = findViewById(R.id.event_delete_button);

        //Get Extra Data if it exists
        try {
            mEventName = getIntent().getStringExtra("eventName");
            if(mEventName==null){
                mEventName="";
            }
            eventNameEditText.setText(mEventName);
            mEventStart = getIntent().getStringExtra("eventStart");
            if(mEventStart==null){
                mEventStart="";
            }
            eventStartTimeTextView.setText(mEventStart);
            mEventEnd = getIntent().getStringExtra("eventEnd");
            if(mEventEnd==null){
                mEventEnd="";
            }
            eventEndTimeTextView.setText(mEventEnd);
            mEventType = getIntent().getStringExtra("eventType");
            if(mEventType==null){
                mEventType="";
            }
            eventTypeEditText.setText(mEventType);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //OnClick Listeneres
        eventDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CalendarEventsDetailsActivity.this, "Event Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        eventSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String eventName = eventNameEditText.getText().toString().trim();
                final String eventStart = eventStartTimeTextView.getText().toString().trim();
                final String eventEnd = eventEndTimeTextView.getText().toString().trim();
                final String eventType = eventTypeEditText.getText().toString().trim();

                //First Layer of Checks for undesired errors
                if (eventName.equals("") || eventStart.equals("") || eventEnd.equals("") || eventType.equals("")) {
                    Toast.makeText(CalendarEventsDetailsActivity.this, "Empty Inputs Available", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (eventStart.equals(eventEnd)) {
                    Toast.makeText(CalendarEventsDetailsActivity.this, "Choose Different Times For Start and End", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(eventStart.substring(0, 1)) > Integer.parseInt(eventEnd.substring(0, 1))) {
                    Toast.makeText(CalendarEventsDetailsActivity.this, "End Time Earlier Than Start Time", Toast.LENGTH_SHORT).show();
                    return;
                }

                ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("MasterCalendar");
                query.whereEqualTo("eventName",eventName);
                query.setLimit(1);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        Log.d("TAG=Name",eventName);
                        if(e==null & objects.size()>0){
                            Toast.makeText(CalendarEventsDetailsActivity.this, "Event With Name "+ eventName +" Already Exists", Toast.LENGTH_SHORT).show();
                        }else{
                            if(e!=null){
                                Toast.makeText(CalendarEventsDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }else{
                                 UploadData(eventName,eventStart,eventEnd,eventType);
                            }


                        }
                    }
                });

            }
        });


        eventStartTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeSelector(eventStartTimeTextView);
            }
        });

        eventEndTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeSelector(eventEndTimeTextView);
            }
        });


    }

    public void UploadData(String eventName,String eventStart,String eventEnd,String  eventType){
        ParseObject calendarEvent = new ParseObject("MasterCalendar");
        Log.d("TAG=Name",mEventName+"/"+eventStart+"/"+eventEnd+"/"+eventType);

        if (!mEventName.equals(eventName) || !mEventStart.equals(eventStart) || !mEventEnd.equals(eventEnd) || !mEventType.equals(eventType)) {
            calendarEvent.put("eventName", eventName);
            calendarEvent.put("eventStart", eventStart);
            calendarEvent.put("eventEnd", eventEnd);
            calendarEvent.put("eventType", eventType);
            calendarEvent.put("eventDate", eventDate);

            calendarEvent.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(CalendarEventsDetailsActivity.this, "Event Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CalendarEventsDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            Toast.makeText(CalendarEventsDetailsActivity.this, "Same Data, Nothing Saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void TimeSelector(final TextView et) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mStartTimePickerDialog;
        mStartTimePickerDialog = new TimePickerDialog(CalendarEventsDetailsActivity.this,
                R.style.MyDialogTheme,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        et.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
        mStartTimePickerDialog.setTitle("Pick Start Time");
        mStartTimePickerDialog.show();
    }
}
