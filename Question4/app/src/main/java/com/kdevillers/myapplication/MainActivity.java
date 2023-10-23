package com.kdevillers.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    InputStream fis;
    BufferedReader br = null;
    InputStreamReader isr;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.imageAndroid);

        fis = getResources().openRawResource(R.raw.coordonnees);
        isr = new InputStreamReader(fis);
        br = new BufferedReader(isr);
        try {
            String line;
            Path path = new Path();
            path.moveTo(image.getX(), image.getY());
            while((line = br.readLine()) != null){
                String[] coordonee = line.split(",");
                System.out.println(coordonee[0] + ", "+  coordonee[1]);
                path.lineTo(Integer.parseInt(coordonee[0]), Integer.parseInt(coordonee[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}