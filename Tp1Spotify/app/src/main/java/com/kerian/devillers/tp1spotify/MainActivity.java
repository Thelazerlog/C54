package com.kerian.devillers.tp1spotify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static SpotifyDiffuseur instance;
    public Button boutonPlay;
    public TextView nomChanson;
    public TextView nomArtiste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = SpotifyDiffuseur.getInstance(this);
        boutonPlay = findViewById(R.id.bouttonPlay);
        nomChanson = findViewById(R.id.nomChanson);
        nomArtiste = findViewById(R.id.nomArtiste);

        boutonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (instance.isPlaying()){
                    instance.pause();
                    System.out.println("pause");}
                else
                    instance.play();

                instance.updateInfo();
                nomChanson.setText(instance.getNomChanson());
                nomArtiste.setText(instance.getNomArtiste());
            }
        });
    }
}