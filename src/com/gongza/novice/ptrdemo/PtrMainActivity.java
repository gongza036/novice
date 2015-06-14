package com.gongza.novice.ptrdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gongza.novice.R;
import com.gongza.views.cube.ptr.PtrClassicFrameLayout;
import com.gongza.views.cube.ptr.PtrDefaultHandler;
import com.gongza.views.cube.ptr.PtrFrameLayout;
import com.gongza.views.cube.ptr.PtrHandler;

public class PtrMainActivity extends Activity implements OnClickListener{
	private PtrClassicFrameLayout mPtrFrame;
	private TextView tv_hello;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cube_ptr_activity_main);

		initView();

	}

	private void initView() {
		mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_grid_view_frame);
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
		mPtrFrame.setDurationToCloseHeader(1000);
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
		
		tv_hello=(TextView) findViewById(R.id.tv_hello);
		tv_hello.setOnClickListener(this);
	}

	protected void setupViews(final PtrClassicFrameLayout ptrFrame) {
		ptrFrame.setLoadingMinTime(1000);
		// setHeaderTitle(R.string.ptr_demo_block_auto_fresh);
		ptrFrame.postDelayed(new Runnable() {
			@Override
			public void run() {
				ptrFrame.autoRefresh(true);
			}
		}, 150);
	}
	
	protected void updateData() {
		mPtrFrame.refreshComplete();
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_hello:
			startActivity(new Intent(this, PtrMateriaAct.class));
			break;

		default:
			break;
		}
		
	}

}
