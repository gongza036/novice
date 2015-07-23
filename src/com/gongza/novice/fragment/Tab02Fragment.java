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
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gongza.novice.R;
import com.gongza.novice.adapter.Bookends;
import com.gongza.novice.adapter.RecyclerViewHeader;
import com.gongza.novice.adapter.SimpleRLAdapter;
import com.gongza.novice.adapter.SimpleRLAdapter.OnItemClickListener;
import com.gongza.novice.adapter.StaggeredRLAdapterN;
import com.gongza.views.cube.ptr.PtrDefaultHandler;
import com.gongza.views.cube.ptr.PtrFrameLayout;
import com.gongza.views.cube.ptr.PtrGongzFrameLayout;
import com.gongza.views.cube.ptr.PtrHandler;
import com.gongza.views.listviewtools.HehuaPullToRefreshView;
import com.gongza.views.listviewtools.HehuaPullToRefreshView.OnFooterRefreshListener;
import com.gongza.views.listviewtools.HehuaPullToRefreshView.OnHeaderRefreshListener;

/**
 * 
 * @author gongza
 *
 */
public class Tab02Fragment extends Fragment {
	private PtrGongzFrameLayout mPtrFrame;
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
//		View view = inflater.inflate(R.layout.tab_2, container, false);
		View view = inflater.inflate(R.layout.tab_2_rl, container, false);
		initData();
		initPullView(view);
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
//		layout_tab2 = (LinearLayout) view.findViewById(R.id.layout_tab2);
//		mRecyclerView = new RecyclerView(getActivity());
//		layout_tab2.addView(mRecyclerView);
		mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_tab2);
		adapter = new SimpleRLAdapter(getActivity(), datas);
		mRecyclerView.setAdapter(adapter);
		//第一种增加头部的方法   
//		RecyclerViewHeader header = RecyclerViewHeader.fromXml(getActivity(), R.layout.tab2_rl_header);
		
		//第二种种增加头部的方法    
		mBookends=new Bookends<SimpleRLAdapter>(adapter);
//		View headerView=inflater.inflate(R.layout.tab2_rl_header, null, false);
		View headerView=LayoutInflater.from(getActivity()).inflate(R.layout.tab2_rl_header, mPtrFrame, false);
		View footerView=LayoutInflater.from(getActivity()).inflate(R.layout.tab2_rl_footer, mPtrFrame, false);
		mBookends.addHeader(headerView);
		mBookends.addFooter(footerView);
		mRecyclerView.setAdapter(mBookends);
		// 设置布局管理器
		LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(mLinearLayoutManager);

		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
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
		//第一种增加头部的方法   
//		header.attachTo(mRecyclerView);
		
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

	
	private void initPullView(View view) {
		mPtrFrame = (PtrGongzFrameLayout) view.findViewById(R.id.rotate_header_grid_view_frame);
		mPtrFrame.setLastUpdateTimeRelateObject(this);
		mPtrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				 updateData();
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
						content, header);
			}
		});
		// the following are default settings
		mPtrFrame.setResistance(2.3f);
		mPtrFrame.setRatioOfHeaderHeightToRefresh(1.0f);
		mPtrFrame.setDurationToClose(200);
		mPtrFrame.setDurationToCloseHeader(800);
		// default is false
		mPtrFrame.setPullToRefresh(false);
		// default is true
		mPtrFrame.setKeepHeaderWhenRefresh(true);
		mPtrFrame.postDelayed(new Runnable() {
			@Override
			public void run() {
				// mPtrFrame.autoRefresh();
			}
		}, 100);
		// updateData();
		setupViews(mPtrFrame);
	}

	protected void setupViews(final PtrGongzFrameLayout ptrFrame) {
		ptrFrame.setLoadingMinTime(1000);
		// setHeaderTitle(R.string.ptr_demo_block_auto_fresh);
		ptrFrame.postDelayed(new Runnable() {
			@Override
			public void run() {
				ptrFrame.autoRefresh(true);
			}
		}, 500);
	}
	
	protected void updateData() {
		mPtrFrame.refreshComplete();
    }

	
}
