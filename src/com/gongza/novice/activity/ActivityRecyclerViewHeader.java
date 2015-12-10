package com.gongza.novice.activity;

import java.util.ArrayList;

import com.gongza.novice.R;
import com.gongza.novice.adapter.AdapterRVHeader;
import com.gongza.novice.adapter.BaseRecyclerAdapter;
import com.gongza.views.recyclerview.GridItemDecoration;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class ActivityRecyclerViewHeader extends Activity {
	private RecyclerView mRecyclerView;
	private RecyclerView.LayoutManager mLayoutManager;
	private AdapterRVHeader mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recyclerviewheader_main);

		mRecyclerView = (RecyclerView) findViewById(R.id.list);
//		 mLayoutManager = new LinearLayoutManager(this,
//		 LinearLayoutManager.VERTICAL, false);
		mLayoutManager = new GridLayoutManager(this, 2);
//		 mLayoutManager = new StaggeredGridLayoutManager(2,
//		 StaggeredGridLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.addItemDecoration(new GridItemDecoration(this, true));

		mAdapter = new AdapterRVHeader();
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.addDatas(generateData());
		setHeader(mRecyclerView);
		mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<String>() {
			@Override
			public void onItemClick(int position, String data) {
				Toast.makeText(ActivityRecyclerViewHeader.this, position + "," + data, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void setHeader(RecyclerView view) {
		View header = LayoutInflater.from(this).inflate(R.layout.activity_recyclerviewheader_header, view, false);
		mAdapter.setHeaderView(header);
	}

	private ArrayList<String> generateData() {
		ArrayList<String> data = new ArrayList<String>() {
			{
				for (int i = 0; i < 21; i++)
					add("数据" + i);
			}
		};
		return data;
	}
}
