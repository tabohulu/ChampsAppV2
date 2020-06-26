package com.example.creativediligence.champsapp.Common;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creativediligence.champsapp.GroupFragmentAdapterMain;
import com.example.creativediligence.champsapp.InstitutionsFragmentAdminAdapterMain;
import com.example.creativediligence.champsapp.MembersArea.Coaches.CoachesFragmentAdapterMain;
import com.example.creativediligence.champsapp.MembersArea.Teams.TeamsFragmentAdapterMain;
import com.example.creativediligence.champsapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class FileExploreDialog {

    private static final String TAG = "F_PATH";
    private static final int DIALOG_LOAD_FILE = 1000;
    TextView currentDirtv;
    ListView dirContentListview;
    Workbook workbook;
    // Stores names of traversed directories
    ArrayList<String> str = new ArrayList<String>();
    View prevView = null;
    // Check if the first level of the directory structure is the one showing
    private Boolean firstLvl = true;
    private Item[] fileList;
    private File path = new File(Environment.getExternalStorageDirectory() + "/NicheSports");
    private String chosenFile;
    private File excelFile;
    private String mCurrentFragment;

    private RecyclerView rv;
    private TextView infoTv;
    private Context mContext;

    public void OpenDialog(final Context context,String currentFragment,RecyclerView mRV,TextView mTv) {
        mCurrentFragment=currentFragment;
        mContext=context;
        rv=mRV;
        infoTv=mTv;
        LayoutInflater myLayout = LayoutInflater.from(mContext);
        final View dialogView = myLayout.inflate(R.layout.file_explorer_layout, null);
        currentDirtv = dialogView.findViewById(R.id.current_dir_tv);
        dirContentListview = dialogView.findViewById(R.id.current_dir_contents);
        LoadFileList();
        ListAdapter adapter = new ArrayAdapter<Item>(mContext, android.R.layout.simple_list_item_1, fileList);
        dirContentListview.setAdapter(adapter);
        dirContentListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int which, long l) {
                chosenFile = fileList[which].file;
                File sel = new File(path + "/" + chosenFile);
                if (sel.isDirectory()) {
                    firstLvl = false;

                    // Adds chosen directory to list
                    str.add(chosenFile);
                    fileList = null;
                    path = new File(sel + "");

                    LoadFileList();

            /*removeDialog(DIALOG_LOAD_FILE);
            showDialog(DIALOG_LOAD_FILE);*/
                    Log.d(TAG, path.getAbsolutePath());

                } else if (chosenFile.equalsIgnoreCase("up") && !sel.exists()) {

                    // present directory removed from list
                    String s = str.remove(str.size() - 1);

                    // path modified to exclude present directory
                    path = new File(path.toString().substring(0,
                            path.toString().lastIndexOf(s)));
                    fileList = null;

                    // if there are no more directories in the list, then
                    // its the first level
                    if (str.isEmpty()) {
                        firstLvl = true;
                    }
                    LoadFileList();


                    Log.d(TAG, path.getAbsolutePath());

                } else {
                    excelFile = sel;
                    Toast.makeText(mContext, sel.getName() + " chosen", Toast.LENGTH_SHORT).show();
                    if (prevView != null) {
                        prevView.setBackgroundColor(mContext.getResources().getColor(R.color.color_white));
                    }
                    view.setBackgroundColor(mContext.getResources().getColor(R.color.sky_blue));
                    prevView = view;
                    // Perform action with file picked
                }
            }
        });

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext, R.style.MyDialogTheme);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setNegativeButton("Cancel", null)
        ;
        alertDialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("Select Excel File");
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!excelFile.getName().contains(".xls") || !excelFile.getName().contains(".xls")) {
                    Toast.makeText(mContext, "Choose a file with a .xls extension", Toast.LENGTH_SHORT).show();
                    return;

                }
                HashMap excelData = ProcessExcelFile(excelFile);
                UploadDataToServer( excelData);
                alertDialog.dismiss();

            }
        });
    }


    private void LoadFileList() {
        Item temp[] = null;
        currentDirtv.setText(path.getAbsolutePath());
        try {
            path.mkdirs();
        } catch (SecurityException e) {
            Log.e(TAG, "unable to write on the sd card ");
        }

        // Checks whether path exists
        if (path.exists()) {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    // Filters based on whether the file is hidden or not
                    return (sel.isFile() || sel.isDirectory())
                            && !sel.isHidden();

                }
            };

            String[] fList = path.list(filter);
            fileList = new Item[fList.length];
            for (int i = 0; i < fList.length; i++) {
                fileList[i] = new Item(fList[i]);

                // Convert into file path
                File sel = new File(path, fList[i]);

                // Set drawables
                /*if (sel.isDirectory()) {
                    fileList[i].icon = R.drawable.directory_icon;
                    Log.d("DIRECTORY", fileList[i].file);
                } else {
                    Log.d("FILE", fileList[i].file);
                }*/
            }

            if (!firstLvl) {
                temp = new Item[fileList.length + 1];
                for (int i = 0; i < fileList.length; i++) {
                    temp[i + 1] = fileList[i];
                }
                temp[0] = new Item("...Up");
                fileList = temp;
            }
        } else {
            Log.e(TAG, "path does not exist");
        }

    }


    private HashMap ProcessExcelFile(File excelFile) {
        HashMap<String, ArrayList<String>> excelData = new HashMap<>();
        try {

            workbook = Workbook.getWorkbook(excelFile);
            Sheet s = workbook.getSheet(0);
            int row = s.getRows();
            int cols = s.getColumns();
            String key = "";

            for (int c = 0; c < cols; c++) {
                ArrayList<String> contents = new ArrayList<>();
                Cell temp = s.getCell(c, 0);
                key = temp.getContents();
                for (int i = 1; i < row; i++) {

                    Cell z = s.getCell(c, i);
                    //Toast.makeText(mContext, z.getContents(), Toast.LENGTH_SHORT).show();
                    Log.d("Excel Read", z.getContents());
                    String content = z.getContents();
                    if (!content.isEmpty()) {
                        contents.add(content);
                    }

                }
                excelData.put(key, contents);


            }
            Log.d("Excel data", excelData.toString());


        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();

            Log.d("Excel Read Exception", e.getMessage());
        } finally {

            if (workbook != null) {
                workbook.close();
            }

        }
        return excelData;
    }


    private void UploadDataToServer(final HashMap<String, ArrayList<String>> hashMap) {

        try {
            String[] keys=null;
            String serverClass=null;
            if(mCurrentFragment.equals("Athletes")) {
                 keys = mContext.getResources().getStringArray(R.array.institution_athlete_keys);
                 serverClass="InstitutionAthlete";
            }else  if(mCurrentFragment.equals("Coaches")) {
                keys = mContext.getResources().getStringArray(R.array.institution_coach_keys);
                serverClass="InstitutionCoach";
            } else  if(mCurrentFragment.equals("Teams")) {
                keys = mContext.getResources().getStringArray(R.array.institution_team_keys);
                serverClass="InstitutionTeam";
            }else  if(mCurrentFragment.equals("Institutions")) {
                keys = mContext.getResources().getStringArray(R.array.institution_institution_keys);
                serverClass="Institution";
            }
            ArrayList<String> arrayList = hashMap.get(keys[0]);
            for (int i = 0; i < arrayList.size(); i++) {

                final ParseObject coach = new ParseObject(serverClass);
                for (int j = 0; j < keys.length; j++) {

                    coach.put(keys[j], (hashMap.get(keys[j])).get(i));
                    Log.d("UploadDataToServer", "Success! :-> "+(hashMap.get(keys[j])).get(i));
                }

                //Check if data already exists
                ParseQuery<ParseObject> dataCheck = new ParseQuery<ParseObject>(serverClass);
                dataCheck.whereEqualTo(keys[0],(hashMap.get(keys[0])).get(i));
                final String[] finalKeys = keys;
                final int finalI = i;
                dataCheck.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e==null && objects.size()>0){
                            Toast.makeText(mContext, mCurrentFragment+" "+ (hashMap.get(finalKeys[0])).get(finalI)+" already Exists", Toast.LENGTH_SHORT).show();
                        }else{
                            SaveDataInBackground(coach);
                        }
                    }
                });

            }
        } catch (Exception e) {
            Log.d("UploadDataToServer", e.getLocalizedMessage() + " / " + e.getStackTrace()[0].getLineNumber()+"/"+mCurrentFragment);
        }

    }

    public void SaveDataInBackground(ParseObject coach){
        coach.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //Log.d("Here", "sucess");
                    Log.d("Excel Data Success", "success");
                    //Toast.makeText(mContext, " New Athlete " + coachName + " created!", Toast.LENGTH_SHORT).show();
                    PopulateRecyclerView();
                } else {

                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Excel Data Success", e.getMessage());
                }
            }
        });
    }
    public void PopulateRecyclerView() {

        String serverClass=null;
        String searchKey=null;
        if(mCurrentFragment.equals("Athletes")) {
            serverClass="InstitutionAthlete";
            searchKey="Institution";
        }else  if(mCurrentFragment.equals("Coaches")) {
            serverClass="InstitutionCoach";
            searchKey="Institution";
        }
        else  if(mCurrentFragment.equals("Teams")) {
            serverClass="InstitutionTeam";
            searchKey="Institution";
        }
        else  if(mCurrentFragment.equals("Institutions")) {
            serverClass="Institution";
            searchKey="";
        }
        final ArrayList<String> mData=new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(serverClass);
        if(!mCurrentFragment.equals("Institutions")) {
            query.whereEqualTo(searchKey, ParseUser.getCurrentUser().getUsername());
        }

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

                    if(mCurrentFragment.equals("Athletes")) {
                        InstitutionAthleteRecylerviewAdapter adapter = new InstitutionAthleteRecylerviewAdapter(mContext, R.layout.home_fragment_layout_custom, mData,mCurrentFragment);
                        rv.setAdapter(adapter);
                    }else  if(mCurrentFragment.equals("Coaches")) {
                        CoachesFragmentAdapterMain adapter = new CoachesFragmentAdapterMain(mContext, R.layout.home_fragment_layout_custom, mData,mCurrentFragment);
                        rv.setAdapter(adapter);
                    }
                    else  if(mCurrentFragment.equals("Teams")) {
                        TeamsFragmentAdapterMain adapter = new TeamsFragmentAdapterMain(mContext, R.layout.home_fragment_layout_custom, mData);
                        rv.setAdapter(adapter);
                    }
                    else  if(mCurrentFragment.equals("Institutions")) {
                        InstitutionsFragmentAdminAdapterMain adapter = new InstitutionsFragmentAdminAdapterMain(mContext, R.layout.home_fragment_layout_custom, mData,mCurrentFragment);
                        rv.setAdapter(adapter);
                    }


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

    private class Item {
        public String file;


        public Item(String file) {
            this.file = file;

        }

        @Override
        public String toString() {
            return file;
        }
    }
}
