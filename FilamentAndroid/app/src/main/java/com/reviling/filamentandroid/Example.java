package com.reviling.filamentandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;

public class Example extends AppCompatActivity {

    SurfaceView surfaceView;
    CustomViewer  customViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = (SurfaceView) findViewById(R.id.surface);
        customViewer = new CustomViewer();
        customViewer.loadEntity();
        customViewer.setSurfaceView(surfaceView);

        //directory and model each as param
        customViewer.loadGlb(this, "grogu", "grogu");
//        customViewer.loadGltf(this, "warcraft", "scene");

        //directory and model as one
//        customViewer.loadGlb(this, "grogu/grogu");

        //Enviroments and Lightning (OPTIONAL)
        customViewer.loadIndirectLight(this,"venetian_crossroads_2k");
//        customViewer.loadEnviroment(this, "venetian_crossroads_2k");
    }

    @Override
    protected void onResume() {
        super.onResume();
        customViewer.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        customViewer.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        customViewer.onDestroy();
    }
}