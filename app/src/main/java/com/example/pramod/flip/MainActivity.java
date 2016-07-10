package com.example.pramod.flip;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private boolean color = false;
    private View view;
    private boolean lastUpdate = false;
    private long lastTime;
    TextView countText;
    Vibrator v;
    Params param;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countText = (TextView) findViewById(R.id.count_value);
        param = new Params();

        // Get instance of Vibrator from current Context
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        lastTime = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset: {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Reset counter")
                        .setMessage("Do you want to reset")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                countText.setText("0");
                                param.setCount(0); // Reset count
                            }
                        })
                        .setNegativeButton("No", null)
                        .create();
                dialog.show();
            }
            return true;
            case R.id.set: {
                final EditText taskEditText = new EditText(this);
                taskEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                taskEditText.setRawInputType(Configuration.KEYBOARD_12KEY);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                     //           param.setCount((taskEditText.getText()));
                            }
                        })
                        .setNegativeButton("No", null)
                        .create();
            }
                return true;
            case R.id.help:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        long actualTime = event.timestamp;

        if((lastUpdate == true) && (z > 0)) {
            if (actualTime - lastTime < 200) {
                return;
            }
            lastTime = actualTime;
            lastUpdate = false;
            param.increment_count();
            countText.setText(Integer.toString(param.getCount()));
            // Vibrate for 400 milliseconds
            v.vibrate(50);
        }
        else if((lastUpdate == false) && (z < 0)) {
            if (actualTime - lastTime < 200) {
                return;
            }
            lastTime = actualTime;
            lastUpdate = true;
            param.increment_count();
            countText.setText(Integer.toString(param.getCount()));
            // Vibrate for 400 milliseconds
            v.vibrate(50);
        }
    }
}
