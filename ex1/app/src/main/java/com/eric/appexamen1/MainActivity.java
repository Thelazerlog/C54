package com.eric.appexamen1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Spinner spinner;

    Groupe[] liste = {new Groupe ("c23", R.drawable.c23),new Groupe("c34", R.drawable.c34),new Groupe("c44", R.drawable.c44)  };
    Groupe groupeSelect;
    Boolean firstEntry;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        spinner = findViewById(R.id.spinner);

        Vector<String> vec = new Vector<>();
        for ( Groupe g : liste)
        {
            vec.add(g.getNomCours());
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,vec );
        spinner.setAdapter(adapter);

        setImage();
        firstEntry = true;

        Ecouteur ec = new Ecouteur();
        spinner.setOnItemSelectedListener(ec);

    }
    private class Ecouteur implements AdapterView.OnItemSelectedListener
    {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ObjectOutputStream oos;
            index = position;
            if (firstEntry == false){
                try{
                    groupeSelect = liste[position];
                    FileOutputStream fos = openFileOutput("fichier.ser", Context.MODE_PRIVATE);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(groupeSelect);
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            setImage();
            firstEntry = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void setImage(){
        try{
            FileInputStream fis = openFileInput("fichier.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            groupeSelect = (Groupe)(ois.readObject());
            imageView.setImageResource(groupeSelect.getAdresseImage());
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}