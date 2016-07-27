package com.application.pram.test2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void button1_click(View v) {
        Log.d("Mainactivity", "Hello");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f = getFragmentManager().findFragmentByTag("HELLO");
        if(f == null) {
            HelloFragment hello = new HelloFragment();
            fragmentTransaction.add(R.id.fragment_container, hello, "HELLO");
            fragmentTransaction.commit();
        }
    }

    public void button2_click(View v) {
        Log.d("Mainactivity", "Whatsup");

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment f = getFragmentManager().findFragmentByTag("HELLO");
        if(f!= null) {
            fragmentTransaction.remove(f);
            fragmentTransaction.commit();
        }
    }
}
