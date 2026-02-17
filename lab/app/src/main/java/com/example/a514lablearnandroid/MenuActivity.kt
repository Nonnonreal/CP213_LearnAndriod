package com.example.a514lablearnandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, MainActivity::class.java))
                }) {
                    Text("RPGCardActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, PokedexActivity::class.java))
                }) {
                    Text("PokedexActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, LifeCycleComposeActivity::class.java))
                }) {
                    Text("LifeCycleComposeActivity")
                }
                Button(onClick = {
                    startActivity(Intent(this@MenuActivity, SharedPreferencesActivity::class.java))
                }) {
                    Text("SharedPreferencesUtil")
                }
            }
        }
    }
}