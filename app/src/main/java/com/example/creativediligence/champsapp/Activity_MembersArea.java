package com.example.creativediligence.champsapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class Activity_MembersArea extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    ParseUser currentUser;
    int accesLevel=-2;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__members_area);

        currentUser=ParseUser.getCurrentUser();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //new PreferenceMethods().setColorChosen(toolbar, MemebersAreaActivity.this);
        bundle = new Bundle();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Log.d("currentUser",currentUser.getUsername());
       /* if (currentUser.getUsername().equals("blakedevon93@gmail.com") || currentUser.getUsername().equals("testuser")) {
            currentUser.put("userRole", "instManager");
            currentUser.put("memAreaAuth", "1234");
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        //Toast.makeText(getApplicationContext(), currentUser.get("userRole").toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }*/
        /*ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("MembersAreaUsers");
        query.whereEqualTo("name",currentUser.getUsername());
        if(currentUser.get("userRole").toString().equals("user")){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.coaches).setVisible(false);
            nav_Menu.findItem(R.id.events).setVisible(false);
            nav_Menu.findItem(R.id.athletes).setVisible(false);
            accesLevel=-1;
        }else if(currentUser.get("userRole").toString().equals("instManager")){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.messages).setVisible(false);
        }*/
        //MemAreaAuth(getApplicationContext(), navigationView.getMenu());


        navigationView.setNavigationItemSelectedListener(this);

        //set default home fragnent
        Fragment fragment = null;
        Class fragmentClass = HomeFragment.class;
        try {

            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainLinLay, fragment).commit();
        MemAreaAuth(this, navigationView.getMenu());
        //new DialogCreator().MemAreaAuth(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.memebers_area, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;
        Class fragmentClass = null;

        int id = item.getItemId();
        if(accesLevel==0){//acces for Admin
            if (id == R.id.home) {
                // Handle the camera action

                fragmentClass = HomeFragment.class;

            }  else if (id == R.id.institutions) {
                fragmentClass = InstitutionsFragmentAdmin.class;
            } else if (id == R.id.institution_managers) {
                fragmentClass = InstitutionManagersFragmentAdmin.class;
            } else if (id == R.id.coaches) {
                //bundle.putString("fragmentName", item.getTitle().toString());
                fragmentClass = InstitutionsCoachesFragment.class;
            }
        }
        else if(accesLevel==-2){//access for coach, institute manager
            if (id == R.id.home) {
                // Handle the camera action

                fragmentClass = HomeFragment.class;

            } else if (id == R.id.profile) {
                fragmentClass = ProfileFragmentInstitution.class;
            } else if (id == R.id.events) {
                fragmentClass = InstitutionsEventsFragment.class;
            } else if (id == R.id.athletes) {

                //bundle.putString("fragmentName", item.getTitle().toString());
                fragmentClass = InstitutionsAthletesFragment.class;


            } else if (id == R.id.groups) {
                fragmentClass = InstitutionsGroupsFragment.class;
            } else if (id == R.id.teams) {
                fragmentClass = InstitutionsTeamsFragment.class;
            } else if (id == R.id.coaches) {
                //bundle.putString("fragmentName", item.getTitle().toString());
                fragmentClass = InstitutionsCoachesFragment.class;
            }
        }else if(accesLevel==-1){ //access for athletes
            if (id == R.id.home) {
                // Handle the camera action

                fragmentClass = HomeFragment.class;

            } else if (id == R.id.profile) {
                fragmentClass = ProfileFragment.class;
            } else if (id == R.id.messages) {
                fragmentClass = MessageFragment.class;
            } else if (id == R.id.groups) {
                fragmentClass = GroupsFragment.class;
            } else if (id == R.id.teams) {
                fragmentClass = TeamsFragment.class;
            } else if (id == R.id.coaches) {
                Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
            }
        }


        try {
            toolbar.setTitle(item.getTitle());
            bundle.putString("fragmentName", item.getTitle().toString());
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainLinLay, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ToMessages(View view) {

        /*Fragment fragment = null;
        Class fragmentClass = MessageFragment.class;
        try {
            toolbar.setTitle("Messages");
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainLinLay, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
    }

    public void ToGroups(View view) {

        /*Fragment fragment = null;
        Class fragmentClass = GroupsFragment.class;
        try {
            toolbar.setTitle("Groups");
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainLinLay, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainLinLay, fragment).commit();*/

        Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
    }

    public void ToTeams(View view) {

       /* Fragment fragment = null;
        Class fragmentClass = TeamsFragment.class;
        try {
            toolbar.setTitle("Teams");
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainLinLay, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainLinLay, fragment).commit();*/

        Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
    }

    public void ToHome(View view) {
        /*Fragment fragment = null;
        Class fragmentClass = HomeFragment.class;
        try {
            toolbar.setTitle("Home");
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainLinLay, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainLinLay, fragment).commit();*/

        Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Toast.makeText(this, "here", Toast.LENGTH_LONG).show();
    }

    public void MemAreaAuth(final Context mContext, final Menu nav_Menu ){
        LayoutInflater myLayout=LayoutInflater.from(mContext);
        final View dialogView =myLayout.inflate(R.layout.mem_area_auth_layout,null);
        final EditText authCodeInput=dialogView.findViewById(R.id.auth_code_edit);
        final Spinner member_level =dialogView.findViewById(R.id.auth_spinner);

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
                // String auth=ParseUser.getCurrentUser().get("memAreaAuth").toString();
                final String userAuth=authCodeInput.getText().toString();
                final String memberLevel=member_level.getSelectedItem().toString();

                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("MembersAreaUsers");
                query.whereEqualTo("name",currentUser.getUsername());
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e==null && objects.size()>0){
                            String password= "";
                            String userRole="";
                            for (ParseObject ob : objects) {
                                password= ob.getString("password");
                                userRole=ob.getString("userRole");
                                //mData.add(ob.getString("Name"));
                            }
                            if (!password.equals(userAuth) || !userRole.equals(memberLevel)){
                                Toast.makeText(mContext,"empty string or wrong code entered",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(!userRole.isEmpty()|| userRole!=null) {
                                bundle.putString("userRole", userRole);
                            }
                            if(userRole.equals("Athlete")){
                                //nav_Menu = navigationView.getMenu();
                                nav_Menu.findItem(R.id.coaches).setVisible(false);
                                nav_Menu.findItem(R.id.events).setVisible(false);
                                nav_Menu.findItem(R.id.athletes).setVisible(false);
                                nav_Menu.findItem(R.id.institutions).setVisible(false);
                                nav_Menu.findItem(R.id.institution_managers).setVisible(false);
                                accesLevel=-1;
                            }else if(userRole.equals("InstituteManager") ){
                                // Menu nav_Menu = navigationView.getMenu();
                                nav_Menu.findItem(R.id.messages).setVisible(false);
                                nav_Menu.findItem(R.id.institutions).setVisible(false);
                                nav_Menu.findItem(R.id.institution_managers).setVisible(false);
                                accesLevel=-2;
                            }else if( userRole.equals("Admin")){
                                // Menu nav_Menu = navigationView.getMenu();
                                nav_Menu.findItem(R.id.profile).setVisible(false);
                                nav_Menu.findItem(R.id.events).setVisible(false);
                                nav_Menu.findItem(R.id.messages).setVisible(false);
                                nav_Menu.findItem(R.id.groups).setVisible(false);
                                nav_Menu.findItem(R.id.athletes).setVisible(false);
                                nav_Menu.findItem(R.id.teams).setVisible(false);
                                accesLevel=0;
                            }
                            else if(userRole.equals("Coach")){
                                // Menu nav_Menu = navigationView.getMenu();
                                nav_Menu.findItem(R.id.messages).setVisible(false);
                                nav_Menu.findItem(R.id.coaches).setVisible(false);
                                nav_Menu.findItem(R.id.institutions).setVisible(false);
                                nav_Menu.findItem(R.id.institution_managers).setVisible(false);
                                accesLevel=-2;
                            }
                            Toast.makeText(mContext,"Authentication Succesful",Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }else{
                            if(e!=null) {
                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(mContext, "No Data Exists", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }
        });
    }

}
