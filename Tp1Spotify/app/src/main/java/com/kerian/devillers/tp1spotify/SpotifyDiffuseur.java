package com.kerian.devillers.tp1spotify;

import android.content.Context;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.ImageUri;
import com.spotify.protocol.types.Track;

public class SpotifyDiffuseur{
    private static SpotifyDiffuseur instance;

    private PlayerApi playerApi;
    private  SpotifyAppRemote appRemote;
    private Context context;
    private static final String CLIENT_ID = "ab437e59bc3642639f14fb62075519f2";
    private static final String REDIRECT_URI = "com.kerian.devillers.tp1spotify://callback";
    private String nom;
    private String artiste;
    private ImageUri image;

    private double songLenght;
    private long songProgress;

    private boolean isPlaying = false;
    public static SpotifyDiffuseur getInstance(Context context) {
        if (instance == null)
            instance = new SpotifyDiffuseur(context);
        return instance;
    }

    private SpotifyDiffuseur(Context context) {
        this.context = context;
        connect();
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
            }
        });
    }

    private void connected() {
    }

    public void play(){
        isPlaying = true;
        playerApi.play("spotify:playlist:37i9dQZF1DWYMokBiQj5qF");
    }
    public void pause(){
        isPlaying = false;
        playerApi.pause();
    }
    public void move(long postion){
        songProgress = postion;
        playerApi.seekTo(songProgress);
    }
    public boolean isPlaying(){
        return isPlaying;
    }
    public void updateInfo(){
        playerApi.subscribeToPlayerState().setEventCallback(playerState -> {
            final Track track = playerState.track;
            if (track != null) {
                nom = track.name;
                artiste = track.artist.name;
                image = track.imageUri;
                songLenght = track.duration;
                songProgress = playerState.playbackPosition;
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

    public ImageUri getCouvertureChanson(){
        return image;
    }
    public void nextSong(){
        playerApi.skipNext();
    }
    public void previousSong(){
        playerApi.skipPrevious();
    }

}

