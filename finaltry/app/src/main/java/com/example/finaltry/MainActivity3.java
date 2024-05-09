package com.example.finaltry;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import androidx.activity.result.ActivityResultContracts;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class MainActivity3 extends AppCompatActivity {
    TextView tvv;
    private static final int REQUEST_CODE = 1;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        tvv=findViewById(R.id.textView5);








        Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                ImageView img2 = findViewById(R.id.imageView22);

                Uri selectedImageUri = data.getData();
                img2.setImageURI(selectedImageUri);
                Log.d(TAG, "Image URI: " + selectedImageUri);
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                //img2.setImageBitmap(bitmap);
                FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
                FirebaseVision firebaseVision = FirebaseVision.getInstance();
                FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();

                //Process the Image
                Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(firebaseVisionImage);
                task.addOnSuccessListener(firebaseVisionText -> {
                    //Set recognized text from image in our TextView
                    String textt = firebaseVisionText.getText();

                    tvv.setText(textt);
                    if(!textt.isEmpty()){
                        Intent i = new Intent(MainActivity3.this,Gemini_API.class);
                        i.putExtra("recognizedtext",textt);
                        startActivity(i);
                    }
                    else{

                        Toast.makeText(this, "text not found", Toast.LENGTH_SHORT).show();
                    }




                });
                task.addOnFailureListener(e ->
                {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });

            } else {
                Log.e(TAG, "Selecting picture cancelled or no image selected.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception in onActivityResult: " + e.getMessage());
        }


    }
}