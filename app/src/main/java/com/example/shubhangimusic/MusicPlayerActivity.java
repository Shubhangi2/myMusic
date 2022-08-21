package com.example.shubhangimusic;

import static com.example.shubhangimusic.MainActivity.myposition;
import static com.example.shubhangimusic.MainActivity.songsList;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.PrimitiveIterator;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {
    ArrayList<AudioModel> musicList;
    TextView title, cTime, tTime;
    ImageView left, right, pausePlay;
    SeekBar seekBar;
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
        seekBar = findViewById(R.id.mp_seekbar);
        cTime = findViewById(R.id.current_time);
        tTime = findViewById(R.id.total_time);

        title.setSelected(true);


        musicList = songsList;
        position = getIntent().getIntExtra("position", -1);

        getMusicWithResorces();

        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    cTime.setText(convertToMMSS(mediaPlayer.getCurrentPosition() + ""));
                }
                new Handler().postDelayed(this, 100);
            }
        });


     seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
         @Override
         public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
             if((mediaPlayer != null) && b){
                 mediaPlayer.seekTo(i);
             }
         }

         @Override
         public void onStartTrackingTouch(SeekBar seekBar) {

         }

         @Override
         public void onStopTrackingTouch(SeekBar seekBar) {

         }
     });

    }

    private void getMusicWithResorces() {
        AudioModel curSong = musicList.get(position);
        title.setText(curSong.getTitle());

        cTime.setText(convertToMMSS("0"));
        tTime.setText(convertToMMSS(curSong.getDuration()));


        if(musicList != null){
            uri = Uri.parse(curSong.getPath());
        }

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());

        }else{
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
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
        myposition = position;
    }

    private void nextSong(){
        if(position == musicList.size()-1)
        {
            Toast.makeText(this, "no next song", Toast.LENGTH_SHORT).show();
            return;
        }

        position = position + 1;
        getMusicWithResorces();
        myposition = position;

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

    public static String convertToMMSS(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }



}