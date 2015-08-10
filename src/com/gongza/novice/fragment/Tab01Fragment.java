package com.gongza.novice.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gongza.novice.R;
import com.gongza.novice.ptrdemo.PtrMainActivity;
import com.gongza.novice.volleydemo.TestVolleyAct;
import com.gongza.novice.volleydemo.VolleyRecycelrViewAct;

/**
 * 
 * @author gongza
 *
 */
public class Tab01Fragment extends Fragment implements OnClickListener {
	private LinearLayout layout_ptrpull, layout_volley,layout_volley_recycler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_1, container, false);
		initView(view, inflater);
		return view;
	}

	private void initView(View view, LayoutInflater inflater) {
		layout_ptrpull = (LinearLayout) view.findViewById(R.id.layout_ptrpull);
		layout_volley = (LinearLayout) view.findViewById(R.id.layout_volley);
		layout_volley_recycler = (LinearLayout) view.findViewById(R.id.layout_volley_recycler);
		layout_ptrpull.setOnClickListener(this);
		layout_volley.setOnClickListener(this);
		layout_volley_recycler.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_ptrpull:
			startActivity(new Intent(getActivity(), PtrMainActivity.class));
			break;
		case R.id.layout_volley:
			startActivity(new Intent(getActivity(), TestVolleyAct.class));
			break;
		case R.id.layout_volley_recycler:
			startActivity(new Intent(getActivity(), VolleyRecycelrViewAct.class));
			break;

		default:
			break;
		}
	}

}
