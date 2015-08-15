package com.gongza.novice.volleydemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.gongza.novice.adapter.VolleyRecycelerViewPagerAdapter;
import com.gongza.novice.bean.HehuaResultBean;
import com.gongza.novice.bean.group.BanerAdDataBean;
import com.gongza.novice.bean.group.BannerAdBean;
import com.gongza.novice.bean.group.BaseNetBean;
import com.gongza.novice.bean.group.GeekGroupBeanN;
import com.gongza.novice.bean.group.GroupRecommIndexBeanN;
import com.gongza.novice.bean.parser.GroupRecommParser;
import com.gongza.utils.L;
import com.gongza.views.cube.ptr.PtrDefaultHandler;
import com.gongza.views.cube.ptr.PtrFrameLayout;
import com.gongza.views.cube.ptr.PtrGongzFrameLayout;
import com.gongza.views.cube.ptr.PtrHandler;
import com.gongza.views.jazzyviewpager.JazzyViewPager;
import com.gongza.views.jazzyviewpager.JazzyViewPager.TransitionEffect;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class VolleyRecycelrViewAct extends Activity {
	private PtrGongzFrameLayout mPtrFrame;
	private RecyclerView rl_volley;
	private VolleyRLAdapter adapter;
	private Bookends<VolleyRLAdapter> mBookends;
	// private List<String> datas;
	private ArrayList<GeekGroupBeanN> datas = new ArrayList<GeekGroupBeanN>();

	private RelativeLayout viewpager_rl;
	private JazzyViewPager viewPager;
	private ArrayList<BannerAdBean> adLists = new ArrayList<BannerAdBean>();// 广告数据
	private VolleyRecycelerViewPagerAdapter bannerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volleyrecyclerview);
		initPullView();
		initView();
	}

	private void initView() {
		rl_volley = (RecyclerView) findViewById(R.id.rl_volley);
		adapter = new VolleyRLAdapter(VolleyRecycelrViewAct.this, datas);
		rl_volley.setAdapter(adapter);

		// 第二种种增加头部的方法
		mBookends = new Bookends<VolleyRLAdapter>(adapter);
		// View headerView = LayoutInflater.from(VolleyRecycelrViewAct.this)
		// .inflate(R.layout.tab2_rl_header, mPtrFrame, false);
		View headerView = LayoutInflater.from(VolleyRecycelrViewAct.this)
				.inflate(R.layout.hehua_mainlistview_head, mPtrFrame, false);
		viewPager = (JazzyViewPager) headerView.findViewById(R.id.view_pager);
		viewpager_rl = (RelativeLayout) headerView
				.findViewById(R.id.viewpager_rl);
		viewPager.setTransitionEffect(TransitionEffect.Accordion);

		View footerView = LayoutInflater.from(VolleyRecycelrViewAct.this)
				.inflate(R.layout.tab2_rl_footer, mPtrFrame, false);
		mBookends.addHeader(headerView);
		mBookends.addFooter(footerView);
		rl_volley.setAdapter(mBookends);

		// 设置布局管理器
		LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(
				VolleyRecycelrViewAct.this, LinearLayoutManager.VERTICAL, false);
		rl_volley.setLayoutManager(mLinearLayoutManager);
		rl_volley.setItemAnimator(new DefaultItemAnimator());
		//添加滑动监听
		rl_volley.addOnScrollListener(new RLOnScrollListener());
	}

	private void initPullView() {
		mPtrFrame = (PtrGongzFrameLayout) findViewById(R.id.rotate_header_grid_view_frame);
		mPtrFrame.setLastUpdateTimeRelateObject(this);
		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				// updateData();
				initData(true);
				bannerAdData();
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

	private void initData(final boolean clear) {
		// datas = new ArrayList<String>();
		// for (int i = 'A'; i <= 'z'; i++) {
		// datas.add("" + (char) i);
		// }

		String url = "http://hehua.lmbang.com/api-group-recomm/index";
		StringRequest request = new StringRequest(Method.GET, url,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						// 解析
						GroupRecommParser patser = new GroupRecommParser();
						HehuaResultBean<GroupRecommIndexBeanN> dataBean = patser
								.getGroupRecomm(arg0);
						GroupRecommIndexBeanN listData = dataBean.getDataBean();
						final ArrayList<GeekGroupBeanN> tList = listData
								.getList();
						
						if (tList!=null&&tList.size()>0) {
							if (clear) {
								datas.clear();
							}
							datas.addAll(tList);
							mBookends.notifyDataSetChanged();// 这里加了头尾的适配器mBookends后是刷新它
							// adapter.notifyDataSetChanged();
						}
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

	private void bannerAdData() {
		String url = "http://hehua.lmbang.com/api-banner-ad/index";
		StringRequest request = new StringRequest(Method.GET, url,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						// Gson解析
						Gson gson = new Gson();
						BaseNetBean<BanerAdDataBean> dataBean = gson.fromJson(
								arg0,
								new TypeToken<BaseNetBean<BanerAdDataBean>>() {
								}.getType());
						BanerAdDataBean listData = dataBean.getData();
						adLists = listData.getList();
						L.d("@@@@@@@" + adLists.size());

						bannerAdapter = new VolleyRecycelerViewPagerAdapter(
								VolleyRecycelrViewAct.this, viewPager, adLists);
						viewPager.setAdapter(bannerAdapter);
						// viewPager.setPageMargin(30);

						// bannerAdapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(VolleyRecycelrViewAct.this, "网络请求失败",
								Toast.LENGTH_LONG).show();
					}
				});
		request.setTag("banner广告");
		ApplicationNovice.getHttpQueue().add(request);

	}

	class RLOnScrollListener extends
			android.support.v7.widget.RecyclerView.OnScrollListener {
		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);
		}

		@Override
		public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
			// super.onScrollStateChanged(recyclerView, newState);
			// 当前RecyclerView显示出来的最后一个的item的position
			int lastPosition = -1;

			// 当前状态为停止滑动状态SCROLL_STATE_IDLE时
			if (newState == RecyclerView.SCROLL_STATE_IDLE) {
				RecyclerView.LayoutManager layoutManager = recyclerView
						.getLayoutManager();
				if (layoutManager instanceof GridLayoutManager) {
					// 通过LayoutManager找到当前显示的最后的item的position
					lastPosition = ((GridLayoutManager) layoutManager)
							.findLastVisibleItemPosition();
				} else if (layoutManager instanceof LinearLayoutManager) {
					lastPosition = ((LinearLayoutManager) layoutManager)
							.findLastVisibleItemPosition();
				} else if (layoutManager instanceof StaggeredGridLayoutManager) {
					// 因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
					// 得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
					int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager)
							.getSpanCount()];
					((StaggeredGridLayoutManager) layoutManager)
							.findLastVisibleItemPositions(lastPositions);
					lastPosition = findMax(lastPositions);
				}

				// 时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
				// 如果相等则说明已经滑动到最后了
				if (lastPosition == recyclerView.getLayoutManager()
						.getItemCount() - 1) {
					Toast.makeText(VolleyRecycelrViewAct.this, "滑动到底了",
							Toast.LENGTH_SHORT).show();
					initData(false);
				}

			}
		}

		// 找到数组中的最大值
		private int findMax(int[] lastPositions) {
			int max = lastPositions[0];
			for (int value : lastPositions) {
				if (value > max) {
					max = value;
				}
			}
			return max;
		}

		
		//item的显示动画
		private Handler animHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				View view = null;
				switch (msg.what) {
				case 400:
					// 获得第一个item
					view = rl_volley.getChildAt(0);
					break;
				case 500:
					// 获得最后一个item
					view = rl_volley.getChildAt(rl_volley.getChildCount() - 1);
					break;

				default:
					break;
				}

				if (null != view) {
					// 加载动画
					Animation animation = AnimationUtils.loadAnimation(
							VolleyRecycelrViewAct.this, R.anim.hehua_list_anim);
					view.startAnimation(animation);
				}
			}
		};

	}
}
