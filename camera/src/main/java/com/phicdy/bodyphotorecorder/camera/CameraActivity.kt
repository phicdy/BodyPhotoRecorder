package com.phicdy.bodyphotorecorder.camera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import java.io.File

class CameraActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraScrren()
        }
    }


    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

}

@Composable
fun CameraScrren() {
    CameraPreview()
}

@Composable
fun CameraPreview() {
    AndroidView(
        factory = { context ->
            val previewView = PreviewView(context)
            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ShutterButton() {
    Button(onClick = { /*TODO*/ }) {

    }
}

@Preview
@Composable
fun CamereScreenPreivew() {
    CameraScrren()
}