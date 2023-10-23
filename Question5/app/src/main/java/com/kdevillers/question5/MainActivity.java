package com.kdevillers.question5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private static Stock instance;
    private SimpleAdapter adapteurListe;
    private Vector<HashMap<String, Object>> infoVoitures;
    private ListView listeItems;
    private Ecouteur ec;
    private Transaction commande;
    private TextView listeCommande;
    private Button commander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listeItems = findViewById(R.id.listeItems);
        listeCommande = findViewById(R.id.listeAchetees);
        commander = findViewById(R.id.commander);

        instance = Stock.getInstance();
        commande = new Transaction();
        lireCommande();
        infoVoitures = new Vector<>();
        ec = new Ecouteur();

        for (VehiculeHyundai voiture:instance.retournerListe()) {
            infoVoitures.add(
                    new HashMap() {{
                        put("prix", voiture.getPrix());
                        put("alimentation", voiture.getAlimentation());
                        put("nom", voiture.getNom());
                    }}
            );
        }
        adapteurListe = new SimpleAdapter(
                this,
                infoVoitures,
                R.layout.layout_item,
                new String[]{"prix", "alimentation", "nom"},
                new int[]{R.id.cout, R.id.typeVoiture, R.id.nomVoiture}
        );
        listeItems.setAdapter(adapteurListe);
        listeItems.setOnItemClickListener(ec);
        commander.setOnClickListener(ec);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try(FileOutputStream fos = openFileOutput("commande.ser", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(commande);
        }
        catch ( Exception e)
        {
            e.printStackTrace();
        }
    }

    private class Ecouteur implements AdapterView.OnItemClickListener, View.OnClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String alimetation = (String) infoVoitures.get(position).get("alimentation");
            String nom = (String) infoVoitures.get(position).get("nom");

            if (commande.ajouterVoiture(instance.trouverObjet(nom, alimetation))){
                listeCommande.setText(listeCommande.getText() + "\n" + nom + " - " + alimetation);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, TotalActivity.class);
            startActivity(intent);
        }
    }
    protected void lireCommande() {
        try (FileInputStream fis = openFileInput("commande.ser");){
            ObjectInputStream ois = new ObjectInputStream(fis);
            commande = (Transaction) ois.readObject();
            for (VehiculeHyundai voiture: commande.getAchats()) {
                listeCommande.setText(listeCommande.getText() + "\n" + voiture.getNom() + " - " + voiture.getAlimentation());
            }

            ois.close();
        }catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }

}