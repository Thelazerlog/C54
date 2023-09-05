package com.eric.labonte.appsaveinstancestate;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AccueilActivity extends AppCompatActivity {
    TextView texteBienvenue;
    Ecouteur ec;
    Button bouton;
    androidx.activity.result.ActivityResultLauncher<Intent> launcher;
    Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bouton = findViewById(R.id.boutonStartActivityForResult);
        texteBienvenue = findViewById(R.id.texteSalutations);
        ec = new Ecouteur();
        bouton.setOnClickListener(ec);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CallBackClass());
        try {
            utilisateur = (Utilisateur) savedInstanceState.getSerializable("util");
            if(utilisateur != null)
            texteBienvenue.setText("Bonjour " + utilisateur.getPrenom() + " " + utilisateur.getNom()  + "!");
        }catch (NullPointerException nullPointerException){
            texteBienvenue.setText("Bonjour!");
        }
    }
    private class CallBackClass implements ActivityResultCallback<ActivityResult> {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == RESULT_OK){
                utilisateur = (Utilisateur) result.getData().getSerializableExtra("user");
                texteBienvenue.setText("Bonjour " + utilisateur.getPrenom() + " " + utilisateur.getNom()  + "!");
            }
        }
    }
    private class Ecouteur implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            launcher.launch(new Intent(AccueilActivity.this, IdentificationActivity.class));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("util", utilisateur);
    }
}