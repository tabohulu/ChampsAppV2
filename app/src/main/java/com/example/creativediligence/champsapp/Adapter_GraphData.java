package com.example.creativediligence.champsapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Adapter_GraphData extends RecyclerView.Adapter<Adapter_GraphData.MyViewHolder> {
    Context mContext;
    String[] mData;
    boolean mIsClickable;
    int times = 5;


    int editprofileState = 0;
    boolean profileEditState = false;
    DataPoint[] dataPoints= new DataPoint[10];
    private MyViewHolder viewHolder;
    private EditText[] dateEdit1;
    private EditText[] dateEdit2;
    private EditText[] timeEdit1;
    private EditText[] timeEdit2;
    private ArrayList<String> mAthleteNames;

    public Adapter_GraphData(Context context, String[] data, boolean isClikable) {

        mContext = context;
        mData = data;//events
        mIsClickable = isClikable;


    }

    public Adapter_GraphData(Context context) {

        mContext = context;
        dateEdit1 = new EditText[times];
        dateEdit2 = new EditText[times];
        timeEdit1 = new EditText[times];
        timeEdit2 = new EditText[times];

        for(int i=0;i<10;i++){
            dataPoints[i]=new DataPoint(i,1);
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter_GraphData.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview_edittext_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        viewHolder = holder;
        dateEdit1[position] = holder.mDateInput;
        dateEdit2[position] = holder.mDateInput2;
        timeEdit1[position] = holder.mTimeInput;
        timeEdit2[position] = holder.mTimeInput2;


        holder.mTextLabel.setText((2 * position + 1) + "");
        holder.mTextLabel2.setText((2 * position + 2) + "");

        final Calendar myCalendar = Calendar.getInstance();
        final int currYear = myCalendar.get(Calendar.YEAR);
        final int[] age = {0};
        final int curMonth = myCalendar.get(Calendar.MONTH);
        final int curDay = myCalendar.get(Calendar.DAY_OF_MONTH);


        holder.mDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextDatePicker date = new EditTextDatePicker(myCalendar, age[0], currYear, curDay, curMonth, holder.mDateInput);
                String etString = holder.mDateInput.getText().toString();
                String dobText = etString.trim();
                if (editprofileState == 1) {
                    Toast.makeText(mContext, "DateEdit" + (position + 1) + " pressed :/" + profileEditState, Toast.LENGTH_SHORT).show();

                    if (!dobText.equals("") && !dobText.equals("()")) {
                        String[] inputText = etString.substring(0, etString.indexOf("(")).split("/");
                        new DatePickerDialog(mContext, date, Integer.parseInt(inputText[0]), Integer.parseInt(inputText[1]) - 1,
                                Integer.parseInt(inputText[2].trim())).show();
                    } else {
                        new DatePickerDialog(mContext, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                }
            }
        });

        holder.mDateInput2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DOBEditClickInput(holder.mDateInput2);
                EditTextDatePicker date = new EditTextDatePicker(myCalendar, age[0], currYear, curDay, curMonth, holder.mDateInput2);
                String etString = holder.mDateInput2.getText().toString().trim();
                String dobText = etString.trim();
                if (editprofileState == 1) {
                    Toast.makeText(mContext, "DateEdit" + (position + 1) + " pressed :/" + profileEditState, Toast.LENGTH_SHORT).show();

                    if (!dobText.equals("") && !dobText.equals("()")) {
                        String[] inputText = etString.substring(0, etString.indexOf("(")).split("/");
                        new DatePickerDialog(mContext, date, Integer.parseInt(inputText[0]), Integer.parseInt(inputText[1]) - 1,
                                Integer.parseInt(inputText[2].trim())).show();
                    } else {
                        new DatePickerDialog(mContext, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                }
            }
        });

        holder.mTimeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    dataPoints[2 * position] = new DataPoint(2 * position, Double.parseDouble(editable.toString()));
                    if (mContext instanceof Activity_AthleteProfile2) {
                        ((Activity_AthleteProfile2) mContext).EditGraph();
                    }
                }
            }
        });

holder.mTimeInput2.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(!editable.toString().isEmpty()) {
            dataPoints[2 * position +1] = new DataPoint(2 * position+1, Double.parseDouble(editable.toString()));
            if (mContext instanceof Activity_AthleteProfile2) {
                ((Activity_AthleteProfile2) mContext).EditGraph();
            }
        }
    }
});
        //holder.mDataTitle.setText(mData[position]);2
       /*holder.mDataInput.setClickable(mIsClickable);
        holder.mDataInput.setFocusable(mIsClickable);
        holder.mDataInput.setFocusableInTouchMode(mIsClickable);*/


    }

    @Override
    public int getItemCount() {

        return times;
    }


    public void ChangeEditState(int editprofileState, boolean profileEditState) {

        this.editprofileState = editprofileState;
        this.profileEditState = profileEditState;
        for (int i = 0; i < dateEdit1.length; i++) {
            viewHolder.ChangeStates(dateEdit1[i], timeEdit1[i], dateEdit2[i], timeEdit2[i]);
        }


    }

    public LineGraphSeries<DataPoint> GetDataPoints(){

        return new LineGraphSeries<>(dataPoints);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {


        public EditText mTimeInput;
        public EditText mTimeInput2;
        public EditText mDateInput;
        public EditText mDateInput2;
        public TextView mTextLabel;
        public TextView mTextLabel2;

        public MyViewHolder(View v) {
            super(v);


            mTimeInput = v.findViewById(R.id.data_entry);
            mTimeInput2 = v.findViewById(R.id.data_entry2);

            mDateInput = v.findViewById(R.id.date_entry);
            mDateInput2 = v.findViewById(R.id.date_entry2);

            mTextLabel = v.findViewById(R.id.input_label);
            mTextLabel2 = v.findViewById(R.id.input_label2);


            ChangeStates(mDateInput, mTimeInput, mDateInput2, mTimeInput2);

        }

        public void ChangeStates(EditText ets, EditText time1, EditText ets2, EditText time2) {

            if (editprofileState == 1) {
                /*time1.setClickable(profileEditState);
                time1.setEnabled(profileEditState);*/

                ets.setClickable(profileEditState);
                ets.setEnabled(profileEditState);

                /*time2.setClickable(profileEditState);
                time2.setEnabled(profileEditState);*/

                ets2.setClickable(profileEditState);
                ets2.setEnabled(profileEditState);
            } else if (editprofileState == 0) {


                ets.setFocusable(profileEditState);
                ets.setClickable(profileEditState);
                ets.setFocusableInTouchMode(profileEditState);
                ets.setEnabled(profileEditState);


                ets2.setFocusable(profileEditState);
                ets2.setClickable(profileEditState);
                ets2.setFocusableInTouchMode(profileEditState);
                ets2.setEnabled(profileEditState);
            }
            time1.setFocusable(profileEditState);
            time1.setClickable(profileEditState);
            time1.setFocusableInTouchMode(profileEditState);
            time1.setEnabled(profileEditState);

            time2.setFocusable(profileEditState);
            time2.setClickable(profileEditState);
            time2.setFocusableInTouchMode(profileEditState);
            time2.setEnabled(profileEditState);

            Toast.makeText(mContext, "state: " + profileEditState + "//" + editprofileState, Toast.LENGTH_SHORT).show();

        }


    }

    class EditTextDatePicker implements DatePickerDialog.OnDateSetListener {
        Calendar myCalendar;
        int age;
        int currYear;
        int curDay;
        int curMonth;
        EditText dateEdit;

        public EditTextDatePicker(Calendar myCalendar, int age, int currYear, int curDay, int curMonth, EditText dateEdit) {
            this.myCalendar = myCalendar;
            this.age = age;
            this.currYear = currYear;
            this.curDay = curDay;
            this.curMonth = curMonth;
            this.dateEdit = dateEdit;
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy/MM/dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            age = currYear - year;
            if (curMonth < monthOfYear && curDay < dayOfMonth) {
                age = currYear - year + 1;
            }
            dateEdit.setText(sdf.format(myCalendar.getTime()) + " (" + age + ")");

        }
    }
}
