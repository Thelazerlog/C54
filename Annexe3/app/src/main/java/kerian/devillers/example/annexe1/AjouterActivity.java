package kerian.devillers.example.annexe1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class AjouterActivity extends AppCompatActivity {

    private Button btnAjouter;
    private EditText texte;
    private Ecouteur ec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);

        btnAjouter = findViewById(R.id.button);
        texte = findViewById(R.id.memo);

        ec = new Ecouteur();
        btnAjouter.setOnClickListener(ec);

    }

    private class Ecouteur implements View.OnClickListener{
        @Override
        public void onClick(View view) {

            SingletonMemos.getInstance(AjouterActivity.this).ajouterMemo(texte.getText().toString());
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            SingletonMemos.getInstance(this).serialiserListe();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}