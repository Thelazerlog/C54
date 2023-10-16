package com.kerian.devillers.tp1spotify;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Vector;

public class PlaylistsActivity extends AppCompatActivity {
    ListView listPlaylists;
    Ecouteur ec;
    Intent intent;
    String choix;
    SimpleAdapter adapteurListe;

    Playlist playlist1;
    Playlist playlist2;
    Playlist playlist3;
    Playlist playlist4;
    Playlist playlist5;
    Playlist playlist6;
    Playlist playlist7;

    Playlist[] playlists;
    Vector<HashMap<String, Object>> infoPlatlists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_choice);

        listPlaylists = findViewById(R.id.listPlaylists);
        ec = new Ecouteur();

        //Si il faut rajouter une playlist just créer un objet et le mettre dans playlists
        playlist1 = new Playlist("Cyberpunk 2077 Official Playlist", 180, 50, R.drawable.playlist1, "spotify:playlist:37i9dQZF1DWYMokBiQj5qF");
        playlist2 = new Playlist("Cyberpunk 2077-Original Score", 130, 37, R.drawable.playlist2, "spotify:album:1B2QrHbMox8vPXUY7rXAFp");
        playlist3 = new Playlist("Cyberpunk 2077: Phantom Liberty (Original Score)",55,15, R.drawable.playlist3, "spotify:album:0YmI7KdXpmTikNmFHVMilu");
        playlist4 = new Playlist("Cyberpunk 2077: Radio, Vol. 1 (Original Soundtrack",37,10, R.drawable.playlist4, "spotify:album:4IzV5XnSOvOBZ2z9WKsi3W");
        playlist5 = new Playlist("Cyberpunk 2077: Radio, Vol. 2 (Original Soundtrack",35,9, R.drawable.playlist5, "spotify:album:1VGVJdmvOSRK2w9RKXk18A");
        playlist6 = new Playlist("Cyberpunk 2077: Radio, Vol. 3 (Original Soundtrack",40 ,12, R.drawable.playlist6, "spotify:album:4L3cV9CUK0MVXUXLYkWY23");
        playlist7 = new Playlist("Cyberpunk 2077: Radio, Vol. 4 (Original Soundtrack",48,5, R.drawable.playlist7, "spotify:album:3j8Mg3DogmEVXNYrHbDWeX");

        playlists = new Playlist[]{playlist1, playlist2, playlist3, playlist4, playlist5, playlist6, playlist7};

        //Boucle a travers playlistes et met dans les hashmap à l'aide des objets
        infoPlatlists = new Vector<>();
        for (Playlist playlist:playlists) {
            infoPlatlists.add(
                    new HashMap(){{
                        put("name", playlist.nom);
                        put("nbTracks", playlist.nbChansons);
                        put("image", playlist.image);
                        put("duree", playlist.duree);
                        put("lien", playlist.lien);
                    }}
            );
        }

        adapteurListe = new SimpleAdapter(
                this,
                infoPlatlists,
                R.layout.layout_playlist,
                new String[]{"name", "duree", "nbTracks", "image"},
                new int[]{R.id.nomPlaylist, R.id.duréePlaylist, R.id.nbChansons, R.id.imagePlaylist}
        );

        listPlaylists.setAdapter(adapteurListe);
        listPlaylists.setOnItemClickListener(ec);
    }
    private class Ecouteur implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            intent = new Intent();
            choix = (String) infoPlatlists.get(position).get("lien");
            intent.putExtra("lien", choix);
            setResult(RESULT_OK, intent);
            finish();

        }
    }

}
