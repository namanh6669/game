package com.hihi.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by Long on 23/2/2017.
 */

public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private static Canvas canvas;
    public boolean running;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();
        while (running) {
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.Draw(canvas);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            long now = System.nanoTime();
            long waitTime = (now - startTime) / 1000000;
            if (waitTime < 10) {
                waitTime = 10;
            }
            try {
                this.sleep(waitTime);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            startTime = System.nanoTime();
        }
    }
}
