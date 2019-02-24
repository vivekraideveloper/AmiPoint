package com.amityprojectvivekrai.amityproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import hotchemi.android.rate.AppRate;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    public  ActionBarDrawerToggle toggle;
    private BottomNavigationView navigationView;
    private HomeFragment homeFragment;
    private NewsFragment newsFragment;
    private CourseFragment courseFragment;
    private FrameLayout frameLayout;
    private int id;
    private String[] to = {"vivekraideveloper@gmail.com"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onBoardCall();

        AppRate.with(this).setInstallDays(1).setLaunchTimes(2).setRemindInterval(2).monitor();
        AppRate.showRateDialogIfMeetsConditions(this);

        frameLayout = findViewById(R.id.frame_layout);
        navigationView = findViewById(R.id.bottom_nav);
        homeFragment = new HomeFragment();
        courseFragment = new CourseFragment();
        newsFragment = new NewsFragment();
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AmiPoint");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, new HomeFragment());
        ft.addToBackStack("");
        ft.commit();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                id = item.getItemId();


                switch (id) {

                    case R.id.navigation_posts:
                        setFragment(homeFragment);
                        item.setEnabled(true);
                        item.setChecked(true);
                        break;
                    case R.id.navigation_videos:
                        setFragment(newsFragment);
                        item.setEnabled(true);
                        item.setChecked(true);
                        break;
                    case R.id.navigation_stores:
                        setFragment(courseFragment);
                        item.setEnabled(true);
                        item.setChecked(true);
                        break;


                }
                return false;
            }
        });

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
    }

    private void onBoardCall(){
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity

            startActivity(new Intent(MainActivity.this, OnBoardActivity.class));
            Toast.makeText(MainActivity.this, "First Run", Toast.LENGTH_LONG)
                    .show();
        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:
                drawerLayout.closeDrawers();
                break;

            case R.id.aboutUs:
                Intent aboutIntent = new Intent(MainActivity.this, AboutUs.class);
                startActivity(aboutIntent);
                break;
            case R.id.help:
                final AlertDialog.Builder helpAlert = new AlertDialog.Builder(MainActivity.this);
                helpAlert.setIcon(R.drawable.andi);
                helpAlert.setTitle("Help");
                helpAlert.setMessage("Android Masterclass is your go to app for learning Android Development. Navigate to the Course section for an action packed journey. ");
                helpAlert.setPositiveButton("Go Ahead", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        drawerLayout.closeDrawers();
                    }
                });
                helpAlert.setCancelable(false);
                helpAlert.show();
                break;
            case R.id.moreApps:
                Intent morIntent = new Intent(Intent.ACTION_VIEW);
                morIntent.setData(Uri.parse("https://play.google.com/store/apps/dev?id=8604498569793359084"));
                if (morIntent != null) {
                    startActivity(morIntent);
                }
                break;

            case R.id.contact:
                Intent conatctIntent = new Intent(Intent.ACTION_SEND);
                conatctIntent.setData(Uri.parse("mailto:"));
                conatctIntent.putExtra(Intent.EXTRA_EMAIL, to);
                conatctIntent.putExtra(Intent.EXTRA_SUBJECT, "Android Masterclass feedback");
                conatctIntent.putExtra(Intent.EXTRA_TEXT, "");
                conatctIntent.setType("message/UTF-8");
                startActivity(Intent.createChooser(conatctIntent, "Please Choose your Email"));
                break;
            case R.id.terms:
                Intent termsIntent = new Intent(MainActivity.this, Terms.class);
                startActivity(termsIntent);
                break;
        }
        return true;
    }



    private void setFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.addToBackStack("");
        ft.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Android Masterclass is an app that is meant for learning Complete Android Development! Download the app now and become an Android Developer\n" + "https://play.google.com/store/apps/details?id=com.vijayjaidewan01vivekrai.androidmasterclass");
            startActivity(Intent.createChooser(shareIntent, "Share Using"));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to Exit!");
        builder.setTitle("AmiPoint");
        builder.setIcon(R.drawable.andi);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.super.onBackPressed();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

//        moveTaskToBack(true);

    }

}
