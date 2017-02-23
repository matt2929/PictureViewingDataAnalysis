package com.example.matthew.pictureviewingdataanalysis;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created by Matthew on 2/16/2017.
 */

public class NinePointCalibrationView extends RelativeLayout {
    private Paint paintYellow, paintRed;
    private float positionX = 1, positionY = 1;
    private Point[] points;
    private final int GAZETIME = 2000, MOVEMENTTIME = 2000;
    private boolean moving = true;
    private long startTime = System.currentTimeMillis();
    private int calibrationIndex = 0;
    private boolean runningCalibration = false;

    public NinePointCalibrationView(Context context) {
        super(context);
        setUp();
    }

    public NinePointCalibrationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public NinePointCalibrationView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    }

    private void setPoint() {
        points = new Point[]{
                new Point(0, 0),
                new Point(getWidth() / 2, 0),
                new Point(getWidth(), 0),
                new Point(0, getHeight() / 2),
                new Point(getWidth() / 2, getHeight() / 2),
                new Point(getWidth(), getHeight() / 2),
                new Point(0, getHeight()),
                new Point(getWidth() / 2, getHeight()),
                new Point(getWidth(), getHeight())};

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setPoint();
        if (runningCalibration) {
            setBackgroundColor(Color.BLACK);
            if (moving) {
                paintYellow.setColor(Color.RED);

                if (Math.abs(System.currentTimeMillis() - startTime) > MOVEMENTTIME) {
                    moving = false;
                    startTime = System.currentTimeMillis();
                }
            } else {
                paintYellow.setColor(Color.YELLOW);

                if (Math.abs(System.currentTimeMillis() - startTime) > GAZETIME) {
                    calibrationIndex++;
                    startTime = System.currentTimeMillis();
                    moving = true;
                    if (calibrationIndex >= 9) {
                        calibrationIndex = 0;
                        runningCalibration = false;
                    }
                }
            }
            Point point = points[calibrationIndex];
            Log.e("data:", "" + "" + point.getX());
            canvas.drawCircle(point.getX(), point.getY(), 45, paintYellow);

        } else {
            setBackgroundColor(Color.TRANSPARENT);
            canvas.drawCircle(positionX, positionY, 45, paintYellow);
        }
    }

    public void setBallPosition(double x, double y) {
        positionX = (float) x;
        positionY = (float) y;
    }

    private class Point {
        float x, y;

        protected Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }

    public void start() {
        runningCalibration = true;
        startTime = System.currentTimeMillis();
        calibrationIndex = 0;
    }

    public int getCurrentDot() {
        if (runningCalibration == false || moving == true) {
            return -1;
        } else {
            return calibrationIndex;
        }
    }

    public boolean isRunningCalibration() {
        return runningCalibration;
    }
}
