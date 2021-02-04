package com.gzeinnumer.mylibdirectoryexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gzeinnumer.mylibdirectoryexample.activities.CameraActivity;
import com.gzeinnumer.mylibdirectoryexample.activities.DirectoryActivity;
import com.gzeinnumer.mylibdirectoryexample.activities.FileActivity;
import com.gzeinnumer.mylibdirectoryexample.activities.GaleryActivity;
import com.gzeinnumer.mylibdirectoryexample.activities.InternetActivity;
import com.gzeinnumer.mylibdirectoryexample.activities.ZipActivity;
import com.gzeinnumer.mylibdirectoryexample.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {
    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DirectoryActivity.class));
            }
        });
        binding.btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FileActivity.class));
            }
        });
        binding.btnZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ZipActivity.class));
            }
        });
        binding.btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CameraActivity.class));
            }
        });
        binding.btnGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GaleryActivity.class));
            }
        });
        binding.btnInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InternetActivity.class));
            }
        });
    }
}