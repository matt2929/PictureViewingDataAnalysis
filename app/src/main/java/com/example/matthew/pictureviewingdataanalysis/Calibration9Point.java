package com.example.matthew.pictureviewingdataanalysis;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Matthew on 2/16/2017.
 */

public class Calibration9Point {
    private ArrayList<ArrayList<Double>> dataX = new ArrayList<>();
    private ArrayList<ArrayList<Double>> dataY = new ArrayList<>();
    private boolean calibrationValuesSet = false;
    private double[] averagesX = new double[9];
    private double[] averagesY = new double[9];

    private double centerPointX = 0, centerPointY = 0, topLeftX = 0, topLeftY = 0, topRightX = 0, topRightY = 0, downLeftX = 0, downLeftY = 0, downRightX = 0, downRightY = 0;

    public Calibration9Point() {
        for (int i = 0; i < 9; i++) {
            dataX.add(new ArrayList<Double>());
            dataY.add(new ArrayList<Double>());
        }
    }

    public void recordCalibration(double x, double y, int state) {
        if (state != -1) {
            dataX.get(state).add(x);
            dataY.get(state).add(y);
        }
    }

    public double[] getXYPoportional(double xGaze, double yGaze, double width, double height) {
        if (!calibrationValuesSet) {
            for (int s = 0; s < dataX.size(); s++) {
                for (int v = 0; v < dataX.get(s).size(); v++) {
                    averagesX[s] += dataX.get(s).get(v);
                    averagesY[s] += dataY.get(s).get(v);
                }
                averagesX[s] /= dataX.get(s).size();
                averagesY[s] /= dataY.get(s).size();

                Log.e("average", ":" + s + "x:" + averagesX[s] + "\n" + "y:" + averagesY[s]);
            }
            centerPointX = averagesX[3];
            centerPointY = averagesY[3];
            topLeftX = averagesX[1];
            topLeftY = averagesY[1];
            topRightX = averagesX[2];
            topRightY = averagesY[2];
            downRightX = averagesX[4];
            downRightY = averagesY[4];
            downLeftX = averagesX[5];
            downLeftY = averagesY[5];
            calibrationValuesSet = true;
        }

        double gazeWidth = Math.abs(Math.abs(((topLeftX + downLeftX) / 2)) - (Math.abs(((topRightX + downRightX) / 2))));
        double gazeHeight = Math.abs(Math.abs(((topLeftY + topRightY) / 2)) - (Math.abs(((downLeftY + downRightY) / 2))));
        double gazeOffsetX = Math.abs(((topLeftX + downLeftX) / 2) - xGaze);
        double gazeOffsetY = Math.abs(((topLeftY + topRightY) / 2) - yGaze);
        return new double[]{(gazeOffsetX*width)/gazeWidth, (gazeOffsetY*width)/gazeHeight};
    }
}
