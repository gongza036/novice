package com.gongza.novice.fragment;

import java.util.ArrayList;
import java.util.List;

import com.gongza.novice.R;
import com.gongza.novice.adapter.BannerListViewAdapter;
import com.gongza.views.listviewtools.AdViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;

public class TabViewPager3Fragment extends Fragment {
	private ListView lv_ad;
	private BannerListViewAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tabviewpager3_fragment, container, false);
		initView(view, inflater);
		return view;
	}

	private void initView(View view, LayoutInflater inflater) {
		lv_ad = (ListView) view.findViewById(R.id.lv_ad);
		adapter = new BannerListViewAdapter(getActivity());
		lv_ad.setAdapter(adapter);
		View bannerLayout = inflater.inflate(R.layout.layout_banner, null);
		vp_banner = (AdViewPager) bannerLayout.findViewById(R.id.vp_banner);
		dot_banner = (LinearLayout) bannerLayout.findViewById(R.id.dot_banner);
		lv_ad.addHeaderView(bannerLayout);
		buildBanner();
	}

	/**
	 * 顶部滚动广告
	 */
	private AdViewPager vp_banner;
	private LinearLayout dot_banner;
	private List<ImageView> dotList;
	private PagerAdapter bannerAdapter;
	private ArrayList<Bitmap> adList = new ArrayList<Bitmap>();
	private ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();
	private int position;
	private int currentItem = 0;

	private void buildBanner() {
		int dotWidth = 10;
		dot_banner.removeAllViews();
		dotList = new ArrayList<ImageView>();
		Bitmap bitmap1 = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_launcher);
		Bitmap bitmap2 = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_launcher);
		Bitmap bitmap3 = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_launcher);
		Bitmap bitmap4 = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_launcher);
		adList.add(bitmap1);
		adList.add(bitmap2);
		adList.add(bitmap3);
		adList.add(bitmap4);
		for (int i = 0; i < adList.size(); i++) {
			position = i;
			ImageView dotIv = new ImageView(getActivity());
			LayoutParams dotParams = new LayoutParams(dotWidth, dotWidth);
			dotParams.leftMargin = dotWidth;
			dotIv.setLayoutParams(dotParams);
			if (i == 0) {
				dotIv.setBackgroundResource(R.drawable.dot_select);
			} else {
				dotIv.setBackgroundResource(R.drawable.dot_normal);
			}

			dotList.add(dotIv);
			dot_banner.addView(dotIv);

			final ImageView imageView = new ImageView(getActivity());
			android.support.v4.view.ViewPager.LayoutParams params = new android.support.v4.view.ViewPager.LayoutParams();
			params.height = android.support.v4.view.ViewPager.LayoutParams.MATCH_PARENT;
			params.width = android.support.v4.view.ViewPager.LayoutParams.MATCH_PARENT;
			imageView.setLayoutParams(params);
			imageView.setScaleType(ScaleType.FIT_XY);
			imageView.setImageBitmap(adList.get(i));
			imageViewList.add(imageView);
			// 点击广告跳转
			imageView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(getActivity(), "点点", Toast.LENGTH_SHORT).show();
				}
			});
		}
		bannerAdapter = new LoadingAdapter(imageViewList);
		vp_banner.setAdapter(bannerAdapter);
		viewPagerhandler.sendEmptyMessageDelayed(1, 5 * 1000);
		vp_banner.setOnPageChangeListener(new OnPageChangeListener() {
			int oldPosition = 0;

			public void onPageSelected(int position) {
				if (dotList != null || dotList.size() != 0) {

					viewPagerhandler.removeMessages(1);
					viewPagerhandler.sendEmptyMessageDelayed(1, 5 * 1000);

					try {
						if (dotList.size() > position % dotList.size()) {
							currentItem = position;
							dotList.get(position % dotList.size()).setBackgroundResource(R.drawable.dot_select);
							dotList.get(oldPosition % dotList.size()).setBackgroundResource(R.drawable.dot_normal);
							oldPosition = position;

						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	Handler viewPagerhandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 更新我们UI页面
			if (vp_banner == null) {
				return;
			}
			int index = vp_banner.getCurrentItem();
			int count = vp_banner.getAdapter().getCount();
			if (count <= 1) {
				return;
			}
			index++;
			if (index >= count) {
				index = 0;
			}
			vp_banner.setCurrentItem(index);
		}
	};

	public class LoadingAdapter extends PagerAdapter {
		private ArrayList<ImageView> images;

		public LoadingAdapter(ArrayList<ImageView> images) {
			this.images = images;
		}

		@Override
		public int getCount() {
			return images.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(images.get(arg1));
			return images.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);

		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void finishUpdate(View arg0) {

		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

	}
}
