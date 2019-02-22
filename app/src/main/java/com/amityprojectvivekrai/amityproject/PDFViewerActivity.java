package com.amityprojectvivekrai.amityproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class PDFViewerActivity extends AppCompatActivity {
    private PDFView pdfView;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private boolean pdfViewType = true;
    InputStream inputStream = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        Bundle bundle = getIntent().getExtras();
        String notesLink = bundle.getString("notesLink");
        String name = bundle.getString("name");
        pdfView = (PDFView)findViewById(R.id.pdfView);
        toolbar = findViewById(R.id.tool_bar);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);


        new RetrivePdfStream().execute(notesLink);

    }

    class RetrivePdfStream extends AsyncTask<String, Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                if (((HttpURLConnection) url.openConnection()).getResponseCode() == 200){
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                }
            }catch (IOException e){

                Toast.makeText(PDFViewerActivity.this, "Error occured while loading", Toast.LENGTH_SHORT).show();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).swipeHorizontal(pdfViewType).defaultPage(4).enableAnnotationRendering(true).scrollHandle(new DefaultScrollHandle(PDFViewerActivity.this)).enableAntialiasing(true).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }).load();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pdf_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.screenshot) {
            Bitmap bitmap = takeScreenshot();
            saveBitmap(bitmap);



            View view = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(view, "Screenshot Saved to Amity Project Folder!",5000).setAction("Action", null);
            snackbar.show();

        }

        return super.onOptionsItemSelected(item);
    }

    public Bitmap takeScreenshot() {
        View rootView = getWindow().peekDecorView();
//        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        Random random = new Random();
        int n = random.nextInt(10000);
        File wallpaperDirectory = new File("/sdcard/Amity Project/");
// have the object build the directory structure, if needed.
        wallpaperDirectory.mkdirs();
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/Amity Project/screenshot"+n+".png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
}
