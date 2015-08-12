package com.gongza.novice.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.gongza.novice.R;
import com.gongza.views.jazzyviewpager.JazzyViewPager;
import com.gongza.views.jazzyviewpager.JazzyViewPager.TransitionEffect;
import com.gongza.views.jazzyviewpager.OutlineContainer;

public class ActivityJazzy extends Activity {
	private JazzyViewPager mJazzy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jazzy);
		initView();
	}

	private void initView() {
		setupJazziness(TransitionEffect.Accordion);//参数是动画样式
	}

	private void setupJazziness(TransitionEffect effect) {
		mJazzy = (JazzyViewPager) findViewById(R.id.jazzy_pager);
		mJazzy.setTransitionEffect(effect);
		mJazzy.setAdapter(new MainAdapter());
		mJazzy.setPageMargin(30);
	}

	private class MainAdapter extends PagerAdapter {
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			
			View view = LayoutInflater.from(ActivityJazzy.this).inflate(
					R.layout.main_pager, null);
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
			return 10;
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

}
