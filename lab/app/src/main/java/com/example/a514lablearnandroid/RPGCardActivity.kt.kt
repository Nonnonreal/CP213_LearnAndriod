package com.example.a514lablearnandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class RPGCardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Lifecyble", "MainActivity : onCreate")
        setContent {
            RPGCardView()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("Lifecycle", "MainActivity : onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Lifecycle", "MainActivity : onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Lifecycle", "MainActivity : onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Lifecycle", "MainActivity : onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Lifecycle", "MainActivity : onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("Lifecycle", "MainActivity : onRestart")
    }
    @Composable
    fun RPGCardView(){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(32.dp)) {
            //hp
            Box (modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(color = Color.LightGray)
            ){
                Text(
                    text = "HP",
                    modifier = Modifier
                        .align(alignment = Alignment.CenterStart)
                        .fillMaxWidth(fraction = 0.20f)
                        .background(color = Color.Green)
                        .padding(8.dp)
                )

            }

            //image
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(300.dp)
                    .align((Alignment.CenterHorizontally))
                    .padding(top = 16.dp)
                    .clickable {
                        startActivity(Intent(this@RPGCardActivity,LifeCycleComposeActivity::class.java))
                    }
            )
            var str by remember { mutableStateOf(10) }
            var int by remember { mutableStateOf(10) }
            var agi by remember { mutableStateOf(10) }
            var cat by remember { mutableStateOf(100) }

            //status
            Row (
                modifier = Modifier.fillMaxWidth().background(color = Color.LightGray).padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ){
                    Button(onClick = {
                        str++
                    }) {
                        Image(
                            painter = painterResource(R.drawable.baseline_arrow_drop_up_24),
                            contentDescription = "up",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(text = "Str", fontSize = 32.sp)
                    Text(text = str.toString(), fontSize = 32.sp)
                    Button(onClick = {str--}) {
                        Image(
                            painter = painterResource(R.drawable.outline_arrow_drop_down_24),
                            contentDescription = "up",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Button(onClick = {agi++}) {
                        Image(
                            painter = painterResource(R.drawable.baseline_arrow_drop_up_24),
                            contentDescription = "up",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(text = "Agi", fontSize = 32.sp)
                    Text(text = agi.toString(), fontSize = 32.sp)
                    Button(onClick = {agi--}) {
                        Image(
                            painter = painterResource(R.drawable.outline_arrow_drop_down_24),
                            contentDescription = "up",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Button(onClick = {int++}) {
                        Image(
                            painter = painterResource(R.drawable.baseline_arrow_drop_up_24),
                            contentDescription = "up",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(text = "Int", fontSize = 32.sp)
                    Text(text = int.toString(), fontSize = 32.sp)
                    Button(onClick = {int--}) {
                        Image(
                            painter = painterResource(R.drawable.outline_arrow_drop_down_24),
                            contentDescription = "up",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ){
                    Button(onClick = {cat++}) {
                        Image(
                            painter = painterResource(R.drawable.baseline_arrow_drop_up_24),
                            contentDescription = "up",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(text = "Cat", fontSize = 32.sp)
                    Text(text = cat.toString(), fontSize = 32.sp)
                    Button(onClick = {cat--}) {
                        Image(
                            painter = painterResource(R.drawable.outline_arrow_drop_down_24),
                            contentDescription = "up",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun previewScreen() {
        RPGCardView()
    }


}