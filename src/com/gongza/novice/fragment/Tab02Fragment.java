package com.gongza.novice.fragment;

import com.gongza.novice.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
/**
 * 
 * @author gongza
 *
 */
public class Tab02Fragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_2, container, false);
		final TextView tv_dian = (TextView) view.findViewById(R.id.tv_dian);
		tv_dian.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int n=(int) (Math.random()*100);
				tv_dian.setText("再点--"+n);
			}
		});

		return view;
	}
}
