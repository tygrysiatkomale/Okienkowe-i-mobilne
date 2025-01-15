package com.example.krety;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {

    private Paint molePaint;
    private float moleX, moleY;
    private float moleRadius;
    private float velocityX, velocityY;
    private Random random;
    private int score;

    private Handler handler = new Handler();
    private final int FRAME_RATE = 16;
    private Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            updatePosition();
            invalidate();
            handler.postDelayed(this, FRAME_RATE);
        }
    };

    public GameView(Context context) {
        super(context);
        init();
    }

    private void init() {
        molePaint = new Paint();
        molePaint.setColor(Color.RED);

        random = new Random();
        score = 0;

        moleRadius = 100;
        moleX = 0;
        moleY = 0;

        velocityX = random.nextInt(11) - 5;
        velocityY = random.nextInt(11) - 5;
        // Upewniamy się, że nie jest 0
        if (velocityX == 0) velocityX = 3;
        if (velocityY == 0) velocityY = 3;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        resetMolePosition(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(moleX, moleY, moleRadius, molePaint);

        Paint scorePaint = new Paint();
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(60);
        canvas.drawText("Score: " + score, 50, 50, scorePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float touchX = event.getX();
            float touchY = event.getY();

            float dx = touchX - moleX;
            float dy = touchY - moleY;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            if (distance <= moleRadius) {
                score++;
                resetMolePosition(getWidth(), getHeight());
                invalidate();
            }
        }
        return true;
    }

    private void resetMolePosition(int width, int height) {
        moleX = random.nextInt(width - (int)moleRadius * 2) + moleRadius;
        moleY = random.nextInt(height - (int)moleRadius * 2) + moleRadius;

        molePaint.setColor(Color.rgb(
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)));

        moleRadius = 50 + random.nextInt(101);
        velocityX = random.nextInt(21) - 5;
        velocityY = random.nextInt(21) - 5;
        if (velocityX == 0) velocityX = 3;
        if (velocityY == 0) velocityY = 3;
    }

    private void updatePosition() {
        moleX += velocityX;
        moleY += velocityY;

        float leftBound = moleRadius;
        float rightBound = getWidth() - moleRadius;
        float topBound = moleRadius;
        float bottomBound = getHeight() - moleRadius;

        if (moleX < leftBound) {
            moleX = leftBound;
            velocityX = -velocityX;
        } else if (moleX > rightBound) {
            moleX = rightBound;
            velocityX = -velocityX;
        }

        if (moleY < topBound) {
            moleY = topBound;
            velocityY = -velocityY;
        } else if (moleY > bottomBound) {
            moleY = bottomBound;
            velocityY = -velocityY;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        handler.post(updateTask);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(updateTask);
    }
}
