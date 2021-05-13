package com.example.myproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {
    private ImageView iv_image;
    Uri imageUri;
    private static  final  int TAKE_IMAGE_CODE=10001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        iv_image=findViewById(R.id.iv_image);

    }
}