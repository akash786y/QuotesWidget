package com.example.quoteswidget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity  // Ensure this import is present

class MainActivity : AppCompatActivity() {  // Extend AppCompatActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // Set the layout file
    }
}