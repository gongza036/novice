package com.gongza.novice.volleydemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gongza.novice.ApplicationNovice;
import com.gongza.novice.R;
import com.gongza.novice.adapter.Bookends;
import com.gongza.novice.adapter.VolleyRLAdapter;
import com.gongza.novice.bean.HehuaResultBean;
import com.gongza.novice.bean.group.GeekGroupBeanN;
import com.gongza.novice.bean.group.GroupRecommIndexBeanN;
import com.gongza.novice.bean.parser.GroupRecommParser;
import com.gongza.views.cube.ptr.PtrDefaultHandler;
import com.gongza.views.cube.ptr.PtrFrameLayout;
import com.gongza.views.cube.ptr.PtrGongzFrameLayout;
import com.gongza.views.cube.ptr.PtrHandler;

public class VolleyRecycelrViewAct extends Activity {
	private PtrGongzFrameLayout mPtrFrame;
	private RecyclerView rl_volley;
	private VolleyRLAdapter adapter;
	private Bookends<VolleyRLAdapter> mBookends;
//	private List<String> datas;
	private ArrayList<GeekGroupBeanN> datas=new ArrayList<GeekGroupBeanN>();;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volleyrecyclerview);
		initPullView();
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
						mBookends.notifyDataSetChanged();//这里加了头尾的适配器mBookends后是刷新它
						//adapter.notifyDataSetChanged();
						updateComplete();
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
		
		//第二种种增加头部的方法    
		mBookends=new Bookends<VolleyRLAdapter>(adapter);
		View headerView=LayoutInflater.from(VolleyRecycelrViewAct.this).inflate(R.layout.tab2_rl_header, mPtrFrame, false);
		View footerView=LayoutInflater.from(VolleyRecycelrViewAct.this).inflate(R.layout.tab2_rl_footer, mPtrFrame, false);
		mBookends.addHeader(headerView);
		mBookends.addFooter(footerView);
		rl_volley.setAdapter(mBookends);
		
		// 设置布局管理器
		LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(
				VolleyRecycelrViewAct.this, LinearLayoutManager.VERTICAL, false);
		rl_volley.setLayoutManager(mLinearLayoutManager);
		rl_volley.setItemAnimator(new DefaultItemAnimator());
	}
	
	private void initPullView() {
		mPtrFrame = (PtrGongzFrameLayout) findViewById(R.id.rotate_header_grid_view_frame);
		mPtrFrame.setLastUpdateTimeRelateObject(this);
		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
//				 updateData();
				initData();
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
						content, header);
			}
		});
		// the following are default settings
		mPtrFrame.setResistance(2.3f);
		mPtrFrame.setRatioOfHeaderHeightToRefresh(1.0f);
		mPtrFrame.setDurationToClose(200);
		mPtrFrame.setDurationToCloseHeader(800);
		// default is false
		mPtrFrame.setPullToRefresh(false);
		// default is true
		mPtrFrame.setKeepHeaderWhenRefresh(true);
		mPtrFrame.postDelayed(new Runnable() {
			@Override
			public void run() {
				// mPtrFrame.autoRefresh();
			}
		}, 100);
		// updateData();
		setupViews(mPtrFrame);
	}

	protected void setupViews(final PtrGongzFrameLayout ptrFrame) {
		ptrFrame.setLoadingMinTime(1000);
		// setHeaderTitle(R.string.ptr_demo_block_auto_fresh);
		ptrFrame.postDelayed(new Runnable() {
			@Override
			public void run() {
				ptrFrame.autoRefresh(true);
			}
		}, 500);
	}
	
	protected void updateComplete() {
		mPtrFrame.refreshComplete();
    }
	
}
