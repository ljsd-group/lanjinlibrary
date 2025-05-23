package com.lanji.library

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.lanji.library.ui.theme.LanjinlibraryTheme
import com.lanji.mylibrary.dialog.AdvDialog
import com.lanji.mylibrary.loadingdialog.MProgressDialog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LanjinlibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val laco= LocalContext.current
    var showDialog = remember { mutableStateOf(false) }
    if(showDialog.value) {
       val  dialog=AdvDialog(laco)
        dialog.show()
        dialog.setImage("http://192.168.31.100:8080/profile/upload/2025/05/22/弹窗广告1@4x _1__20250430202755A015_20250522153311A001.jpg",
            "https://admob.google.com/home/",0)
    }
    Text(
        text = "Hello $name!",
        modifier = modifier.clickable(){
            showDialog.value=true
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LanjinlibraryTheme {
        Greeting("Android")
    }
}