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
        customViewer.setSurfaceView(this, surfaceView);

        customViewer.loadGlb(this, "grogu/grogu");
//        customViewer.loadGltf(this, "peresozo_sale");
//        customViewer.loadEnviroment(this,"venetian_crossroads_2k");
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