package com.amityprojectvivekrai.amityproject;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.r0adkll.slidr.Slidr;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {
    Toolbar toolbar;
    private ImageView imageView;
    private TextView date;
    private TextView time;
    private TextView venue;
    private TextView description;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Slidr.attach(this);
        toolbar = findViewById(R.id.tool_bar);
        imageView = findViewById(R.id.newsImage);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        venue = findViewById(R.id.venue);
        description = findViewById(R.id.description);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(bundle.getString("name"));

        date.setText(bundle.getString("date"));
        time.setText(bundle.getString("time"));
        venue.setText(bundle.getString("venue"));
        description.setText(bundle.getString("description"));
        Picasso.with(NewsActivity.this).load(Uri.parse(bundle.getString("imageUrl"))).fit().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.INVISIBLE);

            }
        });


    }
}
