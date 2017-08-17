package com.hihi.game;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    SoundGame soundGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Key");
        createMedia();
        if (bundle.getBoolean("STT_SOUND") == true) {
            soundGame.mediaPlayer.start();
        }
        setContentView(new GamePanel(this));
    }

    public void createMedia() {
        final SoundGame myMedia = new SoundGame(this);
        soundGame.mediaPlayer = myMedia.myPLAYER;
    }
}
