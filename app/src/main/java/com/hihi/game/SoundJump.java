package com.hihi.game;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by Long on 30/3/2017.
 */

public class SoundJump {
    private MenuLayout menuLayout;
    private int jumpSound;
    private SoundPool soundPool;
    private boolean soundPoolLoaded;
    public SoundJump(Context context){
        this.soundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 0);

        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPoolLoaded = true;
            }
        });

        this.jumpSound= this.soundPool.load(context, R.raw.jumpsound,1);
    }
    public void play()  {
        if(this.soundPoolLoaded && menuLayout.STT_SOUND == true) {
            float leftVolumn = 0.8f;
            float rightVolumn =  0.8f;
            int streamId = this.soundPool.play(this.jumpSound,leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }
}
