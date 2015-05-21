package com.ustc.deliverybox.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by tangnan on 15-3-10.
 */
public class Utils {

    public static JSONObject buildRequest(Context context,String cmd,Map<String, String> params) {
        JSONObject request = new JSONObject(params);
        return request;
    }

    public static String getJsonStringParam(JSONObject json,String key) {
        try {
            return json.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void toast(Context context,String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void toast(Context context,int id) {
        Toast.makeText(context, id, Toast.LENGTH_LONG).show();
    }

    public static String installAPP(String apkPath) {
        String cmd1 = "chmod 777 " + apkPath + " \n"; 
        String cmd2 = "pm install -r "+ apkPath+" \n";
        String cmd3 = "reboot\n";
        execWithSID(cmd1,cmd2,cmd3);
        return "";
    }

    public static String reboot() {
        String cmd = "reboot\n";
        execWithSID(cmd);
        return "";
    }

	private static boolean execWithSID(String... args) {
		boolean isSuccess = false;
		Process process = null;
		OutputStream out = null;
		try {
			process = Runtime.getRuntime().exec("su");
			out = process.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);
			for (String tmp : args) {
				dataOutputStream.writeBytes(tmp);
			}
			dataOutputStream.flush();
			dataOutputStream.close();
			out.close();
			isSuccess = waitForProcess(process);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

	private static boolean waitForProcess(Process p) {
		boolean isSuccess = false;
		int returnCode;
		try {
			returnCode = p.waitFor();
			switch (returnCode) {
			case 0:
				isSuccess = true;
				break;
			case 1:
				break;
			default:
				break;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

    public static String runCmd(String[] args) {
        {
            String result = "";
            ProcessBuilder processBuilder = new ProcessBuilder(args);
            Process process = null;
            InputStream errIs = null;
            InputStream inIs = null;
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int read = -1;
                process = processBuilder.start();
                errIs = process.getErrorStream();
                while ((read = errIs.read()) != -1) {
                    baos.write(read);
                }
                baos.write('\n');
                inIs = process.getInputStream();
                while ((read = inIs.read()) != -1) {
                    baos.write(read);
                }
                byte[] data = baos.toByteArray();
                result = new String(data);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (errIs != null) {
                        errIs.close();
                    }
                    if (inIs != null) {
                        inIs.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (process != null) {
                    process.destroy();
                }
            }
            return result;
        }
    }

    public static String getDownloadDir(Context context) {
        File directory = new File(context.getFilesDir().getPath());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory.getAbsolutePath();
    }

    public static boolean isPhoneNUmber(String value){
    	System.out.println("value");
        Pattern p = Pattern
                .compile("^([0\\+]\\d{2,3})?[1][3-8]+\\d{9}$");
        Matcher m = p.matcher(value);
        return m.matches();
    }

}
