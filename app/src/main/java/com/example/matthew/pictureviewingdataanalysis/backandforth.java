package com.example.matthew.pictureviewingdataanalysis;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created by Matthew on 2/5/2017.
 */

public class backandforth extends RelativeLayout {

    private Paint paintYellow, paintRed;
    private float positionX = 1, positionY = 1;
    private float velocityX = 0, velocityY = 0;
    private float velocityMag = 45;
    private float offset = 45;
    private int state = 0;
    private int delayCount = 0, delayMax = 30;
    private float radius = 35;

    public backandforth(Context context) {
        super(context);
        setUp();
    }

    public backandforth(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public backandforth(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    private void setUp() {
        paintYellow = new Paint();
        paintRed = new Paint();
        paintYellow.setColor(Color.YELLOW);
        paintYellow.setAlpha(172);
        paintRed.setColor(Color.RED);
        this.setBackgroundColor(Color.rgb(131, 131, 255));
        velocityX = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (state == 0) {
        } else if (state == 1) {
            velocityX = velocityMag;
            velocityY = 0;
            if (positionX > getWidth()) {
                state = 2;
                positionX = getWidth() - offset;
            }
        } else if (state == 2) {
            Log.e("count", ":" + delayCount);
            velocityX = 0;
            velocityY = 0;
            if (delayCount > delayMax) {
                state = 3;
                delayCount = 0;
            } else {
                delayCount++;
            }
        } else if (state == 3) {
            velocityX = 0;
            velocityY = velocityMag;
            if (positionY > getHeight()) {
                state = 4;
                positionY = getHeight() - offset;
            }
        } else if (state == 4) {
            velocityX = 0;
            velocityY = 0;
            if (delayCount > delayMax) {
                state = 5;
                delayCount = 0;
            } else {
                delayCount++;
            }
        } else if (state == 5) {
            velocityX = -velocityMag;
            velocityY = 0;
            if (positionX < 0) {
                state = 6;
                positionX = offset;
            }
        } else if (state == 6) {
            velocityX = 0;
            velocityY = 0;
            if (delayCount > delayMax) {
                state = 7;
                delayCount = 0;
            } else {
                delayCount++;
            }
        } else if (state == 7) {
            velocityX = 0;
            velocityY = -velocityMag;
            if (positionY < 0) {
                state = 8;
                positionY = offset;
            }
        } else if (state == 8) {
            state = 9;

        } else if (state == 9) {
            velocityX = 0;
            velocityY = 0;
            radius=300;
        }
        positionX += velocityX;
        positionY += velocityY;
        canvas.drawCircle(positionX, positionY, radius, paintRed);
    }

    public int getState() {
        return state;
    }

    public void start() {
        state = 1;
    }

    public void setBallPosition(double x, double y) {
        positionX = (float) x;
        positionY = (float) y;
    }
}
