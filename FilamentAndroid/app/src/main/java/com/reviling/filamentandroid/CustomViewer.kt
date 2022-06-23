package com.reviling.filamentandroid

import android.content.Context
import android.view.Choreographer
import android.view.SurfaceView
import com.google.android.filament.Skybox
import com.google.android.filament.utils.KTXLoader
import com.google.android.filament.utils.ModelViewer
import com.google.android.filament.utils.Utils
import java.nio.ByteBuffer

class CustomViewer {
    companion object {
        init {
            Utils.init();
        }
    }

    private lateinit var choreographer: Choreographer
    private lateinit var modelViewer: ModelViewer

    fun loadEntity() {
        choreographer = Choreographer.getInstance()
    }

    fun setSurfaceView(mSurfaceView: SurfaceView) {
        modelViewer = ModelViewer(mSurfaceView)
        mSurfaceView.setOnTouchListener(modelViewer)

        //Skybox and background color
        //without this part the scene'll appear broken
        modelViewer.scene.skybox = Skybox.Builder().build(modelViewer.engine)
        modelViewer.scene.skybox!!.setColor(1.0f, 1.0f, 1.0f, 1.0f)//White color
    }

    fun loadGlb(context: Context, name: String) {
        val buffer = readAsset(context, "model/${name}.glb")
        modelViewer.loadModelGlb(buffer)
        modelViewer.transformToUnitCube()
    }

    fun loadGlb(context: Context, dirName: String, name: String) {
        val buffer = readAsset(context, "model/${dirName}/${name}.glb")
        modelViewer.loadModelGlb(buffer)
        modelViewer.transformToUnitCube()
    }

//    fun loadGltf(context: Context, name: String)
//    {
//        val buffer = context.assets.open("model/${name}.gltf").use { input ->
//            val bytes = ByteArray(input.available())
//            input.read(bytes)
//            ByteBuffer.wrap(bytes)
//        }
//        modelViewer.loadModelGltf(buffer){ uri -> readAsset(context, "model/$uri") }
//        modelViewer.transformToUnitCube()
//    }

    fun loadGltf(context: Context, dirName: String, name: String) {
        val buffer = context.assets.open("model/${dirName}/${name}.gltf").use { input ->
            val bytes = ByteArray(input.available())
            input.read(bytes)
            ByteBuffer.wrap(bytes)
        }
        modelViewer.loadModelGltf(buffer) { uri -> readAsset(context, "model/${dirName}/$uri") }
        modelViewer.transformToUnitCube()
    }

    fun loadIndirectLight(context: Context, ibl: String) {
        // Create the indirect light source and add it to the scene.
        var buffer = readAsset(context, "enviroments/venetian_crossroads_2k/${ibl}_ibl.ktx")
        KTXLoader.createIndirectLight(modelViewer.engine, buffer).apply {
            intensity = 50_000f
            modelViewer.scene.indirectLight = this
        }
    }

    fun loadEnviroment(context: Context, ibl: String) {
        // Create the sky box and add it to the scene.
        var buffer = readAsset(context, "enviroments/venetian_crossroads_2k/${ibl}_skybox.ktx")
        KTXLoader.createSkybox(modelViewer.engine, buffer).apply {
            modelViewer.scene.skybox = this
        }
    }

    private fun readAsset(context: Context, assetName: String): ByteBuffer {
        val input = context.assets.open(assetName)
        val bytes = ByteArray(input.available())
        input.read(bytes)
        return ByteBuffer.wrap(bytes)
    }

    private val frameCallback = object : Choreographer.FrameCallback {
        //        override fun doFrame(currentTime: Long) {
//            choreographer.postFrameCallback(this)
//            modelViewer.render(currentTime)
//        }
        private val startTime = System.nanoTime()
        override fun doFrame(currentTime: Long) {
            val seconds = (currentTime - startTime).toDouble() / 1_000_000_000
            choreographer.postFrameCallback(this)
            modelViewer.animator?.apply {
                if (animationCount > 0) {
                    applyAnimation(0, seconds.toFloat())
                }
                updateBoneMatrices()
            }
            modelViewer.render(currentTime)
        }
    }

    fun onResume() {
        choreographer.postFrameCallback(frameCallback)
    }

    fun onPause() {
        choreographer.removeFrameCallback(frameCallback)
    }

    fun onDestroy() {
        choreographer.removeFrameCallback(frameCallback)
    }
}