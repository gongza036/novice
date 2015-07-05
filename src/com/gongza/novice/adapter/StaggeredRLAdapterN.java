package com.gongza.novice.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;

/**
 * RecyclerView 的Adapter
 * 
 * @author gongza
 *
 */
public class StaggeredRLAdapterN extends SimpleRLAdapter {

	private List<Integer> mHeight;

	public StaggeredRLAdapterN(Context context, List<String> datas) {
		super(context, datas);
		mHeight = new ArrayList<Integer>();
		for (int i = 0; i < mDatas.size(); i++) {
			mHeight.add((int) (100 + Math.random() * 300));
		}
	}

	@Override
	public void onBindViewHolder(GzViewHolder holder, final int pos) {
		// 绑定ViewHolder里的数据

		LayoutParams lp = holder.itemView.getLayoutParams();
		lp.height = mHeight.get(pos);
		holder.itemView.setLayoutParams(lp);
		holder.tv_item.setText(mDatas.get(pos));

		setUpItemEvent(holder);
	}

}
