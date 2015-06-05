package com.gongza.novice.activity;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gongza.novice.ApplicationX;
import com.gongza.novice.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class TestVolleyAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// volley_Get();
		volley_Post();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		ApplicationX.getHttpQueue().cancelAll("abcPost");
		ApplicationX.getHttpQueue().cancelAll("abcGet");
	}

	private void volley_Post() {
		String url = "http://apis.juhe.cn/mobile/get?";
		StringRequest request = new StringRequest(Method.POST, url,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Toast.makeText(TestVolleyAct.this, arg0,
								Toast.LENGTH_LONG).show();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(TestVolleyAct.this, "网络请求失败",
								Toast.LENGTH_LONG).show();
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("phone", "13666666666");
				map.put("key", "335adcc4e891ba4e4be6d7534fd54c5d");
				return map;
			}
		};
		request.setTag("abcPost");
		ApplicationX.getHttpQueue().add(request);
	}

	private void volley_Get() {
		String url = "http://apis.juhe.cn/mobile/get?phone=13666666666&key=335adcc4e891ba4e4be6d7534fd54c5d";
		StringRequest request = new StringRequest(Method.GET, url,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Toast.makeText(TestVolleyAct.this, arg0,
								Toast.LENGTH_LONG).show();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(TestVolleyAct.this, "网络请求失败",
								Toast.LENGTH_LONG).show();
					}
				});
		request.setTag("abcGet");
		ApplicationX.getHttpQueue().add(request);
	}
}
