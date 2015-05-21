package com.ustc.deliverybox.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ustc.deliverybox.service.R;

public class CustomDialog extends Dialog {
	protected Context mContext;

	private ImageView vIcon;
	private TextView vTitle;
	private TextView vMessage;
	private Button vPositive;
	private Button vNegative;

	private View vHeaderContainer;
	private ViewGroup vContentContainer;
	private View vFooterContainer;

	private View vContent;

	private Drawable mIcon;
	private CharSequence mTitle;
	private CharSequence mMessage;
	private CharSequence mPositiveText;
	private CharSequence mNegativeText;
	private OnClickListener mPositiveListener;
	private OnClickListener mNegativeListener;
    private OnDismissListener dismissListener;

	public CustomDialog(Context context) {
		super(context, R.style.myDialogTheme);
		setCanceledOnTouchOutside(true);

		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_dialog);

		findViews();
		initViews();
	}

	protected void findViews() {
		vIcon = (ImageView) findViewById(R.id.icon);
		vTitle = (TextView) findViewById(R.id.title);
		vMessage = (TextView) findViewById(R.id.message);
		vPositive = (Button) findViewById(R.id.btn_positive);
		vNegative = (Button) findViewById(R.id.btn_negative);

		vHeaderContainer = findViewById(R.id.header);
		vContentContainer = (ViewGroup) findViewById(R.id.content);
		vFooterContainer = findViewById(R.id.footer);
	}

	protected void initViews() {
		vTitle.setText(mTitle);

		if (mMessage != null) {
			vMessage.setVisibility(View.VISIBLE);
			vMessage.setText(mMessage);
		} else {
			vMessage.setVisibility(View.GONE);
		}

		if (mIcon != null) {
			vIcon.setVisibility(View.VISIBLE);
			vIcon.setImageDrawable(mIcon);
		}

		if (mTitle == null && mIcon == null) {
			vHeaderContainer.setVisibility(View.GONE);
		} else {
			vHeaderContainer.setVisibility(View.VISIBLE);
		}

		if (vContent != null) {
			vContentContainer.removeView(vContent);
			vContentContainer.addView(vContent);
		}

		if (mPositiveText != null) {
			vPositive.setText(mPositiveText);
		}
		if (mNegativeText != null) {
			vNegative.setText(mNegativeText);
		}

		if (mPositiveListener == null && mNegativeListener == null) {
			vFooterContainer.setVisibility(View.GONE);
		} else {
			vFooterContainer.setVisibility(View.VISIBLE);

			vPositive.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mPositiveListener != null) {
						mPositiveListener.onClick(CustomDialog.this,
								BUTTON_POSITIVE);
                        dismiss();
					}
				}
			});
			vNegative.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mNegativeListener != null) {
						mNegativeListener.onClick(CustomDialog.this,
								BUTTON_NEGATIVE);
                        dismiss();
					} else {
						dismiss();
					}
				}
			});
		}
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (dismissListener != null) {
            dismissListener.onDismiss();
        }
        return true;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        dismiss();
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        dismiss();
        return true;
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        dismiss();
        return true;
    }

	public void setIcon(int iconId) {
		mIcon = mContext.getResources().getDrawable(iconId);
	}

	public void setIcon(Bitmap icon) {
		mIcon = new BitmapDrawable(icon);
	}

	public void setIcon(BitmapDrawable icon) {
		mIcon = icon;
	}

	public void setTitle(int titleId) {
		mTitle = mContext.getText(titleId);
	}

	public void setTitle(CharSequence title) {
		mTitle = title;
	}

	public void setMessage(int msgId) {
		mMessage = mContext.getText(msgId);
	}

	public void setMessage(CharSequence msg) {
		mMessage = msg;
	}

	public void setView(View view) {
		vContent = view;
	}

	public void setView(int resId) {
		vContent = View.inflate(mContext, resId, null);
	}

    public void setDismissListener(OnDismissListener listener) {
        this.dismissListener = listener;
    }

	public void setPositiveButton(int textId, final OnClickListener listener) {
		mPositiveText = mContext.getText(textId);
		mPositiveListener = listener;
	}

	public void setPositiveButton(CharSequence text,
			final OnClickListener listener) {
		mPositiveText = text;
		mPositiveListener = listener;
	}

	public void setNegativeButton(int textId, final OnClickListener listener) {
		mNegativeText = mContext.getText(textId);
		mNegativeListener = listener;
	}

	public void setNegativeButton(CharSequence text,
			final OnClickListener listener) {
		mNegativeText = text;
		mNegativeListener = listener;
	}

	@Override
	public View findViewById(int id) {
		View view = null;

		view = super.findViewById(id);

		if (view == null && vContent != null) {
			view = vContent.findViewById(id);
		}

		return view;
	}

    public interface OnDismissListener{
        void onDismiss();
    }

}
