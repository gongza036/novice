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

	private File tempFile;
	private File cropFile;

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
		case PICK:
			// 相册回来 --- 此时data中是所选图片的uri地址
			if (data != null) {
				Uri uri = data.getData();
				iv_camera.setImageBitmap(getBitmapFromUri(uri));
			}
			break;
		case CAMERA_SMALL:
			// 相机小图回来 ---- 此时data中包含图片对象
			if (resultCode == RESULT_OK) {
				Bitmap photo = data.getParcelableExtra("data");
				if (photo != null) {
					iv_camera.setImageBitmap(photo);
					tv_camera.setText("");
				}
			}

			break;
		case CAMERA_LARGE:
			// 相机大图回来 --- 此时图片已经保存在指定File文件中
			if (resultCode == RESULT_OK) {
				// Drawable imageDrawable = Drawable.createFromPath(tempFile
				// .toString());
				// BitmapDrawable bd = (BitmapDrawable) imageDrawable;
				// Bitmap bm = bd.getBitmap();
				// iv_camera.setImageBitmap(bm);
				iv_camera.setImageBitmap(getBitmapFromFile(tempFile));
			}
			break;
		case CROP_PICK:
			// 相册回来进裁剪
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				IntentCropFromPick(uri);
			}
			break;
		case CROP_CAMERA:
			// 相机回来进裁剪大图
			if (resultCode == RESULT_OK) {
				IntentCropFromCamera();
			}
			break;
		case CROP:
			// 裁剪大图回来
			if (resultCode == RESULT_OK) {
				iv_camera.setImageBitmap(getBitmapFromFile(cropFile));
			}
			break;
		case CROP_CAMERA_SMALL:
			// 相机回来进裁剪小图
			if (resultCode == RESULT_OK) {
				IntentCropSmallFromCamera();
			}
			break;
		case CROP_SMALL:
			// 裁剪小图回来
			if (resultCode == RESULT_OK) {
				Bitmap photo = data.getParcelableExtra("data");
				if (photo != null) {
					iv_camera.setImageBitmap(photo);
					tv_camera.setText("");
				}
			}
			break;

		default:
			break;
		}
	}

	private static final int PICK = 1001;// 相册
	private static final int CAMERA_SMALL = 2001;// 相机小图
	private static final int CAMERA_LARGE = 2002;// 相机大图
	private static final int CROP_PICK = 3001;// 相册裁剪
	private static final int CROP_CAMERA = 3002;// 相机裁剪大图
	private static final int CROP_CAMERA_SMALL = 3003;// 相机裁剪小图
	private static final int CROP = 4000;// 裁剪大图
	private static final int CROP_SMALL = 4001;// 裁剪小图

	/** 进相机大图 */
	private void IntentCameraLarge() {
		tempFile = new File(Environment.getExternalStorageDirectory(),
				getPhotoFileName());
		Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定调用相机拍照后照片的储存路径
		cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		startActivityForResult(cameraintent, CAMERA_LARGE);
	}

	/** 进相机小图 */
	private void IntentCameraSmall() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, CAMERA_SMALL);
	}

	/** 进相册 */
	private void IntentPick() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/*");
		startActivityForResult(intent, PICK);
	}

	/** 进相册再裁剪 */
	private void IntentCropPick() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/*");
		startActivityForResult(intent, CROP_PICK);
	}

	/** 相册回来再进裁剪 */
	private void IntentCropFromPick(Uri uri) {
		cropFile = new File(Environment.getExternalStorageDirectory(),
				getPhotoFileName());
		Uri saveUri = Uri.fromFile(cropFile);
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框的比例，1：1
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 800);// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputY", 800);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, saveUri);
		intent.putExtra("return-data", false); // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
		intent.putExtra("noFaceDetection", true); // 取消人脸识别
		startActivityForResult(intent, CROP);
	}

	/** 进相机再裁剪大图 */
	private void IntentCropCamera() {
		tempFile = new File(Environment.getExternalStorageDirectory(),
				getPhotoFileName());
		Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定调用相机拍照后照片的储存路径
		cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		startActivityForResult(cameraintent, CROP_CAMERA);
	}

	/** 相机回来再进裁剪大图 */
	private void IntentCropFromCamera() {
		cropFile = new File(Environment.getExternalStorageDirectory(),
				getPhotoFileName());
		Uri saveUri = Uri.fromFile(cropFile);
		Uri uri = Uri.fromFile(tempFile);
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框的比例，1：1
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 800);// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputY", 800);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, saveUri);
		intent.putExtra("return-data", false); // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());// 图片格式
		intent.putExtra("noFaceDetection", true); // 取消人脸识别
		startActivityForResult(intent, CROP);
	}

	/** 进相机再裁剪小图 */
	private void IntentCropSmallCamera() {
		tempFile = new File(Environment.getExternalStorageDirectory(),
				getPhotoFileName());
		Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定调用相机拍照后照片的储存路径
		cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		startActivityForResult(cameraintent, CROP_CAMERA_SMALL);
	}

	/** 相机回来再进裁剪 -剪出小图 */
	private void IntentCropSmallFromCamera() {
		Uri uri = Uri.fromFile(tempFile);
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 7);
		intent.putExtra("aspectY", 4);
		intent.putExtra("outputX", 350);// 这里不能超过400
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, CROP_SMALL);
	}

	/** 打开弹出框 */
	private void showPopupWindow() {
		pop = new CameraPopwin(CameraAct.this, new CameraPopupButtonListener() {

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

			@Override
			public void pickClick(View v) {
				// 相册直接取
				pop.dismiss();
				IntentPick();
			}

			@Override
			public void pickCropClick(View v) {
				// 相册裁剪
				pop.dismiss();
				IntentCropPick();
			}

			@Override
			public void cameraCropClick(View v) {
				// 相机裁剪大图
				pop.dismiss();
				IntentCropCamera();
			}

			@Override
			public void cameraCropSmallClick(View v) {
				// 相机裁剪小图
				pop.dismiss();
				IntentCropSmallCamera();
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

	/** 使用系统当前日期加以调整作为照片的名称 */
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	/** uri获取bitmap */
	private Bitmap getBitmapFromUri(Uri uri) {
		tv_camera.setText(uri.toString());
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

	/** 文件获取bitmap */
	private Bitmap getBitmapFromFile(File file) {
		tv_camera.setText(file.getAbsolutePath());
		Drawable imageDrawable = Drawable.createFromPath(file.toString());
		BitmapDrawable bd = (BitmapDrawable) imageDrawable;
		Bitmap bm = bd.getBitmap();
		return bm;
	}
	
}
