package com.hihi.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class MenuLayout extends Activity {

    final Context context = this;
    public static boolean STT_SOUND = true;
    SoundMenu soundMenu;
    Button btnPlay, btnSound, btnGuide, btnInformation, btnQuit;
    long animationTime;
    PopupGuide popupGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnSound = (Button) findViewById(R.id.btnSound);
        btnGuide = (Button) findViewById(R.id.btnGuide);
        btnInformation = (Button) findViewById(R.id.btnInformation);
        btnQuit = (Button) findViewById(R.id.btnQuit);
        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animationbutton);
        animationTime = animation.getDuration();
        createMedia();
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPlay.startAnimation(animation);
                animation.start();
                Bundle bundle = new Bundle();
                bundle.putBoolean("STT_SOUND", STT_SOUND);
                Intent intent = new Intent(MenuLayout.this, MainActivity.class);
                intent.putExtra("Key", bundle);
                startActivity(intent);
            }
        });
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnQuit.startAnimation(animation);
                animation.start();
                finish();
            }
        });
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundMenu.mediaPlayer.isPlaying()) {
                    soundMenu.mediaPlayer.pause();
                    STT_SOUND = false;
                    btnSound.setBackgroundResource(R.drawable.btnsoundoff);
                } else if (!soundMenu.mediaPlayer.isPlaying()) {
                    soundMenu.mediaPlayer.start();
                    STT_SOUND = true;
                    btnSound.setBackgroundResource(R.drawable.btnsoundon);
                }
            }
        });
        btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupGuide = new PopupGuide(context);
                popupGuide.show();
            }
        });
        btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuLayout.this, "Made by LAQ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void createMedia() {
        final SoundMenu myMedia = new SoundMenu(this);
        soundMenu.mediaPlayer = myMedia.myPLAYER;
        soundMenu.mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundMenu.mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!soundMenu.mediaPlayer.isPlaying() && STT_SOUND == false) {
            soundMenu.mediaPlayer.pause();
        } else if (STT_SOUND == true) {
            soundMenu.mediaPlayer.start();
        }
    }
}
