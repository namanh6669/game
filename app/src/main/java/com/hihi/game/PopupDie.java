package com.hihi.game;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 3/26/2017.
 */

public class PopupDie extends Dialog {
    public static Button btnBack, btnReplay;
    public static TextView txtScore;
    public static ImageView imageView;

    public PopupDie(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_die);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnBack = (Button) findViewById(R.id.btnBack);
        btnReplay = (Button) findViewById(R.id.btnReplay);
        txtScore = (TextView) findViewById(R.id.txtScore);
        imageView = (ImageView) findViewById(R.id.imageView);
    }
}
