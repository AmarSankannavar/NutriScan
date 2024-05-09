package com.example.finaltry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class step2 extends AppCompatActivity {
    Button b,b5;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step2);
        b=findViewById(R.id.next2);
        String hexColor = "#427C14"; // Hexadecimal color value for orange
        int color = Color.parseColor(hexColor);
        b.setBackgroundColor(color);
        b5=findViewById(R.id.button5);
        b5.setBackgroundColor(color);
        b5.setOnClickListener(v ->{
            Intent s1 = new Intent(step2.this,Step1.class);
            startActivity(s1);
        });
        b.setOnClickListener(v -> {
            Intent ma = new Intent(step2.this,MainActivity.class);
            startActivity(ma);
        });
    }
}