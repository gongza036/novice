package com.gongza.novice.volleydemo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.gongza.novice.ApplicationNovice;
import com.gongza.novice.R;
import com.gongza.novice.volleydemo.volleyrequest.GsonRequest;
import com.gongza.novice.volleydemo.volleyrequest.JsonObjectPostRequest;
import com.gongza.novice.volleydemo.volleyrequest.Weather;
import com.gongza.novice.volleydemo.volleyrequest.WeatherInfo;
import com.gongza.novice.volleydemo.volleyrequest.XMLRequest;

/**
 * volley数据请求的例子
 * 
 * @author gongza
 *
 */
public class TestVolleyAct extends Activity {
	private TextView tv_volley;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volley);
		initView();
		// volley_Get();
		// volley_Post();
		XMLGet();
	}
	
	private void initView() {
		tv_volley=(TextView) findViewById(R.id.tv_volley);

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		ApplicationNovice.getHttpQueue().cancelAll("abcPost");
		ApplicationNovice.getHttpQueue().cancelAll("abcGet");
	}

	/**
	 * 普通post请求方法
	 */
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
		ApplicationNovice.getHttpQueue().add(request);
	}

	/**
	 * 普通get请求方法
	 */
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
		ApplicationNovice.getHttpQueue().add(request);
	}

	/**
	 * 自定义的XML请求方法 http://flash.weather.com.cn/wmaps/xml/china.xml
	 * 这个接口会将中国所有的省份天气数据以XML格式进行返回
	 */
	private void XMLGet() {
		String url = "http://flash.weather.com.cn/wmaps/xml/china.xml";
		XMLRequest mXMLRequest = new XMLRequest(url,
				new Response.Listener<XmlPullParser>() {
					@Override
					public void onResponse(XmlPullParser response) {
						try {
							int eventType = response.getEventType();
							while (eventType != XmlPullParser.END_DOCUMENT) {
								switch (eventType) {
								case XmlPullParser.START_TAG:
									String nodeName = response.getName();
									if ("city".equals(nodeName)) {
										String pName = response
												.getAttributeValue(0);
										Log.d("TAG", "pName is " + pName);
										tv_volley.setText(pName);
									}
									break;
								}
								eventType = response.next();
							}
						} catch (XmlPullParserException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});

		ApplicationNovice.getHttpQueue().add(mXMLRequest);
	}

	/**
	 * 自定义Gson形式的请求
	 * http://www.weather.com.cn/data/sk/101010100.html这个接口可以得到一段JSON格式的天气数据
	 * 返回如下
	 * {"weatherinfo":{"city":"北京","cityid":"101010100","temp":"19","WD":"南风",
	 * "WS":"2级","SD":"43%","WSE":"2","time":"19:45","isRadar":"1","Radar":
	 * "JC_RADAR_AZ9010_JB"}}
	 */
	private void GsonGet() {
		GsonRequest<Weather> gsonRequest = new GsonRequest<Weather>(
				"http://www.weather.com.cn/data/sk/101010100.html",
				Weather.class, new Response.Listener<Weather>() {
					@Override
					public void onResponse(Weather weather) {
						WeatherInfo weatherInfo = weather.getWeatherinfo();
						Log.d("TAG", "city is " + weatherInfo.getCity());
						Log.d("TAG", "temp is " + weatherInfo.getTemp());
						Log.d("TAG", "time is " + weatherInfo.getTime());
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});
		ApplicationNovice.getHttpQueue().add(gsonRequest);

	}

	/**
	 * volley自带的普通请求Json
	 */
	private void JsonGet() {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				"http://m.weather.com.cn/data/101010100.html", null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});

	}

	/**
	 * 带保存cookies的
	 * 
	 * @param userName
	 * @param userPassword
	 */
	private void cookies(String userName, String userPassword) {
		// 生成MD5
		// userPassword = UserUtil.toLowerCaseMD5(userPassword);
		// 转成成UTF-8
		try {
			userName = URLEncoder.encode(userName, "UTF-8");
			userPassword = URLEncoder.encode(userPassword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HashMap<String, String> mMap = new HashMap<String, String>();
		mMap.put("user_name", userName);
		mMap.put("password", userPassword);
		String url = "";
		// 发起请求
		JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(
				url, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jsonObject) {
						// 从服务器响应response中的jsonObject中取出cookie的值，存到本地sharePreference
						try {
							// shareUtil.setLocalCookie(jsonObject.getString("Cookie"));
							// shareUtil.apply();
							jsonObject.getString("Cookie");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						try {
							if (jsonObject.get("status").equals("success")) {
								// 登录成功
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(TestVolleyAct.this, "网络错误，登录失败！",
								Toast.LENGTH_SHORT).show();
					}
				}, mMap);
		// 取本地保存的cookie
		// String localCookieStr = shareUtil.getLocalCookie();
		String localCookieStr = "";
		if (!localCookieStr.equals("")) {
			jsonObjectPostRequest.setSendCookie(localCookieStr);// 向服务器发起post请求时加上cookie字段
		}
		ApplicationNovice.getHttpQueue().add(jsonObjectPostRequest);
	}

}
