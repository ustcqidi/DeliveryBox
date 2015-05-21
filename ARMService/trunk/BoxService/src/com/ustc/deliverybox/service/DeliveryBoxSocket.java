package com.ustc.deliverybox.service;

/**
 Author: Qi Di
 Date: 2014-1-31
 Brief: Socket service for DeliveryBox realtime communication
 */

import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import com.ustc.deliverybox.emitter.Emitter;
import com.ustc.deliverybox.gpio.DeliveryBoxGPIO;
import com.ustc.deliverybox.socketio.client.IO;
import com.ustc.deliverybox.socketio.client.Socket;
import com.ustc.deliverybox.util.Actions;
import com.ustc.deliverybox.util.ConstDef;
import com.ustc.deliverybox.util.HardwareUtil;
import com.ustc.deliverybox.util.Logger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DeliveryBoxSocket extends Service implements ConstDef {

	private static final String TAG = DeliveryBoxSocket.class.getSimpleName();

	private Socket mSocket = null;

	private boolean isSocketConnected = false;

	@Override
	public void onCreate() {

		IO.Options opts = new IO.Options();
		opts.forceNew = true;
		opts.reconnection = true;

		try {
			mSocket = IO.socket(BACKEND_URL, opts);
		} catch (URISyntaxException e) {
			Logger.error(TAG, e.toString());
			isSocketConnected = false;
		}

		super.onCreate();

		Logger.info(TAG, "onCreate");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Logger.info(TAG, "onUnbind");

		mSocket.disconnect();
		isSocketConnected = false;

		return super.onUnbind(intent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		Logger.info(TAG, "onBind");

		if (!isSocketConnected)
			mSocket.connect();

		mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				Logger.info(TAG, "event EVENT_CONNECT \n");
				sendBroadcast(new Intent(Actions.ACTION_ON_LINE));
				mSocket.emit("RegisterCabinet",
						HardwareUtil.getDeviceID(getApplicationContext()));
				isSocketConnected = true;
			}
		});

		mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				sendBroadcast(new Intent(Actions.ACTION_OFF_LINE));
				Logger.info(TAG, "event EVENT_CONNECT_ERROR \n");
				isSocketConnected = false;
			}
		});

		mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				Logger.info(TAG, "event EVENT_DISCONNECT \n");
				isSocketConnected = false;
			}
		});

		mSocket.on(Socket.EVENT_RECONNECT, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				sendBroadcast(new Intent(Actions.ACTION_OFF_LINE));
				Logger.info(TAG, "event EVENT_RECONNECT \n");
			}
		});

		mSocket.on(Socket.EVENT_RECONNECT_ATTEMPT, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				Logger.info(TAG, "event EVENT_RECONNECT_ATTEMPT \n");
			}
		});

		mSocket.on(Socket.EVENT_RECONNECT_ERROR, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				Logger.info(TAG, "event EVENT_RECONNECT_ERROR \n");
				isSocketConnected = false;
			}
		});

		mSocket.on(Socket.EVENT_RECONNECT_FAILED, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				Logger.info(TAG, "event EVENT_RECONNECT_FAILED \n");
				isSocketConnected = false;
			}
		});

		mSocket.on(Socket.EVENT_RECONNECTING, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				Logger.info(TAG, "event EVENT_RECONNECTING \n");
			}
		});

		mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				sendBroadcast(new Intent(Actions.ACTION_OFF_LINE));
				Logger.info(TAG, "event EVENT_CONNECT_TIMEOUT \n");
				isSocketConnected = false;
			}
		});

		mSocket.on("OpenBox", new Emitter.Listener() {

			@Override
			public void call(Object... args) {

				JSONObject obj = null;

				try {
					obj = new JSONObject(args[0].toString());

					Logger.info(TAG, "event OpenBox " + obj.getString("boxID")
							+ "\n");

					int boxID = 0;

					// fix crash issue when boxID is not number
					try {
						boxID = Integer.parseInt(obj.getString("boxID"));
					} catch (NumberFormatException e) {

						if (mSocket != null) {
							
							JSONObject jsonRes = new JSONObject();
							jsonRes.put("res", -1);
							jsonRes.put("cabinetid", HardwareUtil.getDeviceID(getApplicationContext()));
							jsonRes.put("boxid", boxID);
							
							mSocket.emit("OpenBoxRes", jsonRes.toString());
						}

						return;
					}
					// fix crash issue end

					int ret = DeliveryBoxGPIO.openBox(boxID);

					if (mSocket != null) {
						
						JSONObject jsonRes = new JSONObject();
						jsonRes.put("res", ret);
						jsonRes.put("cabinetid", HardwareUtil.getDeviceID(getApplicationContext()));
						jsonRes.put("boxid", boxID);
						
						mSocket.emit("OpenBoxRes", jsonRes.toString());
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});

		return null;
	}

}
