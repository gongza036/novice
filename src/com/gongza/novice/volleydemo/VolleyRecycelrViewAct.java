package com.gongza.novice.volleydemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gongza.novice.ApplicationNovice;
import com.gongza.novice.R;
import com.gongza.novice.adapter.VolleyRLAdapter;
import com.gongza.novice.bean.HehuaResultBean;
import com.gongza.novice.bean.group.GeekGroupBeanN;
import com.gongza.novice.bean.group.GroupRecommIndexBeanN;
import com.gongza.novice.bean.parser.GroupRecommParser;

public class VolleyRecycelrViewAct extends Activity {
	private RecyclerView rl_volley;
	private VolleyRLAdapter adapter;
//	private List<String> datas;
	private ArrayList<GeekGroupBeanN> datas=new ArrayList<GeekGroupBeanN>();;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volleyrecyclerview);
		initData();
		initView();
	}

	private void initData() {
//		datas = new ArrayList<String>();
//		for (int i = 'A'; i <= 'z'; i++) {
//			datas.add("" + (char) i);
//		}

		String url = "http://hehua.lmbang.com/api-group-recomm/index";
		StringRequest request = new StringRequest(Method.GET, url,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
//						Toast.makeText(VolleyRecycelrViewAct.this, arg0,
//								Toast.LENGTH_LONG).show();
						//解析
						GroupRecommParser patser = new GroupRecommParser();
						HehuaResultBean<GroupRecommIndexBeanN> dataBean = patser
								.getGroupRecomm(arg0);
						GroupRecommIndexBeanN listData = dataBean.getDataBean();
						final ArrayList<GeekGroupBeanN> tList = listData.getList();
						datas.addAll(tList);
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(VolleyRecycelrViewAct.this, "网络请求失败",
								Toast.LENGTH_LONG).show();
					}
				});
		request.setTag("推荐列表");
		ApplicationNovice.getHttpQueue().add(request);

	}

	private void initView() {
		rl_volley = (RecyclerView) findViewById(R.id.rl_volley);
		adapter = new VolleyRLAdapter(VolleyRecycelrViewAct.this, datas);
		rl_volley.setAdapter(adapter);
		// 设置布局管理器
		LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(
				VolleyRecycelrViewAct.this, LinearLayoutManager.VERTICAL, false);
		rl_volley.setLayoutManager(mLinearLayoutManager);
		rl_volley.setItemAnimator(new DefaultItemAnimator());
	}

}
