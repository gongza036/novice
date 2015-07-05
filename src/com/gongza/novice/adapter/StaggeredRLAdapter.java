package com.gongza.novice.adapter;

import java.util.ArrayList;
import java.util.List;

import com.gongza.novice.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * RecyclerView 的Adapter
 * 
 * @author gongza
 *
 */
public class StaggeredRLAdapter extends
		RecyclerView.Adapter<StaggeredRLAdapter.GzViewHolder> {
	private LayoutInflater inflater;
	private Context context;
	private List<String> datas;

	private List<Integer> mHeight;


	public StaggeredRLAdapter(Context context, List<String> datas) {
		this.context = context;
		this.datas = datas;
		inflater = LayoutInflater.from(context);
		mHeight = new ArrayList<Integer>();
		for (int i = 0; i < datas.size(); i++) {
			mHeight.add((int) (100 + Math.random() * 300));
		}
	}

	@Override
	public int getItemCount() {
		return datas.size();
	}

	public void addData(int pos) {
		datas.add("加了一条");
		notifyItemInserted(pos);
	}

	public void delData(int pos) {
		datas.remove(pos);
		notifyItemRemoved(pos);
	}

	@Override
	public void onBindViewHolder(final StaggeredRLAdapter.GzViewHolder holder,
			final int pos) {
		// 绑定ViewHolder里的数据

		LayoutParams lp = holder.itemView.getLayoutParams();
		lp.height = mHeight.get(pos);
		holder.itemView.setLayoutParams(lp);
		holder.tv_item.setText(datas.get(pos));

	}

	@Override
	public StaggeredRLAdapter.GzViewHolder onCreateViewHolder(ViewGroup arg0,
			int arg1) {
		// 创建ViewHolder
		View view = inflater.inflate(R.layout.tab2_rl_item, arg0, false);
		StaggeredRLAdapter.GzViewHolder mGzViewHolder = new StaggeredRLAdapter.GzViewHolder(
				view);
		return mGzViewHolder;
	}

	class GzViewHolder extends ViewHolder {
		TextView tv_item;

		public GzViewHolder(View itemView) {
			super(itemView);

			tv_item = (TextView) itemView.findViewById(R.id.tv_item);
		}

	}

}
