package com.reviling.filamentandroid

import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var surfaceView: SurfaceView? = null
    var customViewer: CustomViewer = CustomViewer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        surfaceView = findViewById<View>(R.id.surface_view) as SurfaceView
        customViewer.run {
            loadEntity()
            setSurfaceView(requireNotNull(surfaceView))

            //directory and model each as param
            loadGlb(this@MainActivity, "grogu", "grogu")
            //loadGltf(this, "warcraft", "scene");

            //directory and model as one
            //loadGlb(this, "grogu/grogu");

            //Enviroments and Lightning (OPTIONAL)
            customViewer.loadIndirectLight(this@MainActivity, "venetian_crossroads_2k")
            //loadEnviroment(this, "venetian_crossroads_2k");
        }
    }

    override fun onResume() {
        super.onResume()
        customViewer.onResume()
    }

    override fun onPause() {
        super.onPause()
        customViewer.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        customViewer.onDestroy()
    }
}