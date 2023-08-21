package kerian.devillers.example.annexe1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class ListeActivity extends AppCompatActivity {

    private ListView liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recupererMemo());

        liste = findViewById(R.id.listeMemo);
        liste.setAdapter(arrayAdapter);
    }
    public ArrayList<String> recupererMemo(){
        ArrayList<String> temp = new ArrayList<>();
        FileInputStream fis;
        BufferedReader br = null;
        InputStreamReader isr;
        try {
            fis = openFileInput("memos.txt");
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String line;
            while((line = br.readLine()) != null){
                temp.add(line);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            fermerflux(br);
        }

        return temp;
    }
    public void fermerflux(Reader r){
        try {
            r.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}