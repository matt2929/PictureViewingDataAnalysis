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

public class fivePointCalibration extends RelativeLayout {
    private Paint paintYellow, paintRed;
    private float positionX = 1, positionY = 1;
    private Point[] points;
    private long startTime=System.currentTimeMillis();
    private int calibrationIndex=0;
    private boolean runningCalibration=false;
    public fivePointCalibration(Context context) {
        super(context);
        setUp();
    }

    public fivePointCalibration(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public fivePointCalibration(Context context, AttributeSet attrs, int defStyleAttr) {
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
        points=new Point[]{
                new Point(0,0),
                new Point(getWidth()/2,0),
                new Point(getWidth(),0),
                new Point(0,getHeight()/2),
                new Point(getWidth()/2,getHeight()/2),
                new Point(getWidth(),getHeight()/2),
                new Point(0,getHeight()),
                new Point(getWidth()/2,getWidth()),
                new Point(getWidth(),getHeight())};
        }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(runningCalibration){
            if(Math.abs(System.currentTimeMillis()-startTime)>5000){
                calibrationIndex++;
                if(calibrationIndex>=9){
                    runningCalibration=false;
                }
            }
            for(int i=0;i<points.length;i++){
                Point point = points[i];
                if(i==calibrationIndex){
                    canvas.drawCircle(point.getX(),point.getY(),45,paintYellow);
                }else{
                    canvas.drawCircle(point.getX(),point.getY(),45,paintRed);

                }
            }
        }
    }

    private class Point{
        float x=0, y=0;
        protected Point(float x,float y){
            this.x=x;
            this.y=y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }
    public void start(){
        runningCalibration=true;
        startTime=System.currentTimeMillis();
        calibrationIndex=0;
    }
}
