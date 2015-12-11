package com.gongza.novice.recyclerviewsolution;

import java.util.ArrayList;

import com.gongza.novice.R;
import com.gongza.novice.recyclerviewsolution.weight.SampleFooter;
import com.gongza.novice.recyclerviewsolution.weight.SampleHeader;
import com.gongza.views.recyclerviewsolution.HeaderAndFooterRecyclerViewAdapter;
import com.gongza.views.recyclerviewsolution.RecyclerViewUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by cundong on 2015/10/29.
 *
 * 带HeaderView、FooterView的LinearLayout RecyclerView
 */
public class LinearLayoutActivity extends Activity {
	private SwipeRefreshLayout mSwipeRefreshWidget;
	int lastVisibleItem;

	private RecyclerView mRecyclerView = null;

	private DataAdapter mDataAdapter = null;

	private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rv_solution_activity_linear);
		initView();
	}

	private void initView() {
		mRecyclerView = (RecyclerView) findViewById(R.id.list);
		// init data
		ArrayList<String> dataList = new ArrayList<>();
		for (int i = 0; i < 26; i++) {
			dataList.add("item" + i);
		}
		mDataAdapter = new DataAdapter(this);
		mDataAdapter.setData(dataList);
		mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mDataAdapter);
		mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
		final LinearLayoutManager lm=new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(lm);
		// add a HeaderView
		RecyclerViewUtils.setHeaderView(mRecyclerView, new SampleHeader(this));
		// add a FooterView
		RecyclerViewUtils.setFooterView(mRecyclerView, new SampleFooter(this));

		// 设置下拉刷新控件
		mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
//		mSwipeRefreshWidget.setColorScheme(0xffC93437, 0xff375BF1, 0xffF7D23E, 0xff34A350);
		mSwipeRefreshWidget.setColorSchemeColors(0xffC93437, 0xff375BF1, 0xffF7D23E, 0xff34A350);
		// 这句话是为了，第一次进入页面的时候显示加载进度条
		mSwipeRefreshWidget.setProgressViewOffset(false, 0,
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
		mSwipeRefreshWidget.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				
				setupViews(mSwipeRefreshWidget);
			}
		});

	}

	protected void setupViews(final SwipeRefreshLayout sf) {
		sf.postDelayed(new Runnable() {
			@Override
			public void run() {
//				ptrFrame.autoRefresh(true);
				sf.setRefreshing(false);
			}
		}, 2000);
	}

	private class DataAdapter extends RecyclerView.Adapter {

		private LayoutInflater mLayoutInflater;
		private ArrayList<String> mDataList = new ArrayList<>();

		public DataAdapter(Context context) {
			mLayoutInflater = LayoutInflater.from(context);
		}

		public void setData(ArrayList<String> list) {
			this.mDataList = list;
			notifyDataSetChanged();
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new ViewHolder(mLayoutInflater.inflate(R.layout.rv_solution_item_text, parent, false));
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

			String item = mDataList.get(position);

			ViewHolder viewHolder = (ViewHolder) holder;
			viewHolder.textView.setText(item);
		}

		@Override
		public int getItemCount() {
			return mDataList.size();
		}

		private class ViewHolder extends RecyclerView.ViewHolder {

			private TextView textView;

			public ViewHolder(View itemView) {
				super(itemView);
				textView = (TextView) itemView.findViewById(R.id.info_text);

				textView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String text = mDataList
								.get(RecyclerViewUtils.getAdapterPosition(mRecyclerView, ViewHolder.this));
						Toast.makeText(LinearLayoutActivity.this, text, Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
	}
}