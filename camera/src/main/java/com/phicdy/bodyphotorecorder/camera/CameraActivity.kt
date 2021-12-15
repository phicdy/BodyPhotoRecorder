package com.phicdy.bodyphotorecorder.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import java.io.File

class CameraActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraScreen()
        }
    }


    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

}

enum class PermissionState {
    Checking,
    Granted,
    Denied,
}

@Composable
fun CameraScreen() {
    var permissionState by remember { mutableStateOf(PermissionState.Checking) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        permissionState = if (it) PermissionState.Granted else PermissionState.Denied
    }
    val permission = Manifest.permission.CAMERA
    val context = LocalContext.current
    val lifecycleObserver = remember {
        LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                val result = context.checkSelfPermission(permission)
                if (result != PackageManager.PERMISSION_GRANTED) {
                    permissionState = PermissionState.Checking
                    launcher.launch(permission)
                } else {
                    permissionState = PermissionState.Granted
                }
            }
        }
    }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle, lifecycleObserver) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
    when (permissionState) {
        PermissionState.Checking -> {}
        PermissionState.Granted -> {
            CameraPreview()
        }
        PermissionState.Denied -> {}
    }
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
    CameraScreen()
}