package com.eric.labonte.appsaveinstancestate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IdentificationActivity extends AppCompatActivity {
    Intent intent;
    Button bouton;
    TextView champNom, champPrenom;
    Ecouteur ec;
    Utilisateur utilisateur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        bouton = findViewById(R.id.boutonConfirmer);
        champNom = findViewById(R.id.champNom);
        champPrenom = findViewById(R.id.champPrenom);

        ec = new Ecouteur();
        bouton.setOnClickListener(ec);
    }
    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            intent = new Intent();
            utilisateur = new Utilisateur(champNom.getText().toString(), champPrenom.getText().toString());

            intent.putExtra("user",utilisateur);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}