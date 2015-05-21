package com.ustc.deliverybox.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.ustc.deliverybox.command.HttpRequestExecutor;
import com.ustc.deliverybox.util.Actions;
import com.ustc.deliverybox.util.ConstDef;
import com.ustc.deliverybox.util.UpdateInfo;
import com.ustc.deliverybox.util.Utils;

/**
 * Created by tangnan on 15-1-9.
 */
public class CheckUpdateService extends IntentService {

    private final String TAG = this.getClass().getName();

    private HttpRequestExecutor mHttpExecutor;
    private String action = null;

    public CheckUpdateService() {
        super("CheckUpdateService");
    }

    private int getLocalVersion() {
        int versionCode = 1;
        PackageManager nPackageManager = getPackageManager();
        PackageInfo nPackageInfo = null;
        try {
            nPackageInfo = nPackageManager.getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionCode = nPackageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    private UpdateInfo parseJSON(String json) {
        if (json == null) {
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            int versionCode = jsonObject.getInt("version_code");
            String versioName = jsonObject.getString("version_name");
            String md5 = jsonObject.getString("md5sum");
            String url = jsonObject.getString("download_url");
            return new UpdateInfo(versionCode, versioName, url, md5);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException", e);
            return null;
        }
    }

    private String fetchNewVersion() {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;
        try {
            HttpGet request = new HttpGet(URI.create(ConstDef.CHECK_UPDATE_URL));
            HttpEntity entity = mHttpExecutor.execute(request);
            if (entity != null) {
                reader = new BufferedReader(new InputStreamReader(entity.getContent()), 1024);
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (mHttpExecutor.isAborted()) {
                        break;
                    }
                    stringBuilder.append(line);
                }
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            mHttpExecutor = new HttpRequestExecutor();
        }
        Date d = new Date();
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putLong(ConstDef.PREF_LAST_UPDATE_CHECK, d.getTime())
                .apply();
        String result = fetchNewVersion();
        UpdateInfo info = parseJSON(result);
        if (info == null) {
            return;
        }
        if (info.getVersionCode()  > getLocalVersion()) {
            //yes,I found a new version
            //start download with out ask user
            //DownloadService.start(this, info);
            Intent finishedIntent = new Intent(Actions.ACTION_NEW_VERSION);
            finishedIntent.putExtra(ConstDef.PREF_UPDATE_EXTRA_INFO,(Parcelable)info);
            //handle request error here
            sendBroadcast(finishedIntent);
        } else {
        	Utils.reboot();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        action = intent.getAction();
        if (TextUtils.equals(Actions.ACTION_CANCEL, action)) {
            synchronized (this) {
                if (mHttpExecutor != null) {
                    mHttpExecutor.abort();
                }
            }
            return START_NOT_STICKY;
        }
        return super.onStartCommand(intent, flags, startId);
    }

}
