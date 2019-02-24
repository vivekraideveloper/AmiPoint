package com.amityprojectvivekrai.amityproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.widget.ImageView;

import tyrantgit.explosionfield.ExplosionField;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageView;
    private  ExplosionField explosionField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.image_view);
        explosionField = ExplosionField.attach2Window(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               explosionField.explode(imageView);
            }
        }, 1500);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2500);
    }
}
