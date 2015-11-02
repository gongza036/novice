package com.gongza.novice.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gongza.novice.R;
import com.gongza.views.pullscrollview.PullScrollView;

public class PullScrollViewAct extends Activity {
	private TableLayout mMainLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pullscrollview);

		ImageView headerView = (ImageView) findViewById(R.id.background_img);
		
		PullScrollView pullScrollView = (PullScrollView) findViewById(R.id.pullscrollview);
		pullScrollView.setmHeaderView(headerView);

		mMainLayout = (TableLayout) findViewById(R.id.table_layout);
		showTable();
		
		showUserHeaderEmpty(headerView);
	}

	public void showTable() {
		TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
				TableRow.LayoutParams.MATCH_PARENT,
				TableRow.LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		layoutParams.leftMargin = 30;
		layoutParams.bottomMargin = 10;
		layoutParams.topMargin = 10;

		for (int i = 0; i < 30; i++) {
			TableRow tableRow = new TableRow(this);
			TextView textView = new TextView(this);
			textView.setText("Test pull down scroll view " + i);
			textView.setTextSize(20);
			textView.setPadding(15, 15, 15, 15);

			tableRow.addView(textView, layoutParams);
			if (i % 2 != 0) {
				tableRow.setBackgroundColor(Color.LTGRAY);
			} else {
				tableRow.setBackgroundColor(Color.WHITE);
			}
			final int n = i;
			tableRow.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), "Click item " + n,
							Toast.LENGTH_SHORT).show();
				}
			});

			mMainLayout.addView(tableRow);
		}
	}

	private void showUserHeaderEmpty(ImageView view) {
		Bitmap bp = BitmapFactory.decodeResource(getResources(),
				R.drawable.cube_ptr__head_loding12);
		setImgBlur(PullScrollViewAct.this, view, bp);
	}

	/**
	 * 给ImageView设置高斯模糊
	 */
	@SuppressLint("NewApi")
	private void setImgBlur(Context context, ImageView iv, Bitmap bitmap) {
		if (iv == null || bitmap == null) {
			return;
		}
		if (Integer.parseInt(VERSION.SDK) < 11) {
			iv.setImageBitmap(bitmap);
			// 版本3.0以上才有此方法
			return;
		}
		try {
			final Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			RenderScript rs = RenderScript.create(context
					.getApplicationContext());
			ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs,
					Element.U8_4(rs));
			Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
			Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
			blurScript.setRadius(10.f);
			blurScript.setInput(allIn);
			blurScript.forEach(allOut);
			allOut.copyTo(outBitmap);
			bitmap.recycle();
			rs.destroy();
			iv.setImageBitmap(outBitmap);
		} catch (Exception e) {
			iv.setImageBitmap(bitmap);
		}

	}

}
