package com.example.finaltry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class chatHere extends AppCompatActivity {
    TextView msg;

    String chat_msg;

    Button send;

    EditText edd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_here);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String item = getIntent().getStringExtra("item_name");
        msg=findViewById(R.id.textView7);
        send=findViewById(R.id.send);
        edd=findViewById(R.id.query);
        Gemini_pro modell = new Gemini_pro();
        String hexColor = "#427C14"; // Hexadecimal color value for orange
        int color = Color.parseColor(hexColor);
        send.setBackgroundColor(color);
      //  String query =  "consider yourself a food safety info provider, tell me is it safe for human consumption"+"\n"+recognizedText;
        send.setOnClickListener(v -> {
            chat_msg = item + " is a food item. and my question is: "+edd.getText().toString();
            msg.setText(chat_msg);
            modell.getResponse(chat_msg, new ResponseCallback() {
                @Override
                public void onResponse(String response) {
                    msg.setText(response);




                }

                @Override
                public void onError(Throwable throwable) {
                    Toast.makeText(chatHere.this,"Error:"+throwable.getMessage(), Toast.LENGTH_SHORT).show();


                }
            });

        });


    }
}