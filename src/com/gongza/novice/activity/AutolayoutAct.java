package com.gongza.novice.activity;

import com.gongza.autolayout.AutoLayoutActivity;
import com.gongza.novice.R;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * 自动适配方案
 * 出自Hongyang
 * https://github.com/hongyangAndroid/AndroidAutoLayout
 * http://blog.csdn.net/lmj623565791/article/details/49990941
 * @author gongza
 * 删除了部分需要依赖v7的代码
 */
public class AutolayoutAct extends AutoLayoutActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setImmersionStatus();
		setContentView(R.layout.activity_autolayout);

	}

	private void setImmersionStatus() {
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}
	
}
