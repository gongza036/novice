package com.gongza.views.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.gongza.novice.R;

public class CameraPopwin extends PopupWindow {
	private Button bt_camera_large, bt_crop_large, bt_camera_small,
			bt_crop_small, bt_cancel;
	private View popView;
	private CameraPopupButtonListener btnOnClick;

	public CameraPopwin(final Activity context,
			CameraPopupButtonListener listener) {
		super(context);
		this.btnOnClick = listener;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		popView = inflater.inflate(R.layout.popwin_camera, null);
		bt_camera_large = (Button) popView.findViewById(R.id.bt_camera_large);
		bt_crop_large = (Button) popView.findViewById(R.id.bt_crop_large);
		bt_camera_small = (Button) popView.findViewById(R.id.bt_camera_small);
		bt_crop_small = (Button) popView.findViewById(R.id.bt_crop_small);
		bt_cancel = (Button) popView.findViewById(R.id.bt_cancel);

		// 取消按钮
		bt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		// 相机大图
		bt_camera_large.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (btnOnClick != null) {
					btnOnClick.cameraLargeClick(v);
				}
			}
		});
		// 相机小图
		bt_camera_small.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (btnOnClick != null) {
					btnOnClick.cameraSmallClick(v);
				}
			}
		});

		bt_crop_large.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (btnOnClick != null) {
					btnOnClick.cropLargeClick(v);
				}
			}
		});
		bt_crop_small.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (btnOnClick != null) {
					btnOnClick.cropSmallClick(v);
				}
			}
		});
		this.setContentView(popView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setAnimationStyle(R.style.camera_pop_animation);
		ColorDrawable dw = new ColorDrawable(0xb0646464);
		this.setBackgroundDrawable(dw);

	}

	public interface CameraPopupButtonListener {
		public void cameraLargeClick(View v);

		public void cameraSmallClick(View v);

		public void cropLargeClick(View v);

		public void cropSmallClick(View v);
	};
}
