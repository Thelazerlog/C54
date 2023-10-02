package com.kerian.devillers.tp1spotify;

import android.content.Context;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.ImageUri;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.protocol.types.Uri;

import java.util.concurrent.atomic.AtomicReference;

import javax.security.auth.callback.Callback;

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
    private boolean isPlaying;
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
        playerApi.play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");
    }
    public void pause(){
        playerApi.pause();
    }
    public void move(float nbSecondes){

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
                //isPlaying = track;
            }
        });

    }
    public String getNomChanson(){
        return nom;
    }
    public String getNomArtiste(){
        return artiste;
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

