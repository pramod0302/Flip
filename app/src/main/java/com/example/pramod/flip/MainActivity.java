package com.example.pramod.flip;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private boolean color = false;
    private View view;
    private boolean lastUpdate = false;
    private long lastTime;
    private Integer count = 0;
    TextView valueText, countText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        valueText = (TextView) findViewById(R.id.set_values);
        countText = (TextView) findViewById(R.id.count_value);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        lastTime = System.currentTimeMillis();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        valueText.setText("Val: " + " " + Float.toString(x) + " " + Float.toString(y) + " " + Float.toString(z));
        long actualTime = event.timestamp;

        if((lastUpdate == true) && (z > 0)) {
            if (actualTime - lastTime < 200) {
                return;
            }
            lastTime = actualTime;
            lastUpdate = false;
            count++;
            countText.setText(Integer.toString(count));
        }
        else if((lastUpdate == false) && (z < 0)) {
            if (actualTime - lastTime < 200) {
                return;
            }
            lastTime = actualTime;
            lastUpdate = true;
            count++;
            countText.setText(Integer.toString(count));
        }
    }
}
