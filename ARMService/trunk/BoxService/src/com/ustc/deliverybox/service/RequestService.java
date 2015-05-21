package com.ustc.deliverybox.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.ustc.deliverybox.command.HttpRequestExecutor;
import com.ustc.deliverybox.util.ConstDef;
import com.ustc.deliverybox.util.Utils;

public class RequestService extends IntentService {

	public static final String ACTION_CANCEL = "com.jinlin.deliverybox.action.CANCEL";
	public static final String ACTION_JSON = "com.jinlin.deliverybox.action.JSON";
    public static final String EXTRA_JSON = "EXTRA_JSON";

	private HttpRequestExecutor mHttpExecutor;
    private Handler handler = null;
	private String action = null;

	private final String TAG = this.getClass().getName();

	public RequestService() {
		super("GetVcodeService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
        handler = new Handler(Looper.getMainLooper());
		synchronized (this) {
			mHttpExecutor = new HttpRequestExecutor();
		}
		Intent finishedIntent = new Intent(action);
        String result = null;
		result = request(intent);
        if (result == null) {
            return;
        }
        finishedIntent.putExtra(EXTRA_JSON, result);
        //handle request error here
		sendBroadcast(finishedIntent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		action = intent.getAction();
		if (TextUtils.equals(ACTION_CANCEL, action)) {
			synchronized (this) {
				if (mHttpExecutor != null) {
					mHttpExecutor.abort();
				}
			}
			return START_NOT_STICKY;
		}
		return super.onStartCommand(intent, flags, startId);
	}

    private  String request(Intent intent) {
        String jsonString = intent.getExtras().getString(ACTION_JSON);
        try {
            return request(new JSONObject(jsonString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

	private String request(JSONObject jesonObjct) {
		HttpPost request = new HttpPost(ConstDef.OPEN_BOX_URL);
		addRequestHeaders(request);
		try {
			request.setEntity(new StringEntity(jesonObjct.toString(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpEntity entity;
		try {
			entity = mHttpExecutor.execute(request);
            if(entity == null) {
                return null;
            }
			String json = EntityUtils.toString(entity, "UTF-8");
            Log.i(TAG, json);
            return json;
		} catch (HttpHostConnectException e) {
            e.printStackTrace();
            handlerException(e);
        } catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}

	private void addRequestHeaders(HttpPost post) {
		post.addHeader("Content_type", "Application/json;charset=UTF-8");
	}

    private void handlerException(Exception e) {
        String errorStr = e.getCause().toString();
        if(errorStr.contains("ENETUNREACH")) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Utils.toast(RequestService.this, R.string.no_network);
                }
            });
        } else if (errorStr.contains("ECONNREFUSED")) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Utils.toast(RequestService.this, R.string.connect_to_server_error);
                }
            });
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Utils.toast(RequestService.this, R.string.unknown_error);
                }
            });
        }
    }

}
