package com.kdevillers.question5;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TotalActivity extends AppCompatActivity {
    private TextView total;
    private Transaction commande;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        total = findViewById(R.id.total);
        try (FileInputStream fis = openFileInput("commande.ser");){
            ObjectInputStream ois = new ObjectInputStream(fis);
            commande = (Transaction) ois.readObject();
            ois.close();
        }catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
        Integer valeurTotal = commande.calculerTotal();
        total.setText(valeurTotal.toString());
    }
}

