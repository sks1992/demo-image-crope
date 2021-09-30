package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MainActivity extends AppCompatActivity {
    ImageButton img_select, img_reset;
    ImageView img_view;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_reset = findViewById(R.id.reset);
        img_select = findViewById(R.id.image_select);
        img_view = findViewById(R.id.image_view);

        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.startPickImageActivity(MainActivity.this);
            }
        });

        img_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_view.setImageBitmap(null);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE
                && requestCode == Activity.RESULT_OK) {
            Uri image_uri = CropImage.getPickImageResultUri(this, data);

            if (CropImage.isReadExternalStoragePermissionsRequired(this, image_uri)) {
                uri = image_uri;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }
            } else {
                cropImage(image_uri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            img_view.setImageURI(result.getUri());

                   /*     if(requestCode ==RESULT_OK){
                img_view.setImageURI(result.getUri());
            }*/
        }
    }

    private void cropImage(Uri image_uri) {
        CropImage.activity(image_uri).setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true).start(this);
    }

}