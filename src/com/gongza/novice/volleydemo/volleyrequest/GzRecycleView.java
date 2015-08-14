package com.gongza.novice.volleydemo.volleyrequest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class GzRecycleView extends RecyclerView{
	private float mDownX;
	private float mDownY;

	public GzRecycleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public GzRecycleView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public GzRecycleView(Context context) {
		super(context);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
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
