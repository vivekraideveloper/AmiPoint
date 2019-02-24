package com.amityprojectvivekrai.amityproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.r0adkll.slidr.Slidr;

public class BooksDetailActivity extends AppCompatActivity {
    private static final int MAX_LINES =1;
    private Toolbar toolbar;
    private ImageView imageView;
    private TextView name, authorName, description;
    private Button button;
    private Button button2;
    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_detail);
        Slidr.attach(this);
        telephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        Bundle bundle = getIntent().getExtras();
        String bookName = bundle.getString("name");
        String bookAuthorName = bundle.getString("authorName");
        String bookDescription = bundle.getString("description");
        String bookImageUrl = bundle.getString("imageUrl");
        final String bookAmazonLink = bundle.getString("amazonLink");
        final String bookContactNumber = bundle.getString("contactNumber");


        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.name);
        authorName = findViewById(R.id.authorName);
        description = findViewById(R.id.description);
        toolbar = findViewById(R.id.aboutUsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(bookName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Glide.with(BooksDetailActivity.this).load(bookImageUrl).into(imageView);
        name.setText(bookName);
        authorName.setText(bookAuthorName);
        description.setText(bookDescription);
        ResizableCustomView.doResizeTextView(description, MAX_LINES, "Read More", true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BooksDetailActivity.this, WebActivity.class);
                intent.putExtra("url", bookAmazonLink);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+bookContactNumber));
                startActivity(intent);
            }
        });
    }
}
