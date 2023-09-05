package kerian.devillers.example.annexe1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Intent listeActivity;
    private Intent ajouterActivity;
    private Button liste;
    private Button ajouter;
    private Button quitter;
    private Ecouteur ec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        liste = findViewById(R.id.afficher);
        ajouter = findViewById(R.id.ajouter);
        quitter = findViewById(R.id.Quitter);


        listeActivity = new Intent(MainActivity.this, ListeActivity.class );
        ajouterActivity = new Intent(MainActivity.this, AjouterActivity.class );

        ec = new Ecouteur();

        liste.setOnClickListener(ec);
        ajouter.setOnClickListener(ec);
        quitter.setOnClickListener(ec);

        try {
            SingletonMemos.getInstance(this).getListeMemos();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private class Ecouteur implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if (view.equals(liste)){
                startActivity(listeActivity);
            }
            else if (view.equals(ajouter)){
                startActivity(ajouterActivity);
            }
            else if (view.equals(quitter)){
                finish();
            }
        }
    }

}