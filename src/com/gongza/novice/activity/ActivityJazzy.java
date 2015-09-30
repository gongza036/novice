package com.gongza.novice.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.gongza.novice.R;
import com.gongza.novice.fragment.FargmentV4Jazzy;
import com.gongza.views.jazzyviewpager.JazzyViewPager;
import com.gongza.views.jazzyviewpager.JazzyViewPager.TransitionEffect;

public class ActivityJazzy extends FragmentActivity {
	private JazzyViewPager mJazzy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jazzy);
		initView();
	}

	private void initView() {
		setupJazziness(TransitionEffect.CubeOut);// 参数是动画样式
	}

	private void setupJazziness(TransitionEffect effect) {
		mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);
		mJazzy.setTransitionEffect(effect);
		mJazzy.setAdapter(new TabFragmentAdapter(getSupportFragmentManager()));
		mJazzy.setPageMargin(30);
	}

	private class TabFragmentAdapter extends FragmentPagerAdapter {

		Fragment[] pages = new Fragment[] { new FargmentV4Jazzy(),
				new FargmentV4Jazzy(), new FargmentV4Jazzy(),
				new FargmentV4Jazzy() };

		public TabFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return pages[position];
		}

		@Override
		public int getCount() {
			return pages.length;
		}

		// **************加上下面的两行即可实现换页时的动画效果*********************************//
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Object obj = super.instantiateItem(container, position);
			mJazzy.setObjectForPosition(obj, position);
			return obj;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			if (object != null) {
				return ((Fragment) object).getView() == view;
			} else {
				return false;
			}
		}

	}

}
