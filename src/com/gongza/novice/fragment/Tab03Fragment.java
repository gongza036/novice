package com.gongza.novice.fragment;

import com.gongza.novice.R;
import com.gongza.novice.activity.ActivityJazzy;
import com.gongza.novice.activity.MovingImageViewAct;
import com.gongza.novice.activity.ProgressWheelAct;
import com.gongza.novice.adapter.PtrGongzAdapter;
import com.gongza.views.listviewtools.HehuaPullToRefreshView;
import com.gongza.views.listviewtools.HehuaPullToRefreshView.OnFooterRefreshListener;
import com.gongza.views.listviewtools.HehuaPullToRefreshView.OnHeaderRefreshListener;
import com.gongza.views.movingimageview.MovingImageView;

import android.animation.Animator;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * 
 * @author gongza
 *
 */
public class Tab03Fragment extends Fragment {
	private HehuaPullToRefreshView layout_refresh;
	private ListView lv_tab3;
	
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
		PtrGongzAdapter adapter = new PtrGongzAdapter(getActivity());
		lv_tab3.setAdapter(adapter);
		layout_refresh = (HehuaPullToRefreshView) view
				.findViewById(R.id.layout_refresh);
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
					startActivity(new Intent(getActivity(), MovingImageViewAct.class));
					break;
				case 1:
					startActivity(new Intent(getActivity(), ProgressWheelAct.class));
					break;
				case 2:
					startActivity(new Intent(getActivity(), ActivityJazzy.class));
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
	
}
