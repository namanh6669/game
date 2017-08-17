package com.hihi.game;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by ADMIN on 3/23/2017.
 */

public class SoundMenu {
    public static MediaPlayer mediaPlayer;
    public static MediaPlayer myPLAYER;

    public SoundMenu(Context context) {
        myPLAYER = MediaPlayer.create(context, R.raw.backgroundsound);
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
