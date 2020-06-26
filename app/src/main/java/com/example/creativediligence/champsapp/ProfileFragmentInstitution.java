package com.example.creativediligence.champsapp;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

import static com.example.creativediligence.champsapp.SettingsActivity.getDataColumn;
import static com.example.creativediligence.champsapp.SettingsActivity.isDownloadsDocument;
import static com.example.creativediligence.champsapp.SettingsActivity.isExternalStorageDocument;
import static com.example.creativediligence.champsapp.SettingsActivity.isGooglePhotosUri;
import static com.example.creativediligence.champsapp.SettingsActivity.isMediaDocument;

public class ProfileFragmentInstitution extends Fragment {

    private static final int PICK_PHOTO_FOR_AVATAR = 1203;
    String currentFragment;

    boolean nameEditPressed;
    EditText nameText;
    TextView title1;
    TextView title2;
    TextView title3;
    TextView title4;
    TextView title5;
    TextView title6;
    TextView totalGames;
    TextView bestTeam;
    TextView totalTeams;
    TextView upcomingEvents;
    TextView entry5;
    TextView entry6;
    FloatingActionButton nameEditButton;
    ImageView profileImage;
    ViewPager viewPager;
    Uri imageUri;
    String userRole;
    ParseObject coachDetails;
    private Button uploadVidButton;
    private RecyclerView vidsRv;
    ArrayList<String> vidFiles;
    ArrayList<String> vidURLs;


    public ProfileFragmentInstitution() {
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

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        //imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nameEditPressed = false;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentFragment = getArguments().getString("fragmentName");
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.personal_profile_fragment_layout_institution, container, false);
        if (getArguments().getString("userRole") != null) {
            userRole = getArguments().getString("userRole");
        }
        vidFiles=new ArrayList<>();
        vidURLs=new ArrayList<>();
        vidsRv=rootView.findViewById(R.id.videos_rv);
        uploadVidButton=rootView.findViewById(R.id.uploadVidButton);
        uploadVidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileExplorerUtilities fe=new FileExplorerUtilities();
                fe.UplodFile(getContext(),"Video",vidsRv,currentFragment);
            }
        });
        GetVideos();
        profileImage = rootView.findViewById(R.id.profile_image_institution);
        nameText = rootView.findViewById(R.id.profile_name_institution_textview);
        totalGames = rootView.findViewById(R.id.total_games_played_tv);
        bestTeam = rootView.findViewById(R.id.best_teams_tv);
        totalTeams = rootView.findViewById(R.id.total_teams_tv);
        upcomingEvents = rootView.findViewById(R.id.upcoming_events_tv);
        entry5 = rootView.findViewById(R.id.entry_5);
        entry6 = rootView.findViewById(R.id.entry_6);

        title1 = rootView.findViewById(R.id.title_1);
        title2 = rootView.findViewById(R.id.title_2);
        title3 = rootView.findViewById(R.id.title_3);
        title4 = rootView.findViewById(R.id.title_4);
        title5 = rootView.findViewById(R.id.title_5);
        title6 = rootView.findViewById(R.id.title_6);


        GetUserProfileData();
        nameEditButton = rootView.findViewById(R.id.profile_edit_button);

        nameEditButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Can't currently edit yet", Toast.LENGTH_SHORT).show();//todo:Make editing possible
                // NameEditTextEdit();
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
                profileImage.setAdjustViewBounds(true);
                profileImage.setMaxHeight(200);
                profileImage.setMaxWidth(200);


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
            nameEditPressed = true;
            showKeyboard(getActivity());

        } else {
            nameText.setClickable(false);
            nameText.setCursorVisible(false);
            nameText.setFocusable(false);
            nameText.setFocusableInTouchMode(false);
            nameEditPressed = false;

            ArrayList<String> prefNames = new ArrayList<>();
            ArrayList<String> prefValues = new ArrayList<>();


            prefNames.add("userName");
            prefValues.add(nameText.getText().toString().trim());

            prefNames.add("currentUserName");
            prefValues.add(ParseUser.getCurrentUser().getUsername());

            prefNames.add(getResources().getString(R.string.memberProfileImage));

            //prefValues.add("");
            if (imageUri != null) {

                prefValues.add(getPathFromUri(getContext(), imageUri));
            } else {
                prefValues.add("");
            }

            new ParseQueries().SetUserProfileData(getContext(), prefNames, prefValues, this.getResources().getString(R.string.UserProfilesInstitutionsClassName));


            hideKeyboard(getActivity());
        }
    }


    public void GetUserProfileData() {
        String className = "";
        String searcKey = "";
        if (userRole.equals("Coach")) {
            className = "InstitutionCoach";
            searcKey = "Name";
        }
        if (!className.equals("")) {
            ParseQuery<ParseObject> profileData = new ParseQuery<ParseObject>(className);
            profileData.whereEqualTo(searcKey, ParseUser.getCurrentUser().getUsername());//todo:get name of current user from Members area users
            profileData.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null && objects.size() > 0) {

                        for (ParseObject ob : objects) {
                            SetPreferences(ob, userRole);
                            //coachDetails=ob;
                        }

                    } else {
                        if (e != null) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "No Data Exists", Toast.LENGTH_SHORT).show();
                            //noDataTv.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }

    }

    public void SetPreferences(ParseObject ob, String userRole) {
        nameText.setText(ob.getString("Name"));
        if (userRole.equals("Coach")) {
            title1.setText("Institution");
            title2.setText("DOB");
            title3.setText("Biography");
            title4.setText("Team");
            title5.setText("Age");
            title6.setText("Trophies");

            totalGames.setText(ob.getString("Institution"));
            bestTeam.setText(ob.getString("DOB"));
            totalTeams.setText(ob.getString("Biography"));
            upcomingEvents.setText(ob.getString("Team"));
            entry5.setText(ob.getString("Age"));
            entry6.setText(ob.getString("Trophies"));

        } else {


            String imageuri = ob.getString(getResources().getString(R.string.memberProfileImage));
            if (!imageuri.isEmpty()) {

                Uri imageURI = Uri.parse(imageuri);

                try {
                    imageUri = imageURI;
                    profileImage.setImageURI(imageURI);
                    profileImage.setAdjustViewBounds(true);
                    profileImage.setMaxHeight(200);
                    profileImage.setMaxWidth(200);
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }


        }
    }


    public void GetVideos(){
        new ParseServerMethods(getContext())
                .DownloadVideo("Videos","uploadedBy",ParseUser.getCurrentUser().getUsername(),vidsRv,currentFragment);

    }
}
