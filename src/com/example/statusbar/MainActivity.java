package com.example.statusbar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	LinearLayout mStatusIcons;
	mPSListener phonelisten;
	TelephonyManager tman;

	LinearLayout.LayoutParams iconParam = new LinearLayout.LayoutParams(
			18, LinearLayout.LayoutParams.FILL_PARENT);

	TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mStatusIcons = (LinearLayout)findViewById(R.id.statusIcon);

		init(this);
	}

	private void init(Context context){
		mTextView = new TextView(context);
		mTextView.setLayoutParams(iconParam);

		//mobile data
		phonelisten = new mPSListener();
		tman = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		tman.listen(phonelisten, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
	}

	private void removeIcon(LinearLayout view, String tag){
		int childCount = view.getChildCount();
		View child;
		for (int i = 0; i <= childCount - 1; i++) {
			child = view.getChildAt(i);
			if(child.getTag() == tag){
				view.removeView(child);
			}
		}
	}

	private boolean getIcon(LinearLayout view, String tag){
		int childCount = view.getChildCount();
		View child;
		for (int i = 0; i <= childCount - 1; i++) {
			child = view.getChildAt(i);
			if(child.getTag() == tag){
				return true;
			}
		}
		return false;
	}

	private void addIcon(LinearLayout root, TextView icon, String tag, String text){
		icon.setTag(tag);
		icon.setText(text);
		root.addView(icon);
	}
	private class mPSListener extends PhoneStateListener {

		@Override
		public void onDataConnectionStateChanged(int arg0) {
			super.onDataConnectionStateChanged(arg0);
			Log.i("statusbar", "PSListener");
			if(arg0 == 0 || getIcon(mStatusIcons, "data")){
				removeIcon(mStatusIcons, "data");
			}else{
				addIcon(mStatusIcons, mTextView, "data", "data");
			}
		}

	}
}
