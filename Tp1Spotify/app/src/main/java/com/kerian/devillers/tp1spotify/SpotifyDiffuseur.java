package com.kerian.devillers.tp1spotify;

import android.content.Context;
import android.graphics.Bitmap;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.Track;
import com.spotify.protocol.types.Uri;

public class SpotifyDiffuseur{
    private static SpotifyDiffuseur instance;
    private PlayerApi playerApi;
    private  SpotifyAppRemote appRemote;
    private Context context;
    private static final String CLIENT_ID = "ab437e59bc3642639f14fb62075519f2";
    private static final String REDIRECT_URI = "com.kerian.devillers.tp1spotify://callback";
    private String nom;
    private String artiste;
    private Bitmap image;
    private double songLenght; //Longeur en milisecondes
    private long songProgress; //Place dans la chanson en milisecondes
    private boolean songChanged = false; //Si une chanson à été changée et que MainActivity n'as pas encore géré le changement
    private boolean connected = false; //Si la connection a spotify est établie
    private String curSong = "aucune"; //Uri de la chanson en train de jouer
    private boolean isPlaying = false; //Si une chanson est en train de jouer
    private String activePlaylist = "spotify:playlist:37i9dQZF1DWYMokBiQj5qF";

    public static SpotifyDiffuseur getInstance(Context context) {
        if (instance == null)
            instance = new SpotifyDiffuseur(context);
        return instance;
    }

    private SpotifyDiffuseur(Context context) {
        this.context = context;
        this.connect();
    }

    private void connect(){
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(context, connectionParams, new Connector.ConnectionListener()
        {
            @Override
            public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                appRemote = spotifyAppRemote;
                playerApi = appRemote.getPlayerApi();
                connected();
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }

    private void connected() {
        this.updateInfo();
        connected = true;
    }
    public void play(){
        isPlaying = true;
        playerApi.play(activePlaylist);
    }
    public void pause(){
        isPlaying = false;
        playerApi.pause();
    }
    public void move(int postion){ //Change le progress de la chason à la position donnée en miliseconds
        this.updateInfo();
        playerApi.seekTo(postion);
        songProgress = postion;
    }
    public boolean isPlaying(){
        return isPlaying;
    }
    //Met a jour les informations du diffuseur sur la chanson jouée
    public void updateInfo(){
        playerApi.subscribeToPlayerState().setEventCallback(playerState -> {
            final Track track = playerState.track;
            if (track != null) {
                if (!playerState.track.uri.equals(curSong) && curSong != null){ //Verifie si la chanson qui joue est celle qui jouais avant ce tic
                    nom = track.name;
                    artiste = track.artist.name;
                    appRemote.getImagesApi().getImage(track.imageUri).setResultCallback(
                            new CallResult.ResultCallback<Bitmap>() {
                                @Override
                                public void onResult(Bitmap data) {
                                    image = data;
                                }
                            }
                    );
                    songLenght = track.duration;
                    songProgress = playerState.playbackPosition;
                    songChanged = true;
                    curSong = playerState.track.uri;
                }
            }
        });
    }
    public String getNomChanson(){
        return nom;
    }
    public String getNomArtiste(){
        return artiste;
    }
    public double getSongLenght(){
        return songLenght;
    }
    public long getSongProgress() {
        return songProgress;
    }
    public Bitmap getCouvertureChanson(){
        return image;
    }
    public void nextSong(){
        playerApi.skipNext();
    }
    public void previousSong(){
        playerApi.skipPrevious();
    }
    public boolean songChanged(){
            return songChanged;
    }
    public void resetSongChanged(){
        songChanged = false;
    }
    public boolean isConnected(){
        return connected;
    }
    public void setActivePlaylist(String activePlaylist) {
        this.activePlaylist = activePlaylist; //Met la playlise voue comme active
        this.play();
    }
}

