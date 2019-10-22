package com.febrianwulandari.mysensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDetector implements SensorEventListener {
    private static final  float SHAKE_THRESHOLD = 2.7f; //untuk sensor gerakan
    private static final int SHAKE_TIME = 500; // untuk sensor waktu
    private static final int SHAKE_COUNT_RESET=3000; //untuk sensor jumlah

    private onShakeListener mListener;
    private long mShakeTimestamp;
    private int mShakeCount;

    public interface onShakeListener{
        public void onShake(int count);
    }

    public ShakeDetector(onShakeListener Listener) {
        mListener = Listener;
    }

    //sensornya berubah
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //cek listener setelah sensor berubah
        if(mListener == null)
        return;
        //value nilai goyangannya
        // sensor event dimbil daro public void on sensor changed
        float x  = sensorEvent.values[0] / SensorManager.GRAVITY_EARTH;
        float y = sensorEvent.values[0] / SensorManager.GRAVITY_EARTH;
        float z = sensorEvent.values[0] / SensorManager.GRAVITY_EARTH;
        float gForce = (float) Math.sqrt(x*x + y*y +z*z);
        if(gForce > SHAKE_THRESHOLD){
            long now = System.currentTimeMillis();
            if(mShakeTimestamp + SHAKE_TIME > now) return;
            if (mShakeTimestamp + SHAKE_COUNT_RESET < now){
                mShakeCount = 0;
            }
            mShakeTimestamp = 0;
            mShakeCount++;
            mListener.onShake(mShakeCount);
        }

    }

    //accuracy berubah
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
