package com.example.finaltry;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class MainActivity2 extends AppCompatActivity {
    ImageView mImageView;
    Button btn_image, btn_txt;
    TextView txt_image;
    public static int REQUEST_CODE = 123;
    EditText ed1;

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap bitmap = (Bitmap) extras.get("data");
                            if (bitmap != null) {
                                mImageView.setImageBitmap(bitmap);

                                // Process the image
                                processImage(bitmap);
                            } else {
                                Toast.makeText(this, "No image data received", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "No extras received", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "No data received", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mImageView = findViewById(R.id.image);
        btn_image = findViewById(R.id.button4);
        btn_txt = findViewById(R.id.button2);
        txt_image = findViewById(R.id.txt_image);
        ed1 = findViewById(R.id.editTextText2);
        btn_image.setVisibility(View.GONE);
        btn_txt.setOnClickListener(v -> {
            if (!ed1.getText().toString().isEmpty()) {
                btn_image.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Enter the product name", Toast.LENGTH_SHORT).show();
            }
        });

        btn_image.setOnClickListener(view -> chooseImage());
    }

    private void chooseImage() {
        // Check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
        } else {
            // Open the camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(intent);
        }
    }

    private void processImage(Bitmap bitmap) {
        // Create a FirebaseVisionImage object from your image/bitmap.
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVision firebaseVision = FirebaseVision.getInstance();
        FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();

        // Process the Image
        Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(firebaseVisionImage);

        task.addOnSuccessListener(firebaseVisionText -> {
            // Set recognized text from image in our TextView
            String item = ed1.getText().toString();
            String text = firebaseVisionText.getText();
            txt_image.setText(text);
            if (!text.isEmpty()) {
                Intent i = new Intent(MainActivity2.this, Gemini_API.class);
                i.putExtra("recognizedtext", item);
                startActivity(i);
            } else {
                Toast.makeText(this, "Text not found", Toast.LENGTH_SHORT).show();
            }
        });

        task.addOnFailureListener(e -> {
            Toast.makeText(this, "Text recognition failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}
