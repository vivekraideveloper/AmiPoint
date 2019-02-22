package com.amityprojectvivekrai.amityproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContentActivity extends AppCompatActivity implements ContentAdapter.OnItemClickListener {

    private Toolbar toolbar;
    ContentAdapter adapter;
    private RecyclerView recyclerView;
    private ContentAdapter imageAdapter;
    private DatabaseReference databaseReference;
    private List<Upload> uploads;
    private ValueEventListener valueEventListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    private InterstitialAd interstitialAd;
    private InterstitialAd interstitialAd2;
    private InterstitialAd interstitialAd3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        progressBar = findViewById(R.id.progressBar);

        Bundle bundle = getIntent().getExtras();

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(bundle.getString("name"));


        interstitialAd = new InterstitialAd(ContentActivity.this);
        interstitialAd2 = new InterstitialAd(ContentActivity.this);
        interstitialAd3 = new InterstitialAd(ContentActivity.this);

        interstitialAd.setAdUnitId("ca-app-pub-7875006650320994/1161367888");
        interstitialAd2.setAdUnitId("ca-app-pub-7875006650320994/3270733892");
        interstitialAd3.setAdUnitId("ca-app-pub-7875006650320994/2401054099");

        AdRequest request = new AdRequest.Builder().build();
        interstitialAd.loadAd(request);
        interstitialAd2.loadAd(request);
        interstitialAd3.loadAd(request);


        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        swipeRefreshLayout.setColorSchemeColors(Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });

        recyclerView = findViewById(R.id.rvNumbers);
        recyclerView.setHasFixedSize(true);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL));

        uploads = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("video").child(bundle.getString("content"));
        databaseReference.keepSynced(true);

        imageAdapter = new ContentAdapter(ContentActivity.this, uploads);
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(this);


        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                uploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setMkey(postSnapshot.getKey());
                    uploads.add(upload);

                }
                imageAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ContentActivity.this, "Unable to Retrieve , some error occured!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {

        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }

        Upload selectedItem = uploads.get(position);
        String videoId = selectedItem.getVideoId();
        String name = selectedItem.getName();
        Intent intent = new Intent(ContentActivity.this, VideoActivity.class);
        intent.putExtra("videoId", videoId);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    @Override
    public void onWhatEverClick(int position) {
        if (interstitialAd2.isLoaded()) {
            interstitialAd2.show();
        }

    }

    @Override
    public void onDeleteClick(int position) {
        if (interstitialAd3.isLoaded()) {
            interstitialAd3.show();
        }

    }
}