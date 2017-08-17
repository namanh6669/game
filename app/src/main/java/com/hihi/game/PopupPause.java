package com.hihi.game;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by ADMIN on 3/25/2017.
 */
public class PopupPause extends Dialog {
    public static Button btnResume, btnBack, btnReplay;

    public PopupPause(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pause);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnResume = (Button) findViewById(R.id.btnResume);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnReplay = (Button) findViewById(R.id.btnReplay);
    }
}