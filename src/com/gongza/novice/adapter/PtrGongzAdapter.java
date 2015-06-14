package com.gongza.novice.adapter;

import java.util.ArrayList;

import com.gongza.novice.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class PtrGongzAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<String> mList;

	public PtrGongzAdapter(Context mContext) {
		mList = new ArrayList<String>();
		mList.add("1");
		mList.add("2");
		mList.add("3");
		mList.add("4");
		mList.add("5");
		mList.add("6");
		mList.add("7");
		mList.add("8");
		mList.add("9");
		mList.add("10");
		mList.add("11");
		mList.add("12");
		mList.add("13");
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.cube_ptr_gongz_item, null);
		}
		return convertView;
	}

}
