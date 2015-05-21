package com.ustc.deliverybox.activity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ustc.deliverybox.service.CheckUpdateService;
import com.ustc.deliverybox.service.DownloadService;
import com.ustc.deliverybox.service.MainActivity;
import com.ustc.deliverybox.service.R;
import com.ustc.deliverybox.service.RequestService;
import com.ustc.deliverybox.util.Actions;
import com.ustc.deliverybox.util.ConstDef;
import com.ustc.deliverybox.util.HardwareUtil;
import com.ustc.deliverybox.util.UpdateInfo;
import com.ustc.deliverybox.util.Utils;
import com.ustc.deliverybox.view.CustomDialog;

public class OpenBoxActivity extends Activity implements CustomDialog.OnDismissListener {

	private final int DISMISS_DIALOG = 0x1;

    private int wight = 0;
    private int height = 0;

    private EditText evPhoneNUmber = null;
    private EditText evVerificationCode = null;
    private TextView tvPleaseEnterPhoneNumber = null;
    private TextView tvPleaseEnterVerifyCode = null;
    private TextView tvPhoneNumber = null;
    private TextView tvVerificationCode = null;
    private String errorCode = null;

    CustomDialog dialog = null;
    private Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_box);
        getScreenSize();
        initViews();
        registerReceiver();
        scheduleUpdateService(this, ConstDef.UPDATE_FREQ);
        handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == DISMISS_DIALOG) {
					if (null != dialog) {
						dialog.dismiss();
					}
				}
			}

        };
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    private void initViews() {
    	tvPhoneNumber = (TextView)findViewById(R.id.tv_phone_number);
    	tvVerificationCode = (TextView)findViewById(R.id.tv_verification_code);
        tvPleaseEnterPhoneNumber = (TextView)findViewById(R.id.ev_please_enter_phone_number);
        tvPleaseEnterVerifyCode = (TextView)findViewById(R.id.ev_please_enter_verify_code);
        LinearLayout ivLogo = (LinearLayout)findViewById(R.id.iv_logo);
        
        ivLogo.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Intent intent = new Intent(OpenBoxActivity.this, MainActivity.class);
				OpenBoxActivity.this.startActivity(intent);
				return false;
			}
		});
        
        System.out.println("height "+ivLogo.getHeight());
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                height*714/(312*5),
                height/5);
        ivLogo.setLayoutParams(params1);

        evPhoneNUmber = (EditText)findViewById(R.id.ev_phone_number);
        evVerificationCode = (EditText)findViewById(R.id.ev_verification_code);
        evPhoneNUmber.setInputType(EditorInfo.TYPE_NULL);
        evVerificationCode.setInputType(EditorInfo.TYPE_NULL);
        evPhoneNUmber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	 if (Utils.isPhoneNUmber(s.toString())) {
                     evPhoneNUmber.setTextColor(getResources().getColor(R.color.green));
                     evVerificationCode.requestFocus();
                 } else {
                     evPhoneNUmber.setTextColor(getResources().getColor(R.color.black));
                 }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        evPhoneNUmber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String text = evPhoneNUmber.getText().toString();
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        if (text.length() == 0) {
                            showPhoneTip(R.string.please_enter_phone_number);
                            return true;
                        } else if (!Utils.isPhoneNUmber(text)) {
                            showPhoneTip(R.string.please_enter_right_phone_number);
                            return true;
                        }
                    }
                    dismissPhoneTip();
                    return false;
                }
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DEL:
                        if (text.length() == 0) {
                            //return true;
                        } else {
                            dismissPhoneTip();
                        }
                        break;
                    case KeyEvent.KEYCODE_ENTER:
                        if (text.length() == 0) {
                            showPhoneTip(R.string.please_enter_phone_number);
                            return true;
                        } else if (text.length() != 11) {
                            showPhoneTip(R.string.please_enter_right_phone_number);
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                dismissPhoneTip();
                return false;
            }
        });
        evVerificationCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String text = evVerificationCode.getText().toString();
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        if (text.length() == 0) {
                            showVerifyTip(R.string.please_enter_verify_code);
                            return true;
                        } else if (text.length() != 4) {
                            showVerifyTip(R.string.please_enter_right_verify_code);
                            return true;
                        }
                    }
                    dismissVerifyTip();
                    return false;
                }
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DEL:
                        if (text.length() == 0) {
                            evPhoneNUmber.requestFocus();
                            return true;
                        }
                        dismissVerifyTip();
                        return false;
                    case KeyEvent.KEYCODE_ENTER:
                        if (text.length() == 0) {
                            showVerifyTip(R.string.please_enter_verify_code);
                            return true;
                        } else if (text.length() != 4) {
                            showVerifyTip(R.string.please_enter_right_verify_code);
                            return true;
                        } else {
                            sendReuqst();
                        }
                        break;
                    default:
                        break;
                }
                dismissVerifyTip();
                if(text.length()  == 4) {
                	return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDismiss() {
        if ("000000".equals(errorCode)) {
            resetAll();
        }  else if ("000022".equals(errorCode)){
            resetPhoneNumber();
        } else if ("000002".equals(errorCode)) {
            resetVerifyCode();
        }
        handler.removeMessages(DISMISS_DIALOG);
    }

    private void resetAll() {
        evVerificationCode.setText("");
        evPhoneNUmber.setText("");
        evPhoneNUmber.requestFocus();
    }

    private void resetPhoneNumber() {
        evVerificationCode.setText("");
        evPhoneNUmber.requestFocus();
    }

    private void resetVerifyCode() {
        evVerificationCode.setText("");
        evVerificationCode.requestFocus();
    }

    private void showPhoneTip(int strId) {
        tvPleaseEnterPhoneNumber.setVisibility(View.VISIBLE);
        tvPleaseEnterPhoneNumber.setText(strId);
    }

    private void showVerifyTip(int strId) {
        tvPleaseEnterVerifyCode.setVisibility(View.VISIBLE);
        tvPleaseEnterVerifyCode.setText(strId);
    }

    private void dismissPhoneTip() {
        tvPleaseEnterPhoneNumber.setVisibility(View.GONE);
    }

    private void dismissVerifyTip() {
        tvPleaseEnterVerifyCode.setVisibility(View.GONE);
    }

    private void getScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        wight = dm.widthPixels;
        height = dm.heightPixels;
    }

    private void sendReuqst() {
        sendRequese(this, Actions.ACTION_OPEN_BOX, buildPutRequest());
    }

    private  void sendRequese(Context context,String acion,JSONObject json) {
        if (json == null) {
            return;
        }
        Intent vcodeIntent = new Intent(context, RequestService.class);
        vcodeIntent.setAction(acion);
        vcodeIntent.putExtra(RequestService.ACTION_JSON,
                json.toString());
        context.startService(vcodeIntent);
    }

    private JSONObject buildPutRequest() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("cabinetId", HardwareUtil.getDeviceID(this));
        params.put("tel",evPhoneNUmber.getText().toString());
        params.put("validatecode",evVerificationCode.getText().toString());
        return Utils.buildRequest(this, "open_box", params);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Actions.ACTION_OPEN_BOX.equals(action)) {
                String json = intent.getExtras().getString(
                        RequestService.EXTRA_JSON);
                JSONObject result = null;
                JSONObject res = null;
                try {
                    result = new JSONObject(json);
                    res = result.getJSONObject("res");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String status = Utils.getJsonStringParam(result, "status");
                String desc = Utils.getJsonStringParam(result, "desc");
                errorCode = Utils.getJsonStringParam(result, "errorCode");
                if ("000000".equals(errorCode)) {
                    // open box success
                    showDialog(R.string.congratulation,R.string.success_msg);
                    // reset exit box
                    resetAll();
                }  else if ("000022".equals(errorCode)){
                    // no package for this user
                    showDialog(R.string.sorry,R.string.no_your_package);
                } else if ("000002".equals(errorCode)) {
                    // verify code not right
                    showDialog(R.string.tip,R.string.verify_code_error);
                }
            } else if (Actions.ACTION_NEW_VERSION.equals(action)) {
                Intent i = new Intent(OpenBoxActivity.this, DownloadService.class);
                UpdateInfo info = intent.getParcelableExtra(ConstDef.PREF_UPDATE_EXTRA_INFO);
                info.getDownloadUrl();
                i.putExtra(ConstDef.PREF_UPDATE_EXTRA_INFO,info);
                startService(i);
            } else if (Actions.ACTION_ON_LINE.equals(action)) {
            	onLine();
            } else if(Actions.ACTION_OFF_LINE.equals(action)) {
            	offLine();
            }
        }
    };

    private void onLine() {
    	tvPhoneNumber.setTextColor(getResources().getColor(R.color.black));
    	tvVerificationCode.setTextColor(getResources().getColor(R.color.black));
    }

    private void offLine() {
    	tvPhoneNumber.setTextColor(getResources().getColor(R.color.bg_list_divider));
    	tvVerificationCode.setTextColor(getResources().getColor(R.color.bg_list_divider));
    }

    private void showDialog(int title,int msg) {
    	if (dialog == null) {
    		dialog = new CustomDialog(this);
		}
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setDismissListener(this);
        dialog.show();
        handler.removeMessages(DISMISS_DIALOG);
        handler.sendEmptyMessageDelayed(DISMISS_DIALOG, 5000);
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(
                Actions.ACTION_OPEN_BOX);
        filter.addAction(Actions.ACTION_NEW_VERSION);
        filter.addAction(Actions.ACTION_ON_LINE);
        filter.addAction(Actions.ACTION_OFF_LINE);
        registerReceiver(mReceiver, filter);
    }

    private void unregisterReceiver() {
        unregisterReceiver(mReceiver);
    }

    public void scheduleUpdateService(Context context, int updateFrequency) {
        // Get the intent ready
        Intent i = new Intent(context, CheckUpdateService.class);
        i.setAction(Actions.ACTION_CHECK_UPDATE);
        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        // Clear any old alarms and schedule the new alarm
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
        am.set(AlarmManager.RTC_WAKEUP, getCheckUpdateTime(), pi);
    }

    private long getCheckUpdateTime() {
    	int i = 0;
    	Calendar now=Calendar.getInstance();
    	Calendar calendar=Calendar.getInstance();
    	for(i = 0;i<ConstDef.CHECK_UPDATE_POINTS.length;i++) {
            calendar = generateCalendarOfThisDayFromHour(ConstDef.CHECK_UPDATE_POINTS[i]);
            if (now.getTimeInMillis() < calendar.getTimeInMillis()) {
				return calendar.getTimeInMillis();
			}
    	}
    	calendar = generateCalendarOfThisDayFromHour(ConstDef.CHECK_UPDATE_POINTS[0]);
    	calendar.add(Calendar.DAY_OF_YEAR, 1);
    	return calendar.getTimeInMillis();
    }

    private Calendar generateCalendarOfThisDayFromHour(int hour) {
    	Calendar calendar=Calendar.getInstance();
    	calendar.set(Calendar.HOUR_OF_DAY,hour);
        //set the hour of the calendar to the value that we want
        calendar.set(Calendar.MINUTE, 0);
        //set the minute of the calendar to 0
        calendar.set(Calendar.SECOND, 0);
        //set the minute of the calendar to 0
        calendar.set(Calendar.MILLISECOND,0);
        return calendar;
    }

}
