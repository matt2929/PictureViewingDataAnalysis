package com.example.matthew.pictureviewingdataanalysis;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * Created by Matthew on 2/5/2017.
 */

public class SaveCSV {
    File _myFile;
    private FileOutputStream outputStream;
    private FileInputStream inputStream;
    private Context _context;
    public static String _fileName;
    File _file;
    int count = 0;
    private Calendar cal = Calendar.getInstance();
    private int month = cal.get(Calendar.MONTH) + 1;
    private int day = cal.get(Calendar.DAY_OF_MONTH);
    private int year = cal.get(Calendar.YEAR);
    private int hour = cal.get(Calendar.HOUR_OF_DAY);
    private int minute = cal.get(Calendar.MINUTE);
    private int second = cal.get(Calendar.SECOND);

    public SaveCSV(Context context) {
        FileOutputStream outputStream;
        FileInputStream inputStream;
        _context = context;
        _fileName = "Eye_" + (month + 1) + "M" + day + "D" + year + "Y_" + hour + "h" + minute + "m" + second + "s.csv";
    }

    public void saveData(int numFaces, int smileValue, int leftEyeBlink, int rightEyeBlink, int faceRollValue,
                         int faceYawValue, int facePitchValue, int horizontalGazeAngle, int verticalGazeAngle, double smoothx, double smoothy, double x, double y, double fftX, double fftY, int state) {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        try {
            String string = "";
            string += load();
            String value = "";
            string += hour + ":" + minute + ":" + second + "," + numFaces + "," + smileValue + "," + leftEyeBlink + "," + rightEyeBlink + "," + faceRollValue + "," + faceYawValue + "," + facePitchValue + "," + horizontalGazeAngle + "," + x + "," + y + "," + smoothx + "," + smoothy + "," + fftX + "," + fftY + "," + state + ";";
            outputStream = _context.openFileOutput(_fileName, Context.MODE_WORLD_READABLE);
            outputStream.write(string.getBytes());
            outputStream.close();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), _fileName);
            PrintWriter csvWriter;
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    csvWriter = new PrintWriter(new FileWriter(file, true));
                    int last = 0;
                    int count = 0;
                    csvWriter.print("EYE [" + (month + 1) + "/" + day + "/" + year + "]\nTime,NumFace,SmileVal,LeftBlink,RightBlink,FaceRoll,FaceYaw,FacePitch,HorizontalGaze,x,y,smoothX,smoothY,fftX,fftY");
                    csvWriter.append('\n');
                    for (int i = 0; i < string.length(); i++) {
                        if (string.charAt(i) == ';') {
                            csvWriter.print(string.substring(last + 1, i));
                            csvWriter.append('\n');
                            last = i;
                        }
                    }
                    csvWriter.print(string.substring(last, string.length() - 1));
                    csvWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                file.delete();
                try {
                    file.createNewFile();
                    csvWriter = new PrintWriter(new FileWriter(file, true));

                    csvWriter.print("EYE [" + (month + 1) + "/" + day + "/" + year + "]\nTime,NumFace,SmileVal,LeftBlink,RightBlink,FaceRoll,FaceYaw,FacePitch,HorizontalGaze,X,Y,smoothX,smoothY,fftX,fftY");
                    csvWriter.append('\n');
                    int last = 0;

                    for (int i = 0; i < string.length(); i++) {
                        if (string.charAt(i) == ';') {
                            csvWriter.print(string.substring(last + 1, i));
                            csvWriter.append('\n');
                            last = i;
                        }
                    }
                    csvWriter.print(string.substring(last, string.length() - 1));
                    csvWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String load() {
        String line1 = "";
        try {
            inputStream = _context.openFileInput(_fileName);
            if (inputStream != null) {
                InputStreamReader inputreader = new InputStreamReader(inputStream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line = "";
                try {
                    while ((line = buffreader.readLine()) != null) {
                        line1 += line;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {

            String error = "";
            error = e.getMessage();
        }
        return line1;
    }
}