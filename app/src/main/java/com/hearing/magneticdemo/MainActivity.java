package com.hearing.magneticdemo;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor sensor;

    private LineChartView lineChartX;
    private LineChartView lineChartY;
    private LineChartView lineChartZ;
    private LineChartView lineChartS;

    private LineChartUtil lineChartUtilX;
    private LineChartUtil lineChartUtilY;
    private LineChartUtil lineChartUtilZ;
    private LineChartUtil lineChartUtilS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        lineChartX = (LineChartView) findViewById(R.id.chart_x);
        lineChartY = (LineChartView) findViewById(R.id.chart_y);
        lineChartZ = (LineChartView) findViewById(R.id.chart_z);
        lineChartS = (LineChartView) findViewById(R.id.chart_sum);

        lineChartUtilX = new LineChartUtil();
        lineChartUtilY = new LineChartUtil();
        lineChartUtilZ = new LineChartUtil();
        lineChartUtilS = new LineChartUtil();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void handle_btn(View view) {
        Button btn = (Button) view;
        if (btn.getText().toString().equals("开始测量")) {
            btn.setText("结束测量");
            btn.setBackgroundColor(Color.rgb(120, 250, 130));
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            btn.setText("开始测量");
            btn.setBackgroundColor(Color.rgb(250, 120, 130));
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double value = Math.sqrt(sensorEvent.values[0]*sensorEvent.values[0] + sensorEvent.values[1]*
                sensorEvent.values[1] +sensorEvent.values[2]*sensorEvent.values[2]);

        lineChartUtilX.addData(lineChartX, sensorEvent.values[0], "X");
        lineChartUtilY.addData(lineChartY, sensorEvent.values[1], "Y");
        lineChartUtilZ.addData(lineChartZ, sensorEvent.values[2], "Z");
        lineChartUtilS.addData(lineChartS, (float) value, "S");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
