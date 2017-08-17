package com.hihi.game;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Admin on 3/30/2017.
 */

public class SoundGame {

    public static MediaPlayer mediaPlayer;
    public static MediaPlayer myPLAYER;

    public SoundGame(Context context) {
        myPLAYER = MediaPlayer.create(context, R.raw.gamesound);
        myPLAYER.setVolume(1f, 1f);
        myPLAYER.setLooping(true);

    }

    public void play() {
        myPLAYER.start();
    }

    public void pause() {
        myPLAYER.pause();
    }

    public boolean isPlaying() {
        return myPLAYER.isPlaying();
    }
}
