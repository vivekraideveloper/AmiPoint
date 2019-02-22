package com.amityprojectvivekrai.amityproject;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MajorQuestions extends Fragment implements MajorQuestionsAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private MajorQuestionsAdapter imageAdapter;
    private DatabaseReference databaseReference;
    private List<Upload> uploads;
    private ValueEventListener valueEventListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    public MajorQuestions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_major_questions, container, false);

        QuestionsDetailsActivity activity = (QuestionsDetailsActivity) getActivity();
        String content = activity.getContent();

        progressBar = view.findViewById(R.id.progressBar);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
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
                },5000);
            }
        });

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(numberOfColumns, LinearLayoutManager.VERTICAL));

        uploads = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("questionPapers").child(content).child("major");
        databaseReference.keepSynced(true);

        imageAdapter = new MajorQuestionsAdapter(getActivity(), uploads);
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
                Toast.makeText(getContext(), "Unable to Retrieve , some error occured!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onItemClick(int position) {
//        if (interstitialAd.isLoaded()) {
//            interstitialAd.show();
//        }


//        Upload selectedItem = uploads.get(position);
//        String name = selectedItem.getName();
//        String date = selectedItem.getDate();
//        String time = selectedItem.getTime();
//        String venue = selectedItem.getVenue();
//        String description = selectedItem.getDescription();
//        String imageUrl = selectedItem.getImageUrl();
//        Intent intent = new Intent(getContext(), NewsActivity.class);
//        intent.putExtra("name", name);
//        intent.putExtra("date", date);
//        intent.putExtra("time", time);
//        intent.putExtra("venue", venue);
//        intent.putExtra("description", description);
//        intent.putExtra("imageUrl", imageUrl);
//        startActivity(intent);

    }

    @Override
    public void onWhatEverClick(int position) {
//        if (interstitialAd2.isLoaded()) {
//            interstitialAd2.show();
//        }



    }

    @Override
    public void onDeleteClick(int position) {

//        if (interstitialAd3.isLoaded()) {
//            interstitialAd3.show();
//        }



    }

}