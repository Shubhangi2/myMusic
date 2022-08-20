package com.example.shubhangimusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.IntentCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.window.SplashScreen;

public class SplashActivity extends AppCompatActivity {
    Button button;
    TextView text;
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        button = findViewById(R.id.button);
        text = findViewById(R.id.warnText);
        permission();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SplashActivity.this, "You clicked on me", Toast.LENGTH_SHORT).show();
                askPermission();
            }
        });

    }

    private void askPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            text.setVisibility(View.VISIBLE);
            text.setText("Please allow read permission from settings, for use this application");
        }else{
            ActivityCompat.requestPermissions(SplashActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void permission() {
        int result = ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            button.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}