package kerian.devillers.example.Annexe1b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    TextView reponse;
    Button boutA;
    Button boutB;
    Button boutC;
    Button boutD;
    Ecouteur ec;
    FileInputStream fis;
    BufferedReader br = null;
    InputStreamReader isr;

    Button boutE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reponse = (TextView)findViewById(R.id.reponse);
        boutA = findViewById(R.id.buttona);
        boutB = findViewById(R.id.buttonb);
        boutC = findViewById(R.id.buttonc);
        boutD = findViewById(R.id.buttond);
        boutE = findViewById(R.id.buttonE);

        ec = new Ecouteur();

        boutA.setOnClickListener(ec);
        boutB.setOnClickListener(ec);
        boutC.setOnClickListener(ec);
        boutD.setOnClickListener(ec);
        boutE.setOnClickListener(ec);
    }

    private class Ecouteur implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            try {
                fis = openFileInput("doc.txt");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

            if (view == boutA){
                reponse.setText(String.valueOf(getNbLignes()));
            }
            if (view == boutB){
                reponse.setText(String.valueOf(getNbChar()));
            }
            if (view == boutC){
                reponse.setText(String.valueOf(getNbC()));
            }
            if (view == boutD){
                ecritNom();
            }
            if (view == boutE){
                reponse.setText(String.valueOf(getNbMots()));
            }

            fermerFluLect(br);
        }
    }

    private int getNbLignes(){
        int compteur = 0;
        try {
            String line;
            while((line = br.readLine()) != null){
                compteur++;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return compteur;
    }
    private int getNbChar(){
        int compteur = 0;
        try {
            String line;
            while((line = br.readLine()) != null){
                compteur += line.length();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return compteur;
    }
    private int getNbC(){
        int compteur = 0;
        try {
            String line;
            while((line = br.readLine()) != null){
                for(int cara = 0; cara < line.length(); cara++){
                    if (line.charAt(cara) == 'c' || line.charAt(cara) == 'C'){
                        compteur++;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return compteur;
    }
    private void ecritNom(){
        FileOutputStream fos;
        BufferedWriter bw = null;
        OutputStreamWriter osw;
        try {
            fos = openFileOutput("doc.txt", Context.MODE_APPEND);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            bw.write(String.valueOf("Kerian"));
            bw.newLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            fermerFluEcr(bw);
        }
    }
    private int getNbMots(){
        int compteur = 0;
        Scanner sc = new Scanner(isr);
        while((sc.hasNext())){
            sc.next();
            compteur++;
        }
        return compteur;
    }

    private void fermerFluEcr(Writer w){
        try {
            w.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void fermerFluLect(Reader r){
        try {
            r.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}