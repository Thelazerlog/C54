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
         return SingletonMemos.getInstance(this).getListeMemos();
    }
}