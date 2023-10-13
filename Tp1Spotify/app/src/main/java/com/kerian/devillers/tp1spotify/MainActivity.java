package com.kerian.devillers.tp1spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
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
    private Chronometer chrono;

    private ImageView skipBack;
    private ImageView skipForward;

    private long tempsPause = 0;
    private boolean asCommence = false;


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
        chrono = findViewById(R.id.chronometer);
        skipBack = findViewById(R.id.skipBack);
        skipForward = findViewById(R.id.skipForward);

        chrono.start(); //Afin de faire que onChronometerTick commence à être appelé

        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //Empêche onChronometerTick d'être appelé avant que updateInfo est fini au cas ou il faut changer la valeur du chrono
                chronometer.setOnChronometerTickListener(null);
                updateInfo();
                chronometer.setOnChronometerTickListener(this);
            }
        });
        boutonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instance.isPlaying()){
                    instance.pause();
                    chrono.stop();
                    tempsPause = chrono.getBase() - SystemClock.elapsedRealtime();
                }
                else{
                    instance.play();
                    chrono.setBase(SystemClock.elapsedRealtime() + tempsPause);
                    chrono.start();
                }
            }
        });
        skipBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.previousSong();
                resetChrono();
            }
        });
        skipForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.nextSong();
                resetChrono();
            }
        });
        tempsChanson.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                instance.move(seekBar.getProgress()); //Marche pas, peut être besoin de premium pour bouger
            }
        });
    }
    private void updateInfo(){
        if (instance.isConnected()){
            instance.updateInfo();
            if (asCommence){
                if (instance.songChanged()){ //Verfie si la chanson as été changée il y as peu
                    newSong();
                    instance.resetSongChanged(); //Dit a SpotifyDiffuseur que le changement de chanson à été pris en compte
                }
                updateSeekBar();
            }
            else {
                openApp();
            }
        }
    }
    private void resetChrono(){
        chrono.setBase(SystemClock.elapsedRealtime());
    }
    private void afficherInfoBase(){
        nomChanson.setText(instance.getNomChanson());
        nomArtiste.setText(instance.getNomArtiste());
        imageChanson.setImageBitmap(instance.getCouvertureChanson());
    }
    private void updateSeekBar(){
        tempsChanson.setProgress((int) instance.getSongProgress());
    }
    //Est appelé apres un changement de chanson
    private void newSong(){
        afficherInfoBase();
        tempsChanson.setMax((int) instance.getSongLenght());
        resetChrono();
    }
    //Met le temps sur le chronometre égal au progress de la chanson
    private void setChrono() {
        chrono.setBase(SystemClock.elapsedRealtime() - instance.getSongProgress());
    }

    //Est appelé une fois à l'ouverture de l'aplication afin d'afficher les informations au cas ou de la musique jouais déja
    private void openApp(){
        afficherInfoBase();
        tempsChanson.setMax((int) instance.getSongLenght());
        setChrono();
        asCommence = true;
    }
}