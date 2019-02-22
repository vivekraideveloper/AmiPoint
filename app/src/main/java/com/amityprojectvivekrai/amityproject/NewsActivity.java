package com.amityprojectvivekrai.amityproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

public class NewsActivity extends AppCompatActivity {
    Toolbar toolbar;
    private ImageView imageView;
    private TextView date;
    private TextView time;
    private TextView venue;
    private TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        toolbar = findViewById(R.id.tool_bar);
        imageView = findViewById(R.id.newsImage);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        venue = findViewById(R.id.venue);
        description = findViewById(R.id.description);

        Bundle bundle = getIntent().getExtras();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(bundle.getString("name"));

        date.setText("Date - " + bundle.getString("date"));
        time.setText("Time - " + bundle.getString("time"));
        venue.setText("Venue - " + bundle.getString("venue"));
        description.setText(bundle.getString("description"));
        Glide.with(NewsActivity.this).load(bundle.get("imageUrl")).into(imageView);


    }
}
