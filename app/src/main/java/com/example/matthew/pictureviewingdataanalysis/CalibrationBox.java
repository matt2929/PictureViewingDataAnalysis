package com.example.matthew.pictureviewingdataanalysis;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Matthew on 2/15/2017.
 */

public class CalibrationBox {
    ArrayList<ArrayList<Double>> dataX = new ArrayList<>();
    ArrayList<ArrayList<Double>> dataY = new ArrayList<>();
    boolean calibrationValuesSet = false;
    double[] averagesX = new double[9];
    double[] averagesY = new double[9];

    public CalibrationBox() {
        for (int i = 0; i < 9; i++) {
            dataX.add(new ArrayList<Double>());
            dataY.add(new ArrayList<Double>());
        }
    }

    public void recordCalibration(double x, double y, int state) {
        dataX.get(state).add(x);
        dataY.get(state).add(y);
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
                Log.e("average",":"+s+"x:"+averagesX[s]+"\n"+"y:"+averagesY[s]);
            }
            calibrationValuesSet = true;
        }
        double diffY = Math.abs(averagesY[1] - averagesY[5]);
        double diffX = Math.abs(averagesX[7] - averagesX[3]);
        double screenX = Math.abs(Math.abs(xGaze - averagesX[7]) * width / diffX);
        double screenY = Math.abs(Math.abs(yGaze - averagesY[1]) * height / diffY);

        return new double[]{screenX, screenY};
    }
}