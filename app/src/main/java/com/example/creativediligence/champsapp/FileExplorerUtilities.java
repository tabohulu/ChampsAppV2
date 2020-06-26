package com.example.creativediligence.champsapp.Common;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
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

import com.example.creativediligence.champsapp.R;
import com.parse.ParseUser;

public class FileExplorerUtilities {
    Context mContext;
    TextView currentDirtv;
    ListView dirContentListview;
    String TAG = FileExplorerUtilities.class.getSimpleName();
    private File path;
    private Boolean firstLvl = true;
    private Item[] fileList;
    //private String chosenFile;
    ArrayList<String> str = new ArrayList<String>();
    private View prevView = null;
    private File fileObject;
    String mFileType;
    ParseServerMethods serverMethods;
    RecyclerView mRV;
    String mCurrentFragment;



    public void UplodFile(Context context, String filetype, RecyclerView rv,String currentFragment){
        mContext = context;
        mFileType=filetype;
        mRV=rv;
        mCurrentFragment=currentFragment;
        Initialization();
    }
    private void Initialization() {
        LayoutInflater myLayout = LayoutInflater.from(mContext);
        final View dialogView = myLayout.inflate(R.layout.file_explorer_layout, null);
        currentDirtv = dialogView.findViewById(R.id.current_dir_tv);
        dirContentListview = dialogView.findViewById(R.id.current_dir_contents);
        path = new File(Environment.getExternalStorageDirectory() + "/NicheSports");
        serverMethods=new ParseServerMethods(mContext);
        LoadFileList();
        AdapterActions();
        OpenDialog(dialogView);
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

    private void AdapterActions(){
        ListAdapter adapter = new ArrayAdapter<Item>(mContext, android.R.layout.simple_list_item_1, fileList);
        dirContentListview.setAdapter(adapter);
        dirContentListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int which, long l) {
                String chosenFile = fileList[which].file;
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
                    fileObject = sel;
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
    }

    private void OpenDialog(View dialogView){

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
                if ((!fileObject.getName().contains(".xls") || !fileObject.getName().contains(".xls"))  && mFileType.equals("Excel")){
                    Toast.makeText(mContext, "Choose a file with a .xls extension", Toast.LENGTH_SHORT).show();
                    return;

                }else if ((!fileObject.getName().contains(".mp4") )  && mFileType.equals("Video")){
                    Toast.makeText(mContext, "Choose a file with a .xls extension", Toast.LENGTH_SHORT).show();
                    return;

                }

                if( mFileType.equals("Excel")) {
               /* HashMap excelData = ProcessExcelFile(excelFile);
                UploadDataToServer( excelData);*/
                }else if( mFileType.equals("Video")) {

               serverMethods.UploadVideo("Videos",fileObject, ParseUser.getCurrentUser().getUsername(),mRV,mCurrentFragment);
                }
                alertDialog.dismiss();

            }
        });
    }
/*************************************************************************************/
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