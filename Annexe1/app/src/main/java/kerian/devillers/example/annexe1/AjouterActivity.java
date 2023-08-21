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

            FileOutputStream fos;
            BufferedWriter bw = null;
            OutputStreamWriter osw;
            try {
                fos = openFileOutput("memos.txt", Context.MODE_APPEND);
                osw = new OutputStreamWriter(fos);
                bw = new BufferedWriter(osw);
                bw.write(String.valueOf(texte.getText()));
                bw.newLine();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                fermerflux(bw);
            }
            finish();
        }
        public void fermerflux(Writer w){
            try {
                w.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}