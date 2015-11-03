package com.gongza.novice.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
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

	// private File tempFile;

	private File tempFile = new File(Environment.getExternalStorageDirectory(),
			getPhotoFileName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		// filePath();
		initView();
	}

	// private void filePath() {
	// tempFile = new File(getFilesDir().getAbsolutePath(),
	// "/img/camera_act_tmp.jpg");
	// }

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case CROP_SMALL:
			if (data != null) {
				Uri uri = data.getData();
				iv_camera.setImageBitmap(getBitmapFromUri(uri));
			}
			break;
		case CROP_LARGE:

			break;
		case CAMERA_SMALL:
			if (resultCode == RESULT_OK) {
				Bitmap photo = data.getParcelableExtra("data");
				if (photo != null) {
					iv_camera.setImageBitmap(photo);
				}
			}

			break;
		case CAMERA_LARGE:
			if (resultCode == RESULT_OK) {
				Drawable imageDrawable = Drawable.createFromPath(tempFile
						.toString());
				BitmapDrawable bd = (BitmapDrawable) imageDrawable;
				Bitmap bm = bd.getBitmap();
				iv_camera.setImageBitmap(bm);
			}
			break;

		default:
			break;
		}
	}

	private static final int CROP_SMALL = 1001;// 相册小图
	private static final int CROP_LARGE = 1002;// 相册大图
	private static final int CAMERA_SMALL = 2001;// 相机
	private static final int CAMERA_LARGE = 2002;// 相机

	private void IntentCameraLarge() {
		Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定调用相机拍照后照片的储存路径
		cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		startActivityForResult(cameraintent, CAMERA_LARGE);

	}

	private void IntentCameraSmall() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, CAMERA_SMALL);
	}

	private void IntentCropSmall() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/*");
		startActivityForResult(intent, CROP_SMALL);
	}

	private void showPopupWindow() {
		pop = new CameraPopwin(CameraAct.this, new CameraPopupButtonListener() {

			@Override
			public void cropSmallClick(View v) {
				// 相册小图
				pop.dismiss();
				IntentCropSmall();
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
				IntentCameraSmall();
			}

			@Override
			public void cameraLargeClick(View v) {
				// 相机大图
				pop.dismiss();
				IntentCameraLarge();
			}
		});
		pop.showAtLocation(CameraAct.this.findViewById(R.id.layout_camera),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
	}

	/** 保存方法 */
	public void saveBitmap(Bitmap bm) {
		// File f = new File("/sdcard/namecard/", "camera_act_tmp.PNG");
		File f = new File(getFilesDir().getAbsolutePath(), "camera_act_tmp.PNG");
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 使用系统当前日期加以调整作为照片的名称
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	// uri获取bitmap
	private Bitmap getBitmapFromUri(Uri uri) {
		try {
			// 读取uri所在的图片
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(
					this.getContentResolver(), uri);
			return bitmap;
		} catch (Exception e) {
			Log.e("[Android]", e.getMessage());
			Log.e("[Android]", "目录为：" + uri);
			e.printStackTrace();
			return null;
		}
	}
}
