package com.hihi.game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Long on 23/2/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread mainThread;
    private ArrayList<Character> listCharacter = new ArrayList<>();
    private ArrayList<Ground> listGround = new ArrayList<>();
    private ArrayList<Enemy> listEnemy = new ArrayList<>();
    private ArrayList<Cloud> listCloud = new ArrayList<>();
    private Random r = new Random();
    private SoundGame soundGame;
    private Bitmap cloudBitmap;
    private Bitmap characterBitmap;
    private Bitmap groundBitmap;
    private Bitmap enemyBitmap;
    private Bitmap pauseBitmap;
    private int speed;
    private int ground_X = 0;
    private int score = 0;
    private boolean pause = false;
    private boolean popUpOn = false;
    private boolean checkDeath = false;


    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        mainThread = new MainThread(getHolder(), this);
        this.setFocusable(true);
    }

    public int getGroundHeight() {
        return groundBitmap.getHeight();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (pause == false) {
            startGame(holder);
        } else {
            if (popUpOn == false && checkDeath == false) {
                pauseMenu();
            }
            onPause();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (x >= this.getWidth() - pauseBitmap.getWidth() && x < (this.getWidth() - pauseBitmap.getWidth() + pauseBitmap.getWidth()) && y >= 0 && y < (pauseBitmap.getHeight())) {
                onPause();
                pauseMenu();
            } else {
                for (Character character : listCharacter) {
                    character.onTouch();
                }
            }
        }
        return false;
    }

    public void startGame(SurfaceHolder holder) {
        cloudBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cloud);
        listCloud.add(new Cloud(this, cloudBitmap, 550, this.getHeight() / 4));
        listCloud.add(new Cloud(this, cloudBitmap, 1150, this.getHeight() / 6));
        listCloud.add(new Cloud(this, cloudBitmap, 1550, this.getHeight() / 4));
        listCloud.add(new Cloud(this, cloudBitmap, 2050, this.getHeight() / 6));
        characterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.character);
        listCharacter.add(new Character(this, characterBitmap, 50, this.getHeight() - characterBitmap.getHeight()));
        groundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        enemyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() - enemyBitmap.getWidth(), this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));
        pauseBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btnpause);
        this.mainThread = new MainThread(holder, this);
        this.mainThread.setRunning(true);
        this.mainThread.start();
    }

    public void onResume() {
        SurfaceHolder holder = this.getHolder();
        soundGame.mediaPlayer.start();
        this.mainThread = new MainThread(holder, this);
        this.mainThread.setRunning(true);
        this.mainThread.start();
        pause = false;
    }

    public void onPause() {
        pause = true;
        boolean retry = true;
        mainThread.setRunning(false);
        while (retry) {
            try {
                soundGame.mediaPlayer.pause();
                mainThread.join();
                retry = false;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void pauseMenu() {
        popUpOn = true;
        final SurfaceHolder holder = this.getHolder();
        final PopupPause popupPause = new PopupPause(getContext());
        popupPause.show();
        popupPause.btnResume.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpOn = false;
                popupPause.dismiss();
                onResume();
            }
        });
        popupPause.btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        popupPause.btnReplay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    soundGame.mediaPlayer.start();
                    checkDeath = false;
                    score = 0;
                    mainThread.join();
                    listCloud.clear();
                    listCharacter.clear();
                    listEnemy.clear();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startGame(holder);
                popupPause.dismiss();

            }
        });
    }

    public void gameOver() {
        final SurfaceHolder holder = this.getHolder();
        checkDeath = true;
        final PopupDie popupDie = new PopupDie(getContext());
        popupDie.show();
        popupDie.imageView.setImageResource(R.drawable.gameover);
        popupDie.txtScore.setText("Score: "+String.valueOf(score));
        popupDie.btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        popupDie.btnReplay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    soundGame.mediaPlayer.start();
                    checkDeath = false;
                    score = 0;
                    mainThread.join();
                    listCloud.clear();
                    listCharacter.clear();
                    listEnemy.clear();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startGame(holder);
                popupDie.dismiss();
            }
        });
        onPause();
    }

    public int score(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        int highScore = prefs.getInt("key", 0);
        if (score > highScore) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("key", score);
            editor.commit();
            return score;
        } else return highScore;
    }

    public void update() {
        Cloud();
        deleteGround();
        deleteEnemy();
        collision();
    }

    public void Draw(Canvas canvas) {
        if (score <= 25) {
            setSpeed(17);
        } else if (score <= 75) {
            setSpeed(19);
        } else {
            setSpeed(21);
        }
        canvas.drawColor(Color.parseColor("#51fff0"));
        Paint scorePaint = new Paint();
        scorePaint.setTextSize(this.getHeight() / 30);
        scorePaint.setColor(Color.RED);
        scorePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Score: " + String.valueOf(score), 0, this.getHeight() / 15, scorePaint);
        canvas.drawText("High Score: " + String.valueOf(score(this.getContext())), 0, this.getHeight() / 30, scorePaint);
        canvas.drawBitmap(pauseBitmap, this.getWidth() - pauseBitmap.getWidth(), 0, null);
        update();
        addGround();
        for (Ground ground : listGround) {
            ground.draw(canvas);
        }
        for (Character character : listCharacter) {
            character.draw(canvas);
        }
        for (Enemy enemy : listEnemy) {
            enemy.draw(canvas);
        }
        for (Cloud cloud : listCloud) {
            cloud.draw(canvas);
        }
    }


    public void Cloud() {
        for (int i = 0; i < listCloud.size(); i++) {
            if (listCloud.get(i).getX() + cloudBitmap.getWidth() < 0) {
                listCloud.get(i).setX(listCloud.get(i).getX() + this.getWidth() + cloudBitmap.getWidth());
            }
        }
    }


    public void addGround() {
        while (ground_X <= this.getWidth() + groundBitmap.getWidth()) {
            listGround.add(new Ground(this, this.groundBitmap, ground_X, 0));
            ground_X += groundBitmap.getWidth();
        }
    }

    public void deleteGround() {
        for (int i = listGround.size() - 1; i >= 0; i--) {
            int ground_X = listGround.get(i).getX();
            if (ground_X <= -groundBitmap.getWidth()) {
                listGround.remove(i);
                listGround.add(new Ground(this, this.groundBitmap, ground_X + this.getWidth() + groundBitmap.getWidth(), 0));
            }
        }
    }

    public void addEnemy() {
        if (listEnemy.size() > 6) {
            for (int i = listEnemy.size(); i > 3; i--) {
                listEnemy.remove(i);
            }
        }
        for (int i = 0; i < listEnemy.size(); i++) {
            if (listEnemy.get(i).getX() == listEnemy.get(i + 1).getX()) {
                listEnemy.remove(i + 1);
            }
        }
        int x = r.nextInt(3);
        switch (x) {
            case 0:
                listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() - enemyBitmap.getWidth()+200, this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));
                listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() + 200, this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));

                listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() - enemyBitmap.getWidth() + 800, this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));

                listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() - enemyBitmap.getWidth() + 1700, this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));
                break;
            case 1:
                listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() - enemyBitmap.getWidth()+ 200, this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));

                listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() - enemyBitmap.getWidth() + 600, this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));
                listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() + 600, this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));

                listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() - enemyBitmap.getWidth() + 2100, this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));
                break;
            case 2:
                listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() - enemyBitmap.getWidth(), this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));

                listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() - enemyBitmap.getWidth() + 600, this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));
                listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() + 600, this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));
                if(r.nextInt(2)==1){
                    listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() + enemyBitmap.getWidth() + 600, this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));
                    if(r.nextInt(2)==0){
                        listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() + 2*enemyBitmap.getWidth() + 600, this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));
                    }
                }

                listEnemy.add(new Enemy(this, enemyBitmap, this.getWidth() - enemyBitmap.getWidth() + 1900, this.getHeight() - enemyBitmap.getHeight() - groundBitmap.getHeight()));
                break;

        }
    }

    public void deleteEnemy() {
        for (int i = 0; i < listEnemy.size(); i++) {
            if (listEnemy.get(i).getX() + enemyBitmap.getWidth() < 0) {
                listEnemy.remove(i);
                score++;
                addEnemy();
            }
        }
    }

    public void collision() {
        for (int i = 0; i < listEnemy.size(); i++) {
            for (int j = 0; j < listCharacter.size(); j++) {
                if (listEnemy.get(i).getX() <= listCharacter.get(j).getX() + characterBitmap.getWidth() / 6
                        && listCharacter.get(j).getY() > this.getHeight() - groundBitmap.getHeight() - enemyBitmap.getHeight() - characterBitmap.getHeight()
                        && listEnemy.get(i).getX() + enemyBitmap.getWidth() * 3 / 4 > listCharacter.get(j).getX()) {
                    listCharacter.remove(j);
                    ((Activity) getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onPause();
                            gameOver();
                        }
                    });
                }
            }
        }
    }
    private void finish() {
        Activity activity = (Activity)getContext();
        activity.finish();
    }

}
