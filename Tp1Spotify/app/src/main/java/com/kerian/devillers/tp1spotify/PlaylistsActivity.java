package com.kerian.devillers.tp1spotify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Vector;

public class PlaylistsActivity extends AppCompatActivity {
    ListView listPlaylists;
    Ecouteur ec;
    Intent intent;
    String choix;
    SimpleAdapter adapteurListe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_choice);

        listPlaylists = findViewById(R.id.listPlaylists);
        ec = new Ecouteur();

        Vector<HashMap<String, Object>> infoPlatlists = new Vector<>();

        HashMap<String, Object> un = new HashMap();
        un.put("name", "1");
        un.put("nbTracks", "1");
        un.put("image", "1");
        un.put("lien", "spotify:playlist:37i9dQZF1DWYMokBiQj5qF");

        HashMap<String, Object> deux = new HashMap();
        deux.put("name", "2");
        deux.put("nbTracks", "2");
        deux.put("image", "2");
        deux.put("lien", "spotify:album:1B2QrHbMox8vPXUY7rXAFp");

        HashMap<String, Object> trois = new HashMap();
        trois.put("name", "3");
        trois.put("nbTracks", "3");
        trois.put("image", "3");
        trois.put("lien", "spotify:album:0YmI7KdXpmTikNmFHVMilu");

        HashMap<String, Object> quatre = new HashMap();
        quatre.put("name", "4");
        quatre.put("nbTracks", "4");
        quatre.put("image", "4");
        quatre.put("lien", "spotify:album:4IzV5XnSOvOBZ2z9WKsi3W");

        HashMap<String, Object> cinq = new HashMap();
        cinq.put("name", "5");
        cinq.put("nbTracks", "5");
        cinq.put("image", "5");
        cinq.put("lien", "spotify:album:1VGVJdmvOSRK2w9RKXk18A");

        HashMap<String, Object> six = new HashMap();
        six.put("name", "6");
        six.put("nbTracks", "6");
        six.put("image", "6");
        six.put("lien", "spotify:album:4L3cV9CUK0MVXUXLYkWY23");

        HashMap<String, Object> sept = new HashMap();
        sept.put("name", "7");
        sept.put("nbTracks", "7");
        sept.put("image", "7");
        sept.put("lien", "spotify:album:3j8Mg3DogmEVXNYrHbDWeX");

        infoPlatlists.add(un);
        infoPlatlists.add(deux);
        infoPlatlists.add(trois);
        infoPlatlists.add(quatre);
        infoPlatlists.add(cinq);
        infoPlatlists.add(six);
        infoPlatlists.add(sept);

        adapteurListe = new SimpleAdapter(
                this,
                infoPlatlists,
                R.layout.layout_playlist,
                new String[]{"lien"},
                new int[]{R.id.nomPlaylist}
        );

        listPlaylists.setAdapter(adapteurListe);

        for(int childIndex = 0; childIndex < listPlaylists.getChildCount(); childIndex++){
            listPlaylists.getChildAt(childIndex).setOnClickListener(ec);
        }
    }
    private class Ecouteur implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            intent = new Intent();
            choix = String.valueOf(v.getClass());
            intent.putExtra("user",choix);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
