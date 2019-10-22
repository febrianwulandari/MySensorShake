package com.febrianwulandari.mysensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //ada 4 variable di java
    //kenapa harus ada m karena mereka member variable
    private ShakeDetector mDetector;
    private Sensor mAccelemeter;
    private SensorManager mManager;
    private View mBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBackground = findViewById(R.id.background);
        mManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelemeter = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mDetector = new ShakeDetector(new ShakeDetector.onShakeListener() {
            @Override
            public void onShake(int count) {
                Random random = new Random();
                int color = Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256));
                mBackground.setBackgroundColor(color);
                Log.d("SHAKE", "Sudah goyang : " + count + "x");

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mManager.registerListener(mDetector, mAccelemeter, SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    protected void onPause() {
        mManager.unregisterListener(mDetector);
        super.onPause();

    }
}
