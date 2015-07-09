package com.gongza.novice.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gongza.novice.R;

/**
 * RecyclerView 的Adapter
 * 
 * @author gongza
 *
 */
public class SimpleRLAdapter extends RecyclerView.Adapter<GzViewHolder> {
	private LayoutInflater inflater;
	private Context context;
	protected List<String> mDatas;

	public interface OnItemClickListener {
		void onItemClick(View view, int position);

		void onItemLongClick(View view, int positon);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
		this.mOnItemClickListener = mOnItemClickListener;
	}

	public SimpleRLAdapter(Context context, List<String> datas) {
		this.context = context;
		this.mDatas = datas;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getItemCount() {
		return mDatas.size();
	}

	public void addData(int pos) {
		mDatas.add(pos, "加了一条");
		notifyItemInserted(pos);
	}

	public void delData(int pos) {
		mDatas.remove(pos);
		notifyItemRemoved(pos);
	}

	@Override
	public void onBindViewHolder(final GzViewHolder holder, int pos) {
		// 绑定ViewHolder里的数据
		holder.tv_item.setText(mDatas.get(pos));

		setUpItemEvent(holder);
	}

	protected void setUpItemEvent(final GzViewHolder holder) {
		if (mOnItemClickListener != null) {

			holder.itemView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int layoutPosition = holder.getLayoutPosition();
					mOnItemClickListener.onItemClick(holder.itemView,
							layoutPosition);
				}
			});

			holder.itemView.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					int layoutPosition = holder.getLayoutPosition();
					mOnItemClickListener.onItemLongClick(holder.itemView,
							layoutPosition);
					return false;
				}
			});
		}
	}

	@Override
	public GzViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// 创建ViewHolder
		View view = inflater.inflate(R.layout.tab2_rl_item, arg0, false);
		GzViewHolder mGzViewHolder = new GzViewHolder(view);
		return mGzViewHolder;
	}

}

class GzViewHolder extends ViewHolder {
	TextView tv_item;

	public GzViewHolder(View itemView) {
		super(itemView);

		tv_item = (TextView) itemView.findViewById(R.id.tv_item);
	}

}