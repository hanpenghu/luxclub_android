package com.hsbank.util.android.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 监听器：检测手机摇晃，实现“摇一摇”功能
 * 2016-04-11
 */
public class ShakeListener implements SensorEventListener {
	/** 速度阈值，当摇晃速度大于该值时，表明手机在快速摇晃 */
	private static final int SPEED_SHRESHOLD = 2000;
	/** 两次检测的时间间隔阈值: 70ms，小于该值则表明手机还在快速摇晃 */
	private static final int SHAKE_INTERVAL_SHRESHOLD = 70;
	/** 传感器管理器 */
	private SensorManager mSensorManager;
	/** 传感器 */
	private Sensor mSensor;
	/** 上下文对象 */
	private Context mContext;
	/** 手机在上一次检测时的坐标 */
	private float lastX;
	private float lastY;
	private float lastZ;
	/** 上次检测时间 */
	private long lastShakeTime;
	/** 摇一摇监听回调接口 */
	private ShakeCallback mCallback;

	/**
	 * 构造函数
	 * @param context		上下文
	 */
	public ShakeListener(Context context) {
		mContext = context;
		start();
	}

	/** 开始监听 */
	public void start() {
		// 得到传感器管理器
		mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
		if (mSensorManager != null) {
			// 得到加速度传感器
			mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		// 注册传感器
		if (mSensor != null) {
			mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
		}
	}

	/** 停止检测 */
	public void stop() {
		mSensorManager.unregisterListener(this);
	}

	/**
	 * 加速度传感器感应到变化后的处理器
	 * @param event		传感器事件
	 */
	public void onSensorChanged(SensorEvent event) {
		// 当前时间
		long currentShakeTime = System.currentTimeMillis();
		// 两次检测的时间间隔
		long timeInterval = currentShakeTime - lastShakeTime;
		// 判断是否 < 检测时间间隔
		if (timeInterval < SHAKE_INTERVAL_SHRESHOLD) {
			//本次间隔 < 检测时间间隔，表明手机还在快速摇晃，不处理，等待快速摇晃结束
			return;
		}
		// 现在的时间变成last时间
		lastShakeTime = currentShakeTime;
		// 得到x,y,z坐标
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		// 得到x,y,z的变化值
		float deltaX = x - lastX;
		float deltaY = y - lastY;
		float deltaZ = z - lastZ;
		// 将现在的坐标变成last坐标
		lastX = x;
		lastY = y;
		lastZ = z;
		// 计算手机摇晃的速茺
		double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / timeInterval * 10000;
		if (speed >= SPEED_SHRESHOLD) {
			// 达到速度阀值，发出提示：监听到手机完成了一次“摇一摇”的动作
			mCallback.onShake();
		}
	}

	/**
	 * 当传感器精度改变时回调该方法
	 * @param sensor		传感器
	 * @param accuracy		精度
	 */
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//do nothing
	}

	/**
	 * 设置加速度传感器监听回调接口
	 * @param callback
	 */
	public void setCallback(ShakeCallback callback) {
		mCallback = callback;
	}

	/**
	 * 摇一摇监听回调接口
	 */
	public interface ShakeCallback {
		/**
		 * “摇一摇”之后的事件处理器
		 */
		public void onShake();
	}

}