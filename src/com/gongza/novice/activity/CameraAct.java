package com.gongza.novice.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gongza.novice.R;
import com.gongza.views.popupwindow.CameraPopwin;
import com.gongza.views.popupwindow.CameraPopwin.CameraPopupButtonListener;

public class CameraAct extends Activity implements OnClickListener {
	private Button bt_camera;
	private ImageView iv_camera;
	private TextView tv_camera;

	private PopupWindow pop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		initView();
	}

	private void initView() {
		bt_camera = (Button) findViewById(R.id.bt_camera);
		bt_camera.setOnClickListener(this);
		iv_camera = (ImageView) findViewById(R.id.iv_camera);
		tv_camera = (TextView) findViewById(R.id.tv_camera);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_camera:
			showPopupWindow();
			break;

		default:
			break;
		}
	}

	private void showPopupWindow() {
		pop = new CameraPopwin(CameraAct.this, new CameraPopupButtonListener() {

			@Override
			public void cropSmallClick(View v) {
				// 相册小图
				pop.dismiss();
			}

			@Override
			public void cropLargeClick(View v) {
				// 相册大图
				pop.dismiss();
			}

			@Override
			public void cameraSmallClick(View v) {
				// 相机小图
				pop.dismiss();
			}

			@Override
			public void cameraLargeClick(View v) {
				// 相机大图
				pop.dismiss();
			}
		});
		pop.showAtLocation(CameraAct.this.findViewById(R.id.layout_camera),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
	}
}
