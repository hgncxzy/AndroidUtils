package com.xzy.utils.shake;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * 摇一摇工具类。
 * 分别在Activity的onResume和onPause方法中调用该工具类的onResume和onPause方法
 *
 * @author xzy
 */
@SuppressWarnings("unused")
public class ShakeUtils implements SensorEventListener {

    private static final String TAG = "ShakeUtils";

    public ShakeUtils(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void setOnShakeListener(OnShakeListener onShakeListener) {
        mOnShakeListener = onShakeListener;
    }


    public void onResume() {
        isShake = false;
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        mSensorManager.unregisterListener(this);
    }

    //防止多次打开
    private boolean isShake = false;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = sensorEvent.values;
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            //这里可以调节摇一摇的灵敏度
            if ((Math.abs(values[0]) > SENSOR_VALUE
                    || Math.abs(values[1]) > SENSOR_VALUE
                    || Math.abs(values[2]) > SENSOR_VALUE)) {
                Log.e(TAG, "onSensorChanged: " + values[0]
                        + " " + values[1] + " " + values[2]);
                if (null != mOnShakeListener && !isShake) {
                    mOnShakeListener.onShake();
                    isShake = true;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public interface OnShakeListener {
        void onShake();
    }

    private SensorManager mSensorManager;
    private OnShakeListener mOnShakeListener = null;
    private static final int SENSOR_VALUE = 15;
}