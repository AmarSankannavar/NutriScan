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

public class itemDetails extends AppCompatActivity {
    TextView positives, negatives;

    String pros,cons,itm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        positives=findViewById(R.id.positives);

        Gemini_pro model1 = new Gemini_pro();

         itm = getIntent().getStringExtra("item_name");
        pros="list the positives of the food item: "+itm+" list should not contain images and description";


        model1.getResponse(pros, new ResponseCallback() {
            @Override
            public void onResponse(String response) {
                String pans = "Positives"+"\n"+response;
                positives.setMovementMethod(new ScrollingMovementMethod());
                positives.setText(pans);




            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(itemDetails.this,"Error:"+throwable.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }
}