package com.example.shubhangimusic;

import static com.example.shubhangimusic.MainActivity.songsList;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class MusicPlayerActivity extends AppCompatActivity {
    ArrayList<AudioModel> musicList;
    TextView title, cTime, tTime;
    ImageView left, right, pausePlay;
    static MediaPlayer mediaPlayer;
    static Uri uri;

    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        title = findViewById(R.id.mp_title);
        left = findViewById(R.id.left_arrow);
        right = findViewById(R.id.right_arrow);
        pausePlay = findViewById(R.id.pause);

        title.setSelected(true);


        musicList = songsList;
        position = getIntent().getIntExtra("position", -1);

        getMusicWithResorces();


    }

    private void getMusicWithResorces() {
        AudioModel curSong = musicList.get(position);
        title.setText(curSong.getTitle());
//        mediaPlayer.reset();
        if(musicList != null){
            uri = Uri.parse(curSong.getPath());
        }

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();

        }else{
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        left.setOnClickListener(v->prevSong());
        right.setOnClickListener(v->nextSong());
        pausePlay.setOnClickListener(v->playPause());
    }

    private void prevSong(){
        if(position == 0){
            return;
        }
        position = position - 1;
        getMusicWithResorces();
    }

    private void nextSong(){
        if(position == musicList.size()-1)
        {
            Toast.makeText(this, "no next song", Toast.LENGTH_SHORT).show();
            return;
        }

        position = position + 1;
        getMusicWithResorces();

    }

    private void playPause(){
        if(mediaPlayer.isPlaying()){
            pausePlay.setImageResource(R.drawable.play_btn);
            mediaPlayer.pause();
        }else{
            pausePlay.setImageResource(R.drawable.pause_btn);
            mediaPlayer.start();
        }
    }


}