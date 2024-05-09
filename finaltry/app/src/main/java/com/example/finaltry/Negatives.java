package com.example.finaltry;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Negatives extends AppCompatActivity {
    String cons,itm;

    TextView negatives;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_negatives);
        negatives=findViewById(R.id.negatives);
        cons="list the negatives of the food item: "+itm+" list should not contain images and description";

        Gemini_pro model2 = new Gemini_pro();
        itm = getIntent().getStringExtra("item_name");
        cons="list the negatives of the food item: "+itm+" list should not contain images and description";
        model2.getResponse(cons, new ResponseCallback() {
            @Override
            public void onResponse(String response) {
                String nans = "Negatives"+"\n"+response;
                negatives.setMovementMethod(new ScrollingMovementMethod());
                negatives.setText(nans);




            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(Negatives.this,"Error:"+throwable.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }
}