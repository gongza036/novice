package com.gongza.novice.fragment;

import android.animation.Animator;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.gongza.novice.R;
import com.gongza.novice.activity.ActivityJazzy;
import com.gongza.novice.activity.ActivityWaterfallList;
import com.gongza.novice.activity.CameraAct;
import com.gongza.novice.activity.MovingImageViewAct;
import com.gongza.novice.activity.ProgressWheelAct;
import com.gongza.novice.activity.PullScrollViewAct;
import com.gongza.novice.adapter.PtrGongzAdapter;
import com.gongza.novice.ptrdemo.PtrMainActivity;
import com.gongza.novice.volleydemo.TestVolleyAct;
import com.gongza.novice.volleydemo.VolleyRecycelrViewAct;
import com.gongza.views.listviewtools.HehuaPullToRefreshView;
import com.gongza.views.listviewtools.HehuaPullToRefreshView.OnFooterRefreshListener;
import com.gongza.views.listviewtools.HehuaPullToRefreshView.OnHeaderRefreshListener;
import com.gongza.views.movingimageview.MovingImageView;

/**
 * 
 * @author gongza
 *
 */
public class Tab03Fragment extends Fragment {
	private HehuaPullToRefreshView layout_refresh;
	private ListView lv_tab3;
	private PtrGongzAdapter adapter;
	MovingImageView image;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_3, container, false);
		initView(view);

		return view;
	}

	private void initView(View view) {
		lv_tab3 = (ListView) view.findViewById(R.id.lv_tab3);
		adapter = new PtrGongzAdapter(getActivity());
		lv_tab3.setAdapter(adapter);
		layout_refresh = (HehuaPullToRefreshView) view
				.findViewById(R.id.layout_refresh);
		lv_tab3.setOnScrollListener(new GzOnScrollListener());
		layout_refresh
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(HehuaPullToRefreshView view) {
						new CountDownTimer(2000, 1000) {

							@Override
							public void onTick(long millisUntilFinished) {

							}

							@Override
							public void onFinish() {
								layout_refresh.onHeaderRefreshComplete();
							}
						}.start();

					}
				});
		layout_refresh
				.setOnFooterRefreshListener(new OnFooterRefreshListener() {

					@Override
					public void onFooterRefresh(HehuaPullToRefreshView view) {
						new CountDownTimer(2000, 1000) {

							@Override
							public void onTick(long millisUntilFinished) {

							}

							@Override
							public void onFinish() {
								layout_refresh.onFooterRefreshComplete();

							}
						}.start();

					}
				});
		lv_tab3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:
					startActivity(new Intent(getActivity(),
							MovingImageViewAct.class));
					break;
				case 1:
					startActivity(new Intent(getActivity(),
							ProgressWheelAct.class));
					break;
				case 2:
					startActivity(new Intent(getActivity(), ActivityJazzy.class));
					break;
				case 3:
					startActivity(new Intent(getActivity(),
							ActivityWaterfallList.class));
					break;
				case 4:
					startActivity(new Intent(getActivity(),
							PtrMainActivity.class));
					break;
				case 5:
					startActivity(new Intent(getActivity(), TestVolleyAct.class));
					break;
				case 6:
					startActivity(new Intent(getActivity(),
							VolleyRecycelrViewAct.class));
					break;
				case 7:
					startActivity(new Intent(getActivity(),
							PullScrollViewAct.class));
					break;
				case 8:
					startActivity(new Intent(getActivity(), CameraAct.class));
					break;

				default:
					break;
				}
			}

		});

		image = (MovingImageView) view.findViewById(R.id.image);
		image.getMovingAnimator().addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
				Log.i("Sample MovingImageView", "Start");
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				Log.i("Sample MovingImageView", "End");
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				Log.i("Sample MovingImageView", "Cancel");
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				Log.i("Sample MovingImageView", "Repeat");
			}
		});
	}

	private int startfirstItemIndex;
	private int startlastItemIndex;
	private int endfirstItemIndex;
	private int endlastItemIndex;
	private Boolean isContentRefreshing = false;// 刷新用的开关

	// listview 滚动监听 上拉加载用的
	class GzOnScrollListener implements
			android.widget.AbsListView.OnScrollListener {

		@Override
		public void onScroll(AbsListView arg0, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if (firstVisibleItem > 3) {
			} else {
			}

			startfirstItemIndex = firstVisibleItem;
			startlastItemIndex = firstVisibleItem + visibleItemCount - 1;
			// 判断向下或者向上滑动了
			if ((endfirstItemIndex > startfirstItemIndex)
					&& (endfirstItemIndex > 0)) {
				// 向上
				animHandler.sendEmptyMessage(400);
			} else if ((endlastItemIndex < startlastItemIndex)
					&& (endlastItemIndex > 0)) {
				// 向下
				animHandler.sendEmptyMessage(500);

			}
			endfirstItemIndex = startfirstItemIndex;
			endlastItemIndex = startlastItemIndex;
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_IDLE: // Idle态，进行实际数据的加载显示
				break;
			case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 快速滚动
				break;
			case OnScrollListener.SCROLL_STATE_FLING:// 开始滚动
				break;
			default:
				break;
			}
			int first = view.getFirstVisiblePosition();
			int count = view.getChildCount();
			if (adapter != null
					&& (scrollState == SCROLL_STATE_IDLE
							|| (first + count > adapter.getCount()) || (first == 0))) {
				int firstVisiblePosition = view.getFirstVisiblePosition();
				int lastVisiblePosition = view.getLastVisiblePosition();

			}

			if (adapter != null
					&& (view.getLastVisiblePosition() == view.getCount() - 1)
					&& !isContentRefreshing) {
				// Toast.makeText(getActivity(), "到底部了", Toast.LENGTH_SHORT)
				// .show();
				isContentRefreshing = true;
			}
		}
	}

	// item的显示动画
	private Handler animHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			View view = null;
			switch (msg.what) {
			case 400:
				// 获得第一个item
				view = lv_tab3.getChildAt(0);
				break;
			case 500:
				// 获得最后一个item
				view = lv_tab3.getChildAt(lv_tab3.getChildCount() - 1);
				break;

			default:
				break;
			}

			if (null != view) {
				// 加载动画
				Animation animation = AnimationUtils.loadAnimation(
						getActivity(), R.anim.hehua_list_anim);
				view.startAnimation(animation);
			}
		}
	};
}
