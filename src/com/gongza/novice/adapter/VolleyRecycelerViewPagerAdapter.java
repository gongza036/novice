package com.gongza.novice.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.gongza.novice.ApplicationNovice;
import com.gongza.novice.R;
import com.gongza.novice.bean.group.BannerAdBean;
import com.gongza.novice.volleydemo.volleyrequest.BitmapCache;
import com.gongza.views.jazzyviewpager.JazzyViewPager;
import com.gongza.views.jazzyviewpager.OutlineContainer;

public class VolleyRecycelerViewPagerAdapter extends PagerAdapter {
	private Context context;
	private JazzyViewPager mJazzy;
	private ArrayList<BannerAdBean> adLists = new ArrayList<BannerAdBean>();// 广告数据
	private ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();

	private RequestQueue mQueue;
	private ImageLoader imageLoader;

	private PagerAdapter adapter2;

	public VolleyRecycelerViewPagerAdapter(Context context,
			JazzyViewPager mJazzy, ArrayList<BannerAdBean> adLists) {
		this.context = context;
		this.mJazzy = mJazzy;
		this.adLists = adLists;
		mQueue = ApplicationNovice.getHttpQueue();
		imageLoader = new ImageLoader(mQueue, new BitmapCache());

	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {

		View view = LayoutInflater.from(context).inflate(R.layout.main_pager,
				null);

		final ImageView imageView = (ImageView) view
				.findViewById(R.id.iv_pager);

		ImageListener listener = ImageLoader.getImageListener(imageView,
				R.drawable.ic_launcher, R.drawable.ic_launcher);
		imageLoader.get(adLists.get(position).getAdvert_pic(), listener);

		container.addView(view, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mJazzy.setObjectForPosition(view, position);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object obj) {
		container.removeView(mJazzy.findViewFromObject(position));
	}

	@Override
	public int getCount() {
		return adLists.size();
		// return 10;
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		if (view instanceof OutlineContainer) {
			return ((OutlineContainer) view).getChildAt(0) == obj;
		} else {
			return view == obj;
		}
	}

}
