package com.example.aaaa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val findViewById = findViewById<TextView>(R.id.tv)
        findViewById.setOnClickListener {  }
    }
}