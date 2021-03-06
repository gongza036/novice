package com.gongza.novice;


import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ApplicationNovice extends Application {
	public static RequestQueue queue;

	@Override
	public void onCreate() {
		super.onCreate();
		//初始化Volley
		queue = Volley.newRequestQueue(getApplicationContext());
	}

	/**
	 * 获得Volley请求队列  
	 * @return RequestQueue
	 */
	public static RequestQueue getHttpQueue() {
		return queue;
	}

}
