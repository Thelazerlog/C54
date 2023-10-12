package com.kerian.devillers.tp1spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static SpotifyDiffuseur instance;
    private Button boutonPlay;
    private TextView nomChanson;
    private TextView nomArtiste;
    private ImageView imageChanson;
    private SeekBar tempsChanson;
    private TextView chiffreTempsChanson;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = SpotifyDiffuseur.getInstance(this);
        boutonPlay = findViewById(R.id.bouttonPlay);
        nomChanson = findViewById(R.id.nomChanson);
        nomArtiste = findViewById(R.id.nomArtiste);
        imageChanson = findViewById(R.id.songImage);
        tempsChanson = findViewById(R.id.songTime);
        chiffreTempsChanson = findViewById(R.id.textTimeSong);
        boutonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instance.isPlaying())
                    instance.pause();
                else
                    instance.play();
                updateInfo();
            }
        });

        tempsChanson.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                instance.move(progress);
                chiffreTempsChanson.setText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void updateInfo(){
        instance.updateInfo();

        nomChanson.setText(instance.getNomChanson());
        nomArtiste.setText(instance.getNomArtiste());
        imageChanson.setImageURI(Uri.parse(instance.getCouvertureChanson().toString()));
        tempsChanson.setMax((int) instance.getSongLenght());
        tempsChanson.setProgress((int) instance.getSongProgress());
        chiffreTempsChanson.setText((int) instance.getSongProgress());
    }
}