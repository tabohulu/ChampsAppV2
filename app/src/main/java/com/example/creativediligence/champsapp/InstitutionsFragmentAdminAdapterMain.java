package com.example.creativediligence.champsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class InstitutionsFragmentAdminAdapterMain extends RecyclerView.Adapter<InstitutionsFragmentAdminAdapterMain.MyViewHolder> {
    ArrayList<String> mData ;

    Context mContext;
    int mId;
    String mCurrentFragment;

    public InstitutionsFragmentAdminAdapterMain(Context context, int resourceId, ArrayList<String> data, String currentFragment) {
        mData = data;
        mContext = context;
        mId = resourceId;
        mCurrentFragment=currentFragment;


    }

    @Override
    public InstitutionsFragmentAdminAdapterMain.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(mId, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull InstitutionsFragmentAdminAdapterMain.MyViewHolder holder, final int position) {
        holder.mTextView.setText(mData.get(position));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentValue = mData.get(position);
                Log.d("CardView", "CardView Clicked: " + currentValue);
                Toast.makeText(mContext, "CardView Clicked: " + currentValue, Toast.LENGTH_SHORT).show();

                if(mCurrentFragment.equals("Institutions")) {
                    Intent intent = new Intent(mContext, ToInstitutionDetailsActivity.class);
                    intent.putExtra("institutionName", mData.get(position));
                    mContext.startActivity(intent);
                }else if(mCurrentFragment.equals("Institution Managers")){
                    Toast.makeText(mContext, "Nothing Done Yet", Toast.LENGTH_SHORT).show();
                }


            }
        });
/*holder.settingsIconView.setVisibility(View.INVISIBLE);
        holder.settingsIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = mData.get(position);
                Toast.makeText(mContext, currentValue+ " settings icon clicked", Toast.LENGTH_SHORT).show();
            }
        });*/

        holder.deleteIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String currentValue = mData.get(position);
                //Toast.makeText(mContext, currentValue+ " delete icon clicked", Toast.LENGTH_SHORT).show();
                LayoutInflater li = LayoutInflater.from(mContext);
                final View textView = li.inflate(R.layout.delete_confirm_layout,null);

                AlertDialog.Builder deleteConfirmDialog= new AlertDialog.Builder(mContext,R.style.MyDialogTheme);
                deleteConfirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String serverClass="";
                        String key="";
                        if(mCurrentFragment.equals("Institutions")){
                            serverClass="Institution";
                            key="Name";
                        }else if(mCurrentFragment.equals("Institution Managers")){
                            serverClass="InstitutionManager";
                            key="Name";
                        }
                        ParseQuery<ParseObject> deleteQuery = new ParseQuery<ParseObject>(serverClass);
                        deleteQuery.whereEqualTo(key,currentValue);
                        deleteQuery.setLimit(1);
                        deleteQuery.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if(e==null && objects.size()>0){
                                    for(ParseObject ob :objects){
                                        ob.deleteInBackground(new DeleteCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                mData.remove(position);
                                                notifyDataSetChanged();
                                                Toast.makeText(mContext, "Delete Complete", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                }else{
                                    if (e != null) {
                                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(mContext, "No Data Exists", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        });
                        //Toast.makeText(mContext, currentValue+" Deleted.", Toast.LENGTH_SHORT).show();

                    }
                })
                        .setNegativeButton("No",null)
                .setTitle("Delete Item/"+currentValue);
                deleteConfirmDialog.setView(textView);
                AlertDialog alertDialog=deleteConfirmDialog.create();
                alertDialog.show();
            }
        });

        //ToDo: Delete group on long press
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public TextView auTextView;
        public ImageView iconView;
        public ImageView settingsIconView;
        public ImageView deleteIconView;

        public MyViewHolder(View v) {
            super(v);

            mCardView = v.findViewById(R.id.card_view_aux);
            mTextView = v.findViewById(R.id.athletes_name_textview);
            auTextView = v.findViewById(R.id.athlete_personal_best);
            //settingsIconView=v.findViewById(R.id.settings_icon_view);
            deleteIconView = v.findViewById(R.id.delete_icon_view);
            iconView = v.findViewById(R.id.sub_event_image);
            iconView.setAdjustViewBounds(true);
            iconView.setMaxHeight(100);
            iconView.setMaxWidth(100);

        }


    }
}
