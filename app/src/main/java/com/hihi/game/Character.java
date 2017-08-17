package com.hihi.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Long on 26/2/2017.
 */

public class Character {

    private GamePanel gamePanel;
    private Bitmap bitmap;
    private int x, y;
    private int speed = 1;
    private int jump = 0;
    private SoundJump soundJump;
    private int width;

    private int animationCol = 4;
    private int currentFrame = 0;
    private int animationState = 0;

    public Character(GamePanel gamePanel, Bitmap bitmap, int x, int y) {
        this.gamePanel = gamePanel;
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.width = bitmap.getWidth() / 5;
        soundJump = new SoundJump(gamePanel.getContext());
    }

    public void update() {
        checkground();
        checkAnimaiton();
        switchAnimation();
    }

    public void checkAnimaiton() {
        if (speed < 0) {
            animationState = 2;
        } else if (speed > 0) {
            animationState = 1;
        } else {
            animationState = 0;
        }
    }

    public void switchAnimation() {
        if (animationState == 0) {
            animationCol = 4;
            if (currentFrame >= (animationCol - 2)) {
                currentFrame = 0;
            } else {
                currentFrame += 1;
            }
        } else if (animationState == 1) {
            currentFrame = 2;
            animationCol = 0;
        } else if (animationState == 2) {
            currentFrame = 2;
            animationCol = 0;
        }
    }

    public void checkground() {
        if (y < gamePanel.getHeight() - gamePanel.getGroundHeight() - bitmap.getHeight()) {
            speed+=2;
        } else if (speed > 0) {
            speed = 0;
            y = gamePanel.getHeight() - gamePanel.getGroundHeight() - bitmap.getHeight();
            jump = 0;
        }
        y += speed;
    }


    public void draw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        Rect src = new Rect(currentFrame * width, 0, srcX + width, bitmap.getHeight());
        Rect dst = new Rect(x, y, x + width, y + bitmap.getHeight());
        canvas.drawBitmap(bitmap, src, dst, null);

    }

    public void onTouch() {
        jump++;
        if (y <= gamePanel.getHeight() - gamePanel.getGroundHeight() - bitmap.getHeight() && jump <= 2) {

            soundJump.play();
            speed = -(bitmap.getHeight()/4);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
