package com.gongza.novice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BannerListViewAdapter extends BaseAdapter {
	private Context context;

	public BannerListViewAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return 11;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			TextView tv = new TextView(context);
			tv.setText("listview--" + position);
			tv.setPadding(100, 50, 50, 50);
			convertView = tv;
		}

		return convertView;
	}

}
