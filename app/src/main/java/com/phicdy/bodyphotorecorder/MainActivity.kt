package com.phicdy.bodyphotorecorder

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.phicdy.bodyphotorecorder.camera.CameraActivity
import com.phicdy.bodyphotorecorder.ui.theme.BodyPhotoRecorderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BodyPhotoRecorderTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(
                        "Android",
                        onButtonClicked = {
                            startActivity(Intent(this, CameraActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, onButtonClicked: () -> Unit) {
    Text(text = "Hello $name!")
    Button(onClick = onButtonClicked) {
        Text(text = "Camera")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BodyPhotoRecorderTheme {
        Greeting("Android", {})
    }
}