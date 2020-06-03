package com.example.creativediligence.champsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Activity_HomePage extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView buttonsRV;
    ArrayList<Helper_BasicDataModel> data;
    private RecyclerView.LayoutManager layoutManager;
    final static String TAG = Activity_HomePage.class.getSimpleName();
    //Chat variables
    static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    static final int POLL_INTERVAL = 1000; // milliseconds
    private static final int REQUEST_WRITE_PERMISSION = 786;
    ArrayList<String> files;
    SharedPreferences prefs;

    @Override
    protected void onResume() {
        if (files != null && files.size() > 0) {
            SetupVideo(files.get(0));
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        prefs = getSharedPreferences("com.example.creativediligence.champsapp", MODE_PRIVATE);

        requestPermission();

        //initialize xml elements
        InitializeXMLElements();

        //setup recycler buttons
        SetupRecyclerViewButtons();
    }

    public void InitializeXMLElements() {
        toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        buttonsRV=findViewById(R.id.buttons_recyclerview);
        buttonsRV.setHasFixedSize(true);
    }

    public void SetupRecyclerViewButtons(){
        layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        buttonsRV.setLayoutManager(layoutManager);
        buttonsRV.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<>();
        for (int i = 0; i < Helper_MyData.homePageButtons.length; i++) {
            data.add(new Helper_BasicDataModel(
                    Helper_MyData.homePageButtons[i],
                    Helper_MyData.homePageImages[i]
            ));
        }
        //Toast.makeText(this, ""+data.size(), Toast.LENGTH_LONG).show();
        Adapter_Homepage homeButtonsAdapter = new Adapter_Homepage(data, Activity_HomePage.this);
        buttonsRV.setAdapter(homeButtonsAdapter);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Exists", Toast.LENGTH_LONG).show();
            DownloadVids();
        } else {
            Toast.makeText(this, "Permission Denied, closing App", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void requestPermission() {
        if (permissionAlreadyGranted()) {
            DownloadVids();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
            } else {
                DownloadVids();
            }
        }
    }

    private boolean permissionAlreadyGranted() {

        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void DownloadVids() {
        String myfolder = Environment.getExternalStorageDirectory() + "/NicheSports";
        File appFolder = new File(myfolder);
        if (!appFolder.exists()) {
            try {
                appFolder.mkdir();
                //Toast.makeText(this, "Folder Created", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }

        files = new ArrayList<>();

        ParseQuery<ParseObject> videos = new ParseQuery<ParseObject>("Videos");
        videos.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject objs : objects) {
                        ParseFile videoFile = objs.getParseFile("videofile");
                        files.add(videoFile.getUrl());
                    }
                    SetupVideo(files.get(0));
                } else {
                    if (e.getCode() == ParseException.INVALID_SESSION_TOKEN) {
                        Toast.makeText(Activity_HomePage.this, "Session expired or User deleted. Please signup", Toast.LENGTH_LONG).show();
                        prefs.edit().putBoolean("signedup", false).apply();
                        prefs.edit().putBoolean("loggedin", false).apply();
                        Intent intent = new Intent(Activity_HomePage.this, Activity_Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        Activity_HomePage.this.startActivity(intent);
                        finish();
                    }
                    // Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


//        Log.i("Files", files.get(0));
    }

    public void SetupVideo(String url) {
        Uri uri = Uri.parse(url);
        VideoView vidView = findViewById(R.id.vid_view);
        vidView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        vidView.setMediaController(mediaController);
        //vidView.start();
        Toast.makeText(this, "Vid Success", Toast.LENGTH_LONG).show();
    }
}
