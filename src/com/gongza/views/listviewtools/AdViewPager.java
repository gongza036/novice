package com.gongza.views.listviewtools;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 
  * @ClassName: AdViewPager
  * @Description: 一个处理了左右和上下滑动冲突的viewpager，可以嵌套在listview中
  * @author gongza
  * @date 2015年7月13日 下午3:29:14
  *
 */
public class AdViewPager extends ViewPager {
	private float mDownX;
	private float mDownY;

	public AdViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = ev.getX();
			mDownY = ev.getY();
			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_MOVE:
			if (Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY)) {
				getParent().requestDisallowInterceptTouchEvent(true);
			} else {
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		case MotionEvent.ACTION_UP:

		case MotionEvent.ACTION_CANCEL:
			getParent().requestDisallowInterceptTouchEvent(false);
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
}
