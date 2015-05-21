package com.ustc.deliverybox.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;

import com.ustc.deliverybox.util.ConstDef;
import com.ustc.deliverybox.util.MD5;
import com.ustc.deliverybox.util.UpdateInfo;
import com.ustc.deliverybox.util.Utils;

/**
 * Created by tangnan on 15-3-21.
 */
public class DownloadService extends Service {

	private final static int DOWNLOADING = 0;
	private final static int DOWNLOAD_COMPLETE = 1;
	private final static int DOWNLOAD_FAIL = 2;
	private final static int DOWNLOAD_CANCEL = 3;

	private Handler mHandler;
	private UpdateInfo updateInfo = null;
	private SharedPreferences prefs = null;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		prefs = PreferenceManager
				.getDefaultSharedPreferences(DownloadService.this);
		if (null == intent || !intent.hasExtra(ConstDef.PREF_UPDATE_EXTRA_INFO)) {
			return super.onStartCommand(intent, flags, startId);
		}
		updateInfo = intent.getParcelableExtra(ConstDef.PREF_UPDATE_EXTRA_INFO);
		if (updateInfo == null) {
			return super.onStartCommand(intent, flags, startId);
		}
		downNewFile(updateInfo.getDownloadUrl(), Utils.getDownloadDir(this),
				updateInfo.getFileName());
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case DOWNLOADING:
					break;
				case DOWNLOAD_COMPLETE:
					File file = (File) msg.obj;
					if (MD5.checkMD5(updateInfo.getMd5Sum(), file)) {
						prefs.edit()
								.putString(ConstDef.PREF_INSTALL_FILE,
										file.getAbsolutePath()).apply();
						Utils.installAPP(file.getPath());
					} else {
						// 文件校验失败
						deleteFile((File) msg.obj);
					}
					break;
				case DOWNLOAD_FAIL:
					break;
				case DOWNLOAD_CANCEL:
					break;
				}
			}
		};
	}

	private void downNewFile(final String url, final String path,
			final String fileName) {
		if (url == null || path == null || fileName == null) {
			return;
		}
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				BufferedInputStream bis = null;
				FileOutputStream fos = null;
				BufferedOutputStream bos = null;

				File tempFile = null;
				try {
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet(url);
					HttpResponse response = client.execute(get);

					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity entity = response.getEntity();
						InputStream is = entity.getContent();
						if (is != null) {
							tempFile = new File(path, fileName);
							if (tempFile.exists())
								tempFile.delete();
							tempFile.createNewFile();

							bis = new BufferedInputStream(is);
							fos = new FileOutputStream(tempFile);
							bos = new BufferedOutputStream(fos);

							int read;
							byte[] buffer = new byte[2048];
							while ((read = bis.read(buffer)) != -1) {
								bos.write(buffer, 0, read);
							}
							bos.flush();
							fos.flush();

							Message message = mHandler.obtainMessage(
									DOWNLOAD_COMPLETE, tempFile);
							mHandler.sendMessage(message);
						}
					} else {
						throw new Exception("Download happen error");
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (tempFile != null && tempFile.exists())
						tempFile.delete();
					mHandler.sendEmptyMessage(DOWNLOAD_FAIL);
				} finally {
					try {
						bos.close();
						fos.close();
						bis.close();
					} catch (Exception e2) {
						bos = null;
						fos = null;
						bis = null;
					}
				}
			}
		};

		new Thread(runnable).start();
	}

	private void deleteFile(File file) {
		file.delete();
	}

}
