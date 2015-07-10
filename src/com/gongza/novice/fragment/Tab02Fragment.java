package com.gongza.novice.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gongza.novice.R;
import com.gongza.novice.adapter.Bookends;
import com.gongza.novice.adapter.SimpleRLAdapter;
import com.gongza.novice.adapter.SimpleRLAdapter.OnItemClickListener;
import com.gongza.novice.adapter.StaggeredRLAdapterN;

/**
 * 
 * @author gongza
 *
 */
public class Tab02Fragment extends Fragment {
	private LinearLayout layout_tab2;
	private RecyclerView mRecyclerView;
	private SimpleRLAdapter adapter;
	private List<String> datas;

//	private StaggeredRLAdapter staggeredAdapter;
	private StaggeredRLAdapterN staggeredAdapter;
	private Bookends<SimpleRLAdapter> mBookends;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_2, container, false);
//		View view = inflater.inflate(R.layout.tab_2_rl, container, false);
		initData();
		initView(view, inflater);
		return view;
	}

	private void initData() {
		datas = new ArrayList<String>();
		for (int i = 'A'; i <= 'z'; i++) {
			datas.add("" + (char) i);
		}
	}

	private void initView(View view, LayoutInflater inflater) {
		layout_tab2 = (LinearLayout) view.findViewById(R.id.layout_tab2);
		mRecyclerView = new RecyclerView(getActivity());
		layout_tab2.addView(mRecyclerView);
//		mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_tab2);
		adapter = new SimpleRLAdapter(getActivity(), datas);
//		mRecyclerView.setAdapter(adapter);
		mBookends=new Bookends<SimpleRLAdapter>(adapter);
//		View headerView=inflater.inflate(R.layout.tab2_rl_header, null, false);
		View headerView=LayoutInflater.from(getActivity()).inflate(R.layout.tab2_rl_header, layout_tab2, false);
		View footerView=LayoutInflater.from(getActivity()).inflate(R.layout.tab2_rl_footer, layout_tab2, false);
		mBookends.addHeader(headerView);
		mBookends.addFooter(footerView);
		// 设置布局管理器
		LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(mLinearLayoutManager);

		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setAdapter(mBookends);
		// 设置分割线
		// mRecyclerView.addItemDecoration(new DividerItemDecoration(
		// getActivity(), DividerItemDecoration.VERTICAL_LIST));

		adapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemLongClick(View view, int positon) {
				Toast.makeText(getActivity(), "LongClick" + positon,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onItemClick(View view, int position) {
				Toast.makeText(getActivity(), "Click" + position,
						Toast.LENGTH_SHORT).show();
			}
		});

		
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_gridview:
			mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
					3));
			break;
		case R.id.action_listview:
			mRecyclerView.setLayoutManager(new LinearLayoutManager(
					getActivity()));
			break;
		case R.id.action_staggered:

			break;
		case R.id.action_hor_gridview:
			mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5,
					StaggeredGridLayoutManager.HORIZONTAL));
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	// 变成GridView
	public void actionGridView() {
		mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
	}

	// 变成ListView
	public void actionListView() {
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
	}

	// 变成横向GriView
	public void actionHorizontalGridView() {
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5,
				StaggeredGridLayoutManager.HORIZONTAL));
	}

	// 变成瀑布流
	public void actionStaggered() {
		staggeredAdapter = new StaggeredRLAdapterN(getActivity(), datas);
		mRecyclerView.setAdapter(staggeredAdapter);
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
				StaggeredGridLayoutManager.VERTICAL));
		
		staggeredAdapter.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemLongClick(View view, int positon) {
				staggeredAdapter.delData(1);
			}
			
			@Override
			public void onItemClick(View view, int position) {
				
			}
		});

	}

	// 增加一条item
	public void actionAdd() {
		if (adapter!=null) {
			adapter.addData(1);
		}
		if (staggeredAdapter!=null) {
			staggeredAdapter.addData(1);
		}
	}

	// 删除一条item
	public void actionDelete() {
		if (adapter!=null) {
			adapter.delData(1);
		}
		
		if (staggeredAdapter!=null) {
			staggeredAdapter.delData(1);
		}
	}

}
