package com.example.creativediligence.champsapp;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.creativediligence.champsapp.SettingsActivity.getDataColumn;
import static com.example.creativediligence.champsapp.SettingsActivity.isDownloadsDocument;
import static com.example.creativediligence.champsapp.SettingsActivity.isExternalStorageDocument;
import static com.example.creativediligence.champsapp.SettingsActivity.isGooglePhotosUri;
import static com.example.creativediligence.champsapp.SettingsActivity.isMediaDocument;

public class ProfileFragment extends Fragment {

    private static final int PICK_PHOTO_FOR_AVATAR = 1203;

    boolean nameEditPressed;
    SharedPreferences prefs;
    EditText nameText;
    FloatingActionButton nameEditButton;
    TextView aboutMe;
    TextView bioAndStats;
    TextView experience;
    TextView team1;
    TextView team2;


    EditText aboutMeEditText;
    EditText experienceEditText;
    EditText teams1EditText;
    EditText teams2EditText;
    EditText statsDob;
    EditText statsAge;
    EditText statsHeight;
    EditText statsWeight;
    EditText statsSkils;

    ImageView profileImage;


    RecyclerView biosRecyclerView;

    Uri imageUri;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }


        return null;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
       /* View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }*/
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        //imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nameEditPressed = false;

        prefs = this.getActivity().getSharedPreferences("com.example.creativediligence.champsapp", MODE_PRIVATE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.personal_profile_fragment_layout, container, false);


        nameText = rootView.findViewById(R.id.profile_name_textview);
        bioAndStats = rootView.findViewById(R.id.bio_and_stats_TextView);
        aboutMe = rootView.findViewById(R.id.aboutMe_tv);
        aboutMeEditText = rootView.findViewById(R.id.profile_aboutme_textview);
        experienceEditText = rootView.findViewById(R.id.experience_edittext);
        teams1EditText = rootView.findViewById(R.id.teams1);
        teams2EditText = rootView.findViewById(R.id.teams2);
        profileImage = rootView.findViewById(R.id.profile_image);
        /*profileImage.setAdjustViewBounds(true);
        profileImage.setMaxHeight(200);
        profileImage.setMaxWidth(500);*/

        experience = rootView.findViewById(R.id.experience);
        team1 = rootView.findViewById(R.id.teams1_title);
        team2 = rootView.findViewById(R.id.teams2_title);

        statsDob = rootView.findViewById(R.id.statsDob);
        statsAge = rootView.findViewById(R.id.statsAge);
        statsHeight = rootView.findViewById(R.id.statsHeight);
        statsSkils = rootView.findViewById(R.id.statsSkills);
        statsWeight = rootView.findViewById(R.id.statsWeight);

        GetUserProfileData();
        nameEditButton = rootView.findViewById(R.id.profile_edit_button);

        nameEditButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                NameEditTextEdit();
            }
        });

        aboutMeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OtherEditText(aboutMeEditText);

            }
        });


        experienceEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherEditText(experienceEditText);
            }
        });

        teams1EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherEditText(teams1EditText);
            }
        });

        teams2EditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherEditText(teams2EditText);
            }
        });

        statsDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherEditText(statsDob);
            }
        });

        statsAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherEditText(statsAge);
            }
        });

        statsHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherEditText(statsHeight);
            }
        });

        statsWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherEditText(statsWeight);
            }
        });

        statsSkils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherEditText(statsSkils);
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameEditPressed) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
                }

            }
        });

        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                Uri uri = data.getData();
                File file = new File(uri.toString());


                imageUri = uri;
                profileImage.setImageURI(uri);
                //Toast.makeText(this, (file.getAbsolutePath()+" / "+getPathFromUri(this,uri)), Toast.LENGTH_SHORT).show();
                /*ParseObject userProfiles =new ParseObject(getResources().getString(R.string.UserProfilesClassName));
                userProfiles.put(getResources().getString(R.string.memberProfileImage),getPathFromUri(getContext(),uri));
                userProfiles.saveInBackground();*/
                prefs.edit().putString(getResources().getString(R.string.memberProfileImage), getPathFromUri(getContext(), uri)).apply();
                //InputStream inputStream = this.getContentResolver().openInputStream(data.getData());

            } catch (Exception e) {
                //Toast.makeText(e.getMessage(), this, Toast.LENGTH_SHORT).show();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void NameEditTextEdit() {
        if (nameEditPressed == false) {
            nameText.setClickable(true);
            nameText.setCursorVisible(true);
            nameText.setFocusable(true);
            nameText.setFocusableInTouchMode(true);
            nameText.requestFocus();
            //nameEditButton.setText("SAVE");
            nameEditPressed = true;
            showKeyboard(getActivity());
            //BiosRVAdapter adapter = new BiosRVAdapter( getContext(),R.layout.biography_recyclerview_custom_layout,nameEditPressed,prefs);
            //biosRecyclerView.setAdapter(adapter);


        } else {
            nameText.setClickable(false);
            nameText.setCursorVisible(false);
            nameText.setFocusable(false);
            nameText.setFocusableInTouchMode(false);
            // nameEditButton.setText("EDIT");
            nameEditPressed = false;
            ArrayList<String> prefNames = new ArrayList<>(Arrays.asList("userName", "aboutMe", "experience", "teams1", "teams2", "statsAge", "statsDob", "statsSkills"
                    , "statsHeight", "statsWeight"));
            ArrayList<EditText> EditTexts = new ArrayList<>(Arrays.asList(nameText, aboutMeEditText, experienceEditText, teams1EditText, teams2EditText, statsAge,
                    statsDob, statsSkils, statsHeight, statsWeight));
            ArrayList<String> prefValues = new ArrayList<>();
            for (int i = 0; i < prefNames.size(); i++) {
                prefValues.add(EditTextStrings(EditTexts.get(i)));
            }


            prefNames.add("currentUserName");
            prefValues.add(ParseUser.getCurrentUser().getUsername());

            prefNames.add(getResources().getString(R.string.memberProfileImage));
            if (imageUri != null) {

                prefValues.add(getPathFromUri(getContext(), imageUri));
            } else {
                prefValues.add("");
            }

            new ParseQueries().SetUserProfileData(getContext(), prefNames, prefValues,this.getResources().getString(R.string.UserProfilesClassName));
            prefs.edit().putString("userName", nameText.getText().toString().trim()).apply();
            ExitEditText(aboutMeEditText, "aboutMe");
            ExitEditText(experienceEditText, "experience");
            ExitEditText(teams1EditText, "teams1");
            ExitEditText(teams2EditText, "teams2");
            ExitEditText(statsAge, "statsAge");
            ExitEditText(statsDob, "statsDob");
            ExitEditText(statsSkils, "statsSkills");
            ExitEditText(statsHeight, "statsHeight");
            ExitEditText(statsWeight, "statsWeight");
            //AddProfileImageAndCurrentUser();
            hideKeyboard(getActivity());
        }
    }

    public void OtherEditText(EditText editText) {
        if (nameEditPressed) {

            editText.setClickable(true);
            editText.setCursorVisible(true);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                    | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        } else {
           /* editText.setClickable(false);
            editText.setCursorVisible(false);
            editText.setFocusable(false);
            editText.setFocusableInTouchMode(false);
            nameEditPressed = false;
            prefs.edit().putString(prefName, editText.getText().toString().trim()).apply();*/
        }
    }

    public String EditTextStrings(EditText editText) {

        return editText.getText().toString().trim();
    }

    public void ExitEditText(EditText editText, String prefName) {

        editText.setClickable(false);
        editText.setCursorVisible(false);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        nameEditPressed = false;

        /*ParseObject userProfiles =new ParseObject(getResources().getString(R.string.UserProfilesClassName));
        userProfiles.put(prefName,editText.getText().toString().trim());
        userProfiles.saveInBackground();*/
        prefs.edit().putString(prefName, editText.getText().toString().trim()).apply();
    }


    public void GetUserProfileData() {

        ParseQuery<ParseObject> profileData = new ParseQuery<ParseObject>(this.getResources().getString(R.string.UserProfilesClassName));
        profileData.whereEqualTo("currentUserName", ParseUser.getCurrentUser().getUsername());
        profileData.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {

                    for (ParseObject ob : objects) {
                        SetPreferences(ob);
                    }

                } else {

                }
            }
        });

    }

    public void SetPreferences(ParseObject ob) {
        nameText.setText(ob.getString("userName"));
        aboutMeEditText.setText(ob.getString("aboutMe"));
        experienceEditText.setText(ob.getString("experience"));
        teams1EditText.setText(ob.getString("teams1"));
        teams2EditText.setText(ob.getString("teams2"));
        statsDob.setText(ob.getString("statsDob"));
        statsAge.setText(ob.getString("statsAge"));
        statsHeight.setText(ob.getString("statsHeight"));
        statsSkils.setText(ob.getString("statsSkills"));
        statsWeight.setText(ob.getString("statsWeight"));
        String imageuri = ob.getString(getResources().getString(R.string.memberProfileImage));
        if(!imageuri.isEmpty()) {
            Uri imageURI = Uri.parse(imageuri);
            try {
                profileImage.setImageURI(imageURI);
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

       /* if (prefs.contains("aboutMe")) {
            aboutMeEditText.setText(prefs.getString("aboutMe", null));
        }*/

        /*if (prefs.contains("experience")) {
            experienceEditText.setText(prefs.getString("experience", null));
        }*/

       /* if (prefs.contains("teams1")) {
            teams1EditText.setText(prefs.getString("teams1", null));
        }*/

        /*if (prefs.contains("teams2")) {
            teams2EditText.setText(prefs.getString("teams2", null));
        }*/

        /*if (prefs.contains("statsDob")) {
            statsDob.setText(prefs.getString("statsDob", null));
        }*/

        /*if (prefs.contains("statsAge")) {
            statsAge.setText(prefs.getString("statsAge", null));
        }*/

       /* if (prefs.contains("statsHeight")) {
            statsHeight.setText(prefs.getString("statsHeight", null));
        }*/

        /*if (prefs.contains("statsSkills")) {
            statsSkils.setText(prefs.getString("statsSkills", null));
        }*/

        /*if (prefs.contains("memberProfileImage")) {
            String imageuri = prefs.getString("memberProfileImage", null);
            Uri imageURI = Uri.parse(imageuri);
            try {
                profileImage.setImageURI(imageURI);
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }*/
    }

}
