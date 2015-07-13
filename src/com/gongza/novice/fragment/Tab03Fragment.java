package com.gongza.novice.fragment;

import com.gongza.novice.R;
import com.gongza.novice.adapter.PtrGongzAdapter;
import com.gongza.views.listviewtools.HehuaPullToRefreshView;
import com.gongza.views.listviewtools.HehuaPullToRefreshView.OnFooterRefreshListener;
import com.gongza.views.listviewtools.HehuaPullToRefreshView.OnHeaderRefreshListener;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_3, container, false);
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

		return view;
	}
}
