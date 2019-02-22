package com.amityprojectvivekrai.amityproject;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private SliderLayout sliderLayout;
    private ProgressBar progressBar;


//    First Section
    private RecyclerView courseRecyclerView;
    private CourseHome courseHome;
    private List<Upload> courseUploads;
    private DatabaseReference courseDatabaseReference;
    private ValueEventListener courseValueEventListener;

//    Second Section
    private RecyclerView booksRecyclerView;
    private BooksHome booksHome;
    private List<Upload> booksUploads;
    private DatabaseReference booksDatabaseReference;
    private ValueEventListener booksValueEventListener;

//        Third Section
    private RecyclerView questionsRecyclerView;
    private QuestionsHome questionsHome;
    private List<Upload> questionsUploads;
    private DatabaseReference questionsDatabaseReference;
    private ValueEventListener questionsValueEventListener;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sliderLayout = view.findViewById(R.id.imageSlider);
        progressBar = view.findViewById(R.id.progressBar);

        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds :
        setSliderViews();
        courseRecyclerView = view.findViewById(R.id.recycler_view_course);
        courseSection();
        booksRecyclerView = view.findViewById(R.id.recycler_view_books);
        booksSection();
        questionsRecyclerView = view.findViewById(R.id.recycler_view_questions);
        questionsSection();
        return view;
    }

    private void courseSection(){
        //        First Section


        courseRecyclerView.setHasFixedSize(true);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        courseUploads = new ArrayList<>();
        FirebaseApp.initializeApp(getContext());
        courseDatabaseReference = FirebaseDatabase.getInstance().getReference("course");
        courseDatabaseReference.keepSynced(true);

        courseHome = new CourseHome(getContext(), courseUploads);
        courseRecyclerView.setAdapter(courseHome);
        courseHome.setOnItemClickListener(new CourseHome.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), "Hola", Toast.LENGTH_SHORT).show();
                Intent courseDetails = new Intent(getContext(), CourseContentActivity.class);
                courseDetails.putExtra("name",courseUploads.get(position).getName());
                courseDetails.putExtra("content",courseUploads.get(position).getContent());
                startActivity(courseDetails);
            }

            @Override
            public void onWhatEverClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {

            }
        });

        courseValueEventListener = courseDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                courseUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setMkey(postSnapshot.getKey());
                    courseUploads.add(upload);

                }
                courseHome.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Unable to Retrieve , some error occured!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void booksSection(){
        //        Second Section


        booksRecyclerView.setHasFixedSize(true);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        booksUploads = new ArrayList<>();
        FirebaseApp.initializeApp(getContext());
        booksDatabaseReference = FirebaseDatabase.getInstance().getReference("books");
        booksDatabaseReference.keepSynced(true);

        booksHome = new BooksHome(getContext(), booksUploads);
        booksRecyclerView.setAdapter(booksHome);
        booksHome.setOnItemClickListener(new BooksHome.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), "Gee thats Wonderful", Toast.LENGTH_SHORT).show();

                Intent bookDetails = new Intent(getContext(), BooksDetailActivity.class);
                bookDetails.putExtra("name",booksUploads.get(position).getName());
                bookDetails.putExtra("authorName",booksUploads.get(position).getAuthorName());
                bookDetails.putExtra("description",booksUploads.get(position).getDescription());
                bookDetails.putExtra("imageUrl",booksUploads.get(position).getImageUrl());
                bookDetails.putExtra("amazonLink", booksUploads.get(position).getAmazonLink());
                bookDetails.putExtra("contactNumber", booksUploads.get(position).getContactNumber());
                startActivity(bookDetails);
            }

            @Override
            public void onWhatEverClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {

            }
        });

        booksValueEventListener = booksDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                booksUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setMkey(postSnapshot.getKey());
                    booksUploads.add(upload);

                }
                booksHome.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Unable to Retrieve , some error occured!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void questionsSection(){
        //        Third Section


        questionsRecyclerView.setHasFixedSize(true);
        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        questionsUploads = new ArrayList<>();
        FirebaseApp.initializeApp(getContext());
        questionsDatabaseReference = FirebaseDatabase.getInstance().getReference("questions");
        questionsDatabaseReference.keepSynced(true);

        questionsHome = new QuestionsHome(getContext(), questionsUploads);
        questionsRecyclerView.setAdapter(questionsHome);
        questionsHome.setOnItemClickListener(new QuestionsHome.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), "You Betcha", Toast.LENGTH_SHORT).show();
                Intent questionsDetails = new Intent(getContext(), QuestionsDetailsActivity.class);
                questionsDetails.putExtra("name", questionsUploads.get(position).getName());
                questionsDetails.putExtra("content", questionsUploads.get(position).getContent());
                startActivity(questionsDetails);
            }

            @Override
            public void onWhatEverClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {

            }
        });

        questionsValueEventListener = questionsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                questionsUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setMkey(postSnapshot.getKey());
                    questionsUploads.add(upload);

                }
                questionsHome.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Unable to Retrieve , some error occured!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(getContext());

            switch (i) {
                case 0:
                    sliderView.setImageUrl("https://images.pexels.com/photos/547114/pexels-photo-547114.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 1:
                    sliderView.setImageUrl("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 2:
                    sliderView.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
                    break;
                case 3:
                    sliderView.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription("setDescription " + (i + 1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(getContext(), "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }
}
