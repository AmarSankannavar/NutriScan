package com.example.finaltry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Step1 extends AppCompatActivity {
    Button b;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step1);
         b = findViewById(R.id.next2);
        String hexColor = "#427C14"; // Hexadecimal color value for orange
        int color = Color.parseColor(hexColor);
        b.setBackgroundColor(color);
         b.setOnClickListener(v -> {
             Intent st2i = new Intent(Step1.this, step2.class);
             startActivity(st2i);
         });

    }
}