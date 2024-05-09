package com.example.finaltry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Gemini_API extends AppCompatActivity {
    Button b3,det,b;

    String itemm;
    ConstraintLayout cl;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gemini_api);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        b=findViewById(R.id.gocon);
        cl = findViewById(R.id.cl);
        TextView t = findViewById(R.id.textView4);
        b3 = findViewById(R.id.gochat);
        det=findViewById(R.id.gopro);
        b3.setVisibility(View.GONE);
        det.setVisibility(View.GONE);
        cl.setVisibility(View.GONE);
        b.setVisibility(View.GONE);
        String hexColor = "#56A01C"; // Hexadecimal color value for orange
        int color = Color.parseColor(hexColor);
        b3.setBackgroundColor(color);
        det.setBackgroundColor(color);
        b.setBackgroundColor(color);

        ProgressBar pb = findViewById(R.id.progressBar);
        pb.setProgressTintList(ColorStateList.valueOf(color));

        String recognizedText = getIntent().getStringExtra("recognizedtext");
        itemm=recognizedText;
        if (recognizedText != null) {
           // t.setText(recognizedText);
        } else {
            Toast.makeText(this, "not recognized", Toast.LENGTH_SHORT).show();
        }

       // b.setOnClickListener(v -> {
            Gemini_pro model = new Gemini_pro();
            String query =  "consider yourself a food safety info provider, tell me is it safe for human consumption"+"\n"+recognizedText;
            pb.setVisibility(View.VISIBLE);
            t.setMovementMethod(new ScrollingMovementMethod());
            t.setText("");

          //  et.setText("");

            model.getResponse(query, new ResponseCallback() {
                @Override
                public void onResponse(String response) {
                    t.setText(response);
                    pb.setVisibility(View.GONE);
                    b3.setVisibility(View.VISIBLE);
                    det.setVisibility(View.VISIBLE);
                    b.setVisibility(View.VISIBLE);
                    cl.setVisibility(View.VISIBLE);
                    b3.setOnClickListener(v -> {
                        Intent chati = new Intent(Gemini_API.this, chatHere.class);
                        chati.putExtra("item_name", itemm);
                        startActivity(chati);
                    });
                    det.setOnClickListener(v -> {
                        Intent deti = new Intent(Gemini_API.this, itemDetails.class);
                        deti.putExtra("item_namee", itemm);
                        startActivity(deti);
                    });
                    b.setOnClickListener(v -> {
                        Intent deti = new Intent(Gemini_API.this, Negatives.class);
                        deti.putExtra("item_namee", itemm);
                        startActivity(deti);
                    });



                }

                @Override
                public void onError(Throwable throwable) {
                    Toast.makeText(Gemini_API.this,"Error:"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.GONE);

                }
            });


       // });
    }
}