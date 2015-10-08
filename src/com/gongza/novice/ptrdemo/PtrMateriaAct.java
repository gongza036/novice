package com.gongza.novice.ptrdemo;

import com.gongza.novice.R;
import com.gongza.views.cube.header.MaterialHeader;
import com.gongza.views.cube.ptr.PtrFrameLayout;
import com.gongza.views.cube.ptr.PtrHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PtrMateriaAct extends Activity implements OnClickListener{
	private PtrFrameLayout mPtrFrameLayout;
	private TextView tv_hello;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cube_ptr_materia_main);
		initView();
	}

	private void initView() {
		tv_hello=(TextView) findViewById(R.id.tv_hello);
		tv_hello.setOnClickListener(this);
		tv_hello.setText("可以点2");		
		mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.material_style_ptr_frame);
		// header
		final MaterialHeader header = new MaterialHeader(this);
		// int[] colors = getResources().getIntArray(R.array.google_colors);
		int[] colors = new int[4];
		colors[0] = 0xffC93437;
		colors[1] = 0xff375BF1;
		colors[2] = 0xffF7D23E;
		colors[3] = 0xff34A350;
		header.setColorSchemeColors(colors);
		header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
		// header.setPadding(0, LocalDisplay.dp2px(15), 0,
		// LocalDisplay.dp2px(10));
		header.setPadding(0, 15, 0, 15);
		header.setPtrFrameLayout(mPtrFrameLayout);

		mPtrFrameLayout.setLoadingMinTime(1000);
		mPtrFrameLayout.setDurationToCloseHeader(1500);
		mPtrFrameLayout.setHeaderView(header);
		mPtrFrameLayout.addPtrUIHandler(header);
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrFrameLayout.autoRefresh(false);
			}
		}, 100);

		mPtrFrameLayout.setPtrHandler(new PtrHandler() {

			@Override
			public void onRefreshBegin(final PtrFrameLayout frame) {
				updateData();
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				// TODO Auto-generated method stub
				return true;
			}
		});

	}
	
	protected void updateData() {
		mPtrFrameLayout.refreshComplete();
    }
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_hello:
			startActivity(new Intent(this, PtrGongzActivity.class));
			break;

		default:
			break;
		}
		
	}
}
