package com.example.creativediligence.champsapp.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.creativediligence.champsapp.R;
import com.example.creativediligence.champsapp.VideoDataRecyclerViewAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseServerMethods {
    Context mContext;
    ProgressDialog progressDialog;

    public ParseServerMethods(Context context) {
        mContext = context;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Its loading....");
        progressDialog.setTitle("ProgressDialog bar example");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    private byte[] ConvertVidToBytes(File videoFile) throws IOException {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(videoFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[(int) videoFile.length()];

        for (int readNum; (readNum = fis.read(buf)) != -1; ) {
            bos.write(buf, 0, readNum);
        }
        return bos.toByteArray();
    }

    public void UploadVideo(final String serverClass, final File videoFile, final String userName, final RecyclerView vidsRv, final String currentFragment) {


        final ProgressDialog progressDialog = new ProgressDialog(mContext,R.style.MySpinnerTheme);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Adding Video....");
        progressDialog.setTitle("Uploading "+videoFile.getName());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        byte[] bytes = new byte[0];
        try {
            bytes = ConvertVidToBytes(videoFile);
        } catch (IOException e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        ParseFile file = new ParseFile("TestVideo1.mp4", bytes);
        ParseObject vidObject = new ParseObject(serverClass);
        vidObject.put("videofile", file);
        vidObject.put("filename", videoFile.getName());
        vidObject.put("uploadedBy", userName);
        file.saveInBackground(new ProgressCallback() {
            @Override
            public void done(Integer percentDone) {
                progressDialog.setProgress(percentDone);
                if (percentDone == 100) {
                    progressDialog.setMessage("Final Touches.........");
                }
            }
        });


        vidObject.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(mContext, videoFile.getName() + " uploaded", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    DownloadVideo("Videos", "uploadedBy", ParseUser.getCurrentUser().getUsername(), vidsRv, currentFragment);
                } else {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void DownloadVideo(String serverClass, String searchKey, String searchKeyValue, final RecyclerView rv, final String currentFragment) {
        /*final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMax(100);
        progressDialog.setMessage("Updating List....");
        progressDialog.setTitle("ProgressDialog bar example");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();*/
        final ArrayList<String> vidNames = new ArrayList<>();
        final ArrayList<String> vidURLs = new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(serverClass);
        query.whereEqualTo(searchKey, searchKeyValue);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject ob : objects) {
                        String f = ob.getString("filename");
                        vidNames.add(f);
                        ParseFile videoFile = ob.getParseFile("videofile");
                        vidURLs.add(videoFile.getUrl());
                    }
                    //progressDialog.dismiss();
                    VideoDataRecyclerViewAdapter adapter = new VideoDataRecyclerViewAdapter(mContext, R.layout.home_fragment_layout_custom, vidNames, currentFragment, vidURLs);
                    rv.setAdapter(adapter);
                    LinearLayoutManager llm = new LinearLayoutManager(mContext);
                    rv.setLayoutManager(llm);
                } else {
                    if (e != null) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
