package com.example.shubhangimusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;

public class MusicPlayerActivity extends AppCompatActivity {
    TextView textView;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        textView = findViewById(R.id.textView);

        position = getIntent().getIntExtra("position", -1);
        textView.setText("position is : " + position);
    }
}