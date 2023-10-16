package com.kerian.devillers.tp1spotify;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
    private TextView lienSite;
    private ImageView pagePlaylists;
    private androidx.activity.result.ActivityResultLauncher<Intent> launcher;

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
        lienSite = findViewById(R.id.lienSite);
        pagePlaylists = findViewById(R.id.pagePlaylists);


        chrono.start(); //Afin de faire que onChronometerTick commence à être appelé

        //Ticks
        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //Empêche onChronometerTick d'être appelé avant que updateInfo est fini au cas ou il faut changer la valeur du chrono
                chronometer.setOnChronometerTickListener(null);
                updateInfo();
                chronometer.setOnChronometerTickListener(this);
            }
        });
        //Play et pause
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
                    if (!chrono.isActivated()){ //Si une chanson n'est pas déja en train de jouer
                        chrono.setBase(SystemClock.elapsedRealtime() + tempsPause);
                        chrono.start();
                    }
                }
            }
        });

        //Changement chanson
        skipBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.previousSong();
            }
        });
        skipForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.nextSong();
            }
        });
        //Mouvement barre progress
        tempsChanson.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //instance.move(seekBar.getProgress()); //Marche pas, peut être besoin de premium pour bouger
                seekBar.setProgress((int) instance.getSongProgress());
            }
        });

        //Lien exterieur
        lienSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url= "https://www.cyberpunk.net/ca/fr";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        //Partie boomerang
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK){
                    instance.setActivePlaylist((String) result.getData().getSerializableExtra("lien"));
                }
            }
        });
        pagePlaylists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch(new Intent(MainActivity.this, PlaylistsActivity.class));
            }
        });
    }
    @Override
    public void onStop() {
        super.onStop();
        instance.pause();
    }

    private void updateInfo(){
        if (instance.isConnected()){
            instance.updateInfo();
            if (instance.songChanged()){ //Verfie si la chanson as été changée il y as peu
                newSong();
                instance.resetSongChanged(); //Dit a SpotifyDiffuseur que le changement de chanson à été pris en compte
            }
            updateSeekBar(); //Ajoute la seconde qui est passée
        }
    }
    private void updateSeekBar(){
        tempsChanson.setProgress(tempsChanson.getProgress() + 1000);
    }
    //Est appelé après un changement de chanson
    private void newSong(){
        nomChanson.setText(instance.getNomChanson());
        nomArtiste.setText(instance.getNomArtiste());
        imageChanson.setImageBitmap(instance.getCouvertureChanson());
        tempsChanson.setMax((int) instance.getSongLenght());
        //Met pas a zero au cas ou une chanson jouais déja a ouverture de l'app ou changement de playlist
        setChrono();
        tempsChanson.setProgress((int) instance.getSongProgress());
    }
    //Met le temps sur le chronometre égal au progress de la chanson
    private void setChrono() {
        chrono.setBase(SystemClock.elapsedRealtime() - instance.getSongProgress());
    }
}