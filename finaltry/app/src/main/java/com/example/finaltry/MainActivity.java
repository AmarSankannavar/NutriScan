package com.example.finaltry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Button scanButton;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanButton = findViewById(R.id.scan);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String hexColor = "#56A01C"; // Hexadecimal color value for orange
        int color = Color.parseColor(hexColor);
        scanButton.setBackgroundColor(color);
        scanButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Enter_product.class);
            startActivity(intent);

        });
    }
}
