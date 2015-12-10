package com.gongza.novice.adapter;

import java.util.ArrayList;

import com.gongza.novice.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PtrGongzAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<String> mList;

	public PtrGongzAdapter(Context mContext) {
		mList = new ArrayList<String>();
		mList.add("MovingImageView");
		mList.add("ProgressWheel");
		mList.add("JazzyViewPager4Fragment");
		mList.add("WaterfallList");
		mList.add("PtrClassicFrameLayout");
		mList.add("Volley");
		mList.add("Volley+RecyclerView...");
		mList.add("PullScrollView");
		mList.add("CameraAct");
		mList.add("RecyclerViewHeader");
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
		TextView tv_item = (TextView) convertView.findViewById(R.id.tv_item);
		tv_item.setText(mList.get(position));
		return convertView;
	}

}
